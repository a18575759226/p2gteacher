package com.xmg.p2p.base.query;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

@Setter@Getter
public class SystemDictionaryItemQueryObject extends QueryObject {
    private String keyword;
    private Long parentId;
    public String getKeyword() {
        return StringUtils.isNotBlank(keyword)?keyword:null;
    }
}
