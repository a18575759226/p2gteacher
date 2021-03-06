package com.xmg.p2p.base.service;
import java.util.List;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;

public interface ISystemDictionaryItemService {
    int insert(SystemDictionaryItem record);
    SystemDictionaryItem selectByPrimaryKey(Long id);
    int updateByPrimaryKey(SystemDictionaryItem record);
    PageResult queryPage(QueryObject qo);

    void saveOrUpdate(SystemDictionaryItem systemDictionaryItem);

    List<SystemDictionaryItem> listItemBySn(String sn);
}
