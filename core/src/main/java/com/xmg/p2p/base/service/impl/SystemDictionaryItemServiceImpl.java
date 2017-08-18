package com.xmg.p2p.base.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.xmg.p2p.base.query.SystemDictionaryQueryObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.mapper.SystemDictionaryItemMapper;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.service.ISystemDictionaryItemService;
@Service
public class SystemDictionaryItemServiceImpl implements ISystemDictionaryItemService {
	@Autowired
	private SystemDictionaryItemMapper systemDictionaryItemMapper;

	public int insert(SystemDictionaryItem record) {
		return systemDictionaryItemMapper.insert(record);
	}

	public SystemDictionaryItem selectByPrimaryKey(Long id) {
		return systemDictionaryItemMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKey(SystemDictionaryItem record) {
		return systemDictionaryItemMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageResult queryPage(QueryObject qo) {
		Long count = systemDictionaryItemMapper.selectPageCount(qo);
		if(count==0L){
			return PageResult.empty(qo.getPageSize());
		}
		List<SystemDictionaryItem> result = systemDictionaryItemMapper.selectPageData(qo);
		return new PageResult(result,count.intValue(),qo.getCurrentPage(),qo.getPageSize());
	}

	@Override
	public void saveOrUpdate(SystemDictionaryItem systemDictionaryItem) {
		if(systemDictionaryItem.getId()==null){
			systemDictionaryItemMapper.insert(systemDictionaryItem);
		}else{
			systemDictionaryItemMapper.updateByPrimaryKey(systemDictionaryItem);
		}
	}

	@Override
	public List<SystemDictionaryItem> listItemBySn(String sn) {
		return systemDictionaryItemMapper.listItemBySn(sn);
	}

}
