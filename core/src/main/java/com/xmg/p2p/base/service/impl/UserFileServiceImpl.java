package com.xmg.p2p.base.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.UserFileMapper;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.UserContext;

@Service
public class UserFileServiceImpl implements IUserFileService {

	@Autowired
	private UserFileMapper userFileMapper;

	@Autowired
	private IUserinfoService userinfoService;

	@Override
	public PageResult query(UserFileQueryObject qo) {
		int count = this.userFileMapper.queryForCount(qo);
		if (count > 0) {
			List<UserFile> list = this.userFileMapper.query(qo);
			return new PageResult(list, count, qo.getCurrentPage(), qo.getPageSize());
		}
		return PageResult.empty(qo.getPageSize());
	}

	@Override
	public void apply(String fileName) {
		UserFile uf = new UserFile();
		uf.setImg(fileName);
		uf.setApplier(UserContext.getCurrent());
		System.out.println(UserContext.getCurrent() + "===============");
		uf.setApplyTime(new Date());
		this.userFileMapper.insert(uf);
	}

	@Override
	public List<UserFile> listSelectTypeUserFiles(Long userid, boolean selected) {
		return this.userFileMapper.listSelectTypeUserFiles(userid, selected);
	}

	@Override
	public List<UserFile> listFilesByUser(Long id) {
		return this.userFileMapper.listFilesByUser(id);
	}

	@Override
	public void choiceUserFileType(Long[] ids, Long[] fileTypes) {
		for (int i = 0; i < ids.length; i++) {
			UserFile uf = this.userFileMapper.selectByPrimaryKey(ids[i]);
			if (uf.getState() == UserFile.STATE_NORMAL && uf.getFileType() == null) {
				SystemDictionaryItem item = new SystemDictionaryItem();
				item.setId(fileTypes[i]);

				uf.setFileType(item);
				this.userFileMapper.updateByPrimaryKey(uf);
			}
		}
	}

	@Override
	public void audit(Long id, int state, int score, String remark) {
		// 判断,状态处于待审核
		UserFile uf = this.userFileMapper.selectByPrimaryKey(id);
		// 设置审核相关属性;
		if (uf != null && uf.getState() == UserFile.STATE_NORMAL) {
			uf.setAuditor(UserContext.getCurrent());
			uf.setAuditTime(new Date());
			uf.setRemark(remark);
			uf.setState(state);
			// 如果审核通过,累加用户的分数;
			if (state == UserFile.STATE_PASS) {
				uf.setScore(score);
				Userinfo applier = this.userinfoService.get(uf.getApplier().getId());
				applier.setScore(applier.getScore() + uf.getScore());
				this.userinfoService.update(applier);
			}
			this.userFileMapper.updateByPrimaryKey(uf);
		}
	}

}
