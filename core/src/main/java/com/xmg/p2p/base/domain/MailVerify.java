package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by lanxw on 2017/7/26.
 */
@Setter@Getter
public class MailVerify  extends BaseDomain {
    private Long userId;
    private String email;
    private String uuid;
    private Date sendTime;
    
}
