package com.xmg.p2p.base.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.domain.VedioAuth;
import com.xmg.p2p.base.mapper.VedioAuthMapper;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.VedioAuthQueryObject;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.service.IVedioAuthService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;

@Service
public class VedioAuthServiceImpl implements IVedioAuthService {
	@Autowired
	private VedioAuthMapper vedioAuthMapper;

	@Autowired
	private IUserinfoService userinfoService;

	@Override
	public PageResult query(VedioAuthQueryObject qo) {
		int count = this.vedioAuthMapper.queryForCount(qo);
		if (count > 0) {
			List<VedioAuth> list = this.vedioAuthMapper.query(qo);
			return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
		}
		return PageResult.empty(qo.getPageSize());
	}

	@Override
	public void audit(long userinfoId, int state, String remark) {
		// 判断这个用户没有视频审核
		Userinfo applier = this.userinfoService.get(userinfoId);
		if (!applier.getIsVedioAuth()) {
			// 直接创建一个视频审核对象;
			VedioAuth v = new VedioAuth();
			// 设置相关的审核属性
			Logininfo ap = new Logininfo();
			ap.setId(userinfoId);
			v.setApplier(ap);
			v.setApplyTime(new Date());
			v.setAuditor(UserContext.getCurrent());
			v.setAuditTime(new Date());
			v.setRemark(remark);
			v.setState(state);
			this.vedioAuthMapper.insert(v);
			// 如果审核通过,为这个用户添加对应的状态吗
			if (state == VedioAuth.STATE_PASS) {
				applier.setBitState(BitStatesUtils.addState(applier.getBitState(), BitStatesUtils.OP_VEDIO_AUTH));
				this.userinfoService.update(applier);
			}
		}
	}

}
