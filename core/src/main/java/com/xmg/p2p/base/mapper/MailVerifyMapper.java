package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.MailVerify;
import com.xmg.p2p.base.query.QueryObject;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MailVerifyMapper {
    int insert(MailVerify record);
    MailVerify selectByUUID(String uuid);
}