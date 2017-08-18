package com.xmg.p2p.base.service;
import java.util.List;
import com.xmg.p2p.base.domain.MailVerify;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;

public interface IMailVerifyService {
    int insert(MailVerify record);
    MailVerify selectByUUID(String uuid);
}
