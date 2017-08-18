package com.xmg.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lanxw on 2017/7/23.
 */
@Setter@Getter
public class SystemDictionaryItem  extends BaseDomain {
    private Long parentId;
    private String title;
    private int sequence;
    public String getJsonString(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",super.getId());
        map.put("parentId",this.parentId);
        map.put("title",this.title);
        map.put("sequence",this.sequence);
        return JSON.toJSONString(map);
    }
}
