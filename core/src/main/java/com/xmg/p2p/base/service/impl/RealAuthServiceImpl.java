package com.xmg.p2p.base.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.RealAuthMapper;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.RealAuthQueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;

@Service
public class RealAuthServiceImpl implements IRealAuthService {
	@Autowired
	private RealAuthMapper realAuthMapper;
	@Autowired
	private IUserinfoService userinfoService;

	@Override
	public PageResult query(RealAuthQueryObject qo) {
		int count = this.realAuthMapper.queryForCount(qo);
		if (count > 0) {
			List<RealAuth> list = this.realAuthMapper.query(qo);
			return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
		}
		return PageResult.empty(qo.getPageSize());
	}

	@Override
	public RealAuth get(Long realAuthId) {
		return realAuthMapper.selectByPrimaryKey(realAuthId);
	}

	@Override
	public void save(RealAuth realAuth) {
		// 是否有待审核的流程||已经审核的流程--->userinfo.realAuthId
		// 获取到userinfo对象
		Userinfo current = userinfoService.getCurrent();
		if (current.getRealAuthId() == null) {
			RealAuth r = new RealAuth();
			r.setApplier(UserContext.getCurrent());
			r.setApplyTime(new Date());
			r.setBornDate(realAuth.getBornDate());
			r.setIdNumber(realAuth.getIdNumber());
			r.setAddress(realAuth.getAddress());
			r.setImage1(realAuth.getImage1());
			r.setImage2(realAuth.getImage2());
			r.setRealName(realAuth.getRealName());
			r.setSex(realAuth.getSex());
			r.setState(RealAuth.STATE_NORMAL);
			realAuthMapper.insert(r);
			// 获取到RealAuthId注入到userinfo中
			current.setRealAuthId(r.getId());
			userinfoService.update(current);
		}

	}

	@Override
	public void audit(Long id, String remark, int state) {
		RealAuth ra = this.realAuthMapper.selectByPrimaryKey(id);
		if (ra != null) {
			Userinfo applier = this.userinfoService.get(ra.getApplier().getId());
			// 判断,状态为待审核,申请人没有实名认证,
			if (ra.getState() == RealAuth.STATE_NORMAL// 判断,状态为待审核
					&& !applier.getIsRealAuth()// 判断,申请人没有实名认证,
			) {
				// 设置审核相关属性;
				ra.setAuditor(UserContext.getCurrent());
				ra.setAuditTime(new Date());
				ra.setRemark(remark);
				ra.setState(state);
				// 如果审核通过;
				if (state == RealAuth.STATE_PASS) {
					// 1,设置userinfo里面idnumber和realname
					applier.setRealName(ra.getRealName());
					applier.setIdNumber(ra.getIdNumber());
					// 2,为用户添加实名认证状态吗
					applier.setBitState(BitStatesUtils.addState(applier.getBitState(), BitStatesUtils.OP_REAL_AUTH));
				} else {
					// 如果审核失败;
					// 1,设置realauthid为空
					applier.setRealAuthId(null);
				}
				this.userinfoService.update(applier);
				this.realAuthMapper.updateByPrimaryKey(ra);
			}
		}

	}

}
