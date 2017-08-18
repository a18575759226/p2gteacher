package com.xmg.p2p.base.service;

import java.util.List;

import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.UserFileQueryObject;

public interface IUserFileService {

	PageResult query(UserFileQueryObject qo);

	/**
	 * 申请风控材料(用一个风控材料图片去创建一个对应的风控材料对象)
	 * 
	 * @param fileName
	 */
	void apply(String fileName);

	/**
	 * 列出一个用户所有没有选择风控材料类型的风控材料对象
	 * 
	 * @param id
	 * @return
	 */
	List<UserFile> listSelectTypeUserFiles(Long userid, boolean selected);

	/**
	 * 用户选择风控材料分类
	 * 
	 * @param id
	 * @param fileType
	 */
	void choiceUserFileType(Long[] id, Long[] fileType);

	/**
	 * 审核
	 * 
	 * @param id
	 * @param state
	 * @param score
	 * @param remark
	 */
	void audit(Long id, int state, int score, String remark);

	/**
	 * 列出一个用户所有已经通过审核的风控材料列表
	 * 
	 * @param id
	 * @return
	 */
	List<UserFile> listFilesByUser(Long id);

}
