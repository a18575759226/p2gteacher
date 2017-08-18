package com.xmg.p2p.base.service;
import java.util.List;
import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;

public interface IAccountService {
    int insert(Account record);
    Account selectByPrimaryKey(Long id);
    int updateByPrimaryKey(Account record);
    public Account getCurrent();
}
