package com.xmg.p2p.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.query.UserFileQueryObject;

public interface UserFileMapper {

	int insert(UserFile record);

	UserFile selectByPrimaryKey(Long id);

	int updateByPrimaryKey(UserFile record);

	/**
	 * 列出一个用户所有没有选择风控材料类型的风控材料对象
	 * 
	 * @param userid
	 * @return
	 */
	List<UserFile> listSelectTypeUserFiles(@Param("userid") Long userid, @Param("selected") boolean selected);

	int queryForCount(UserFileQueryObject qo);

	List<UserFile> query(UserFileQueryObject qo);

	/**
	 * 列出一个用户所有已经通过审核的风控材料列表
	 * 
	 * @param id
	 * @return
	 */
	List<UserFile> listFilesByUser(Long id);

}