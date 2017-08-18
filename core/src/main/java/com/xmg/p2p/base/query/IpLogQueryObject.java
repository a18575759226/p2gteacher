package com.xmg.p2p.base.query;

import com.xmg.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter@Getter
public class IpLogQueryObject extends QueryObject {
    private String username;
    private Date beginDate;
    private Date endDate;
    private int state = -1;
    private int userType = -1;
    public String getUsername() {
        return StringUtils.isNotBlank(username)?username:null;
    }

    public Date getBeginDate() {
        return DateUtil.getBeginDate(beginDate);
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return DateUtil.getEndDate(endDate);
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
