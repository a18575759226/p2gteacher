package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.query.QueryObject;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    int insert(Account record);
    Account selectByPrimaryKey(Long id);
    int updateByPrimaryKey(Account record);
}