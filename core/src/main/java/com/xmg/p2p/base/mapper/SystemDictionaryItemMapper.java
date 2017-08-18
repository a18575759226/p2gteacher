package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.query.QueryObject;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemDictionaryItemMapper {
    int insert(SystemDictionaryItem record);
    SystemDictionaryItem selectByPrimaryKey(Long id);
    int updateByPrimaryKey(SystemDictionaryItem record);

    Long selectPageCount(QueryObject qo);

    List<SystemDictionaryItem> selectPageData(QueryObject qo);

    List<SystemDictionaryItem> listItemBySn(String sn);
}