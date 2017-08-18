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
public class SystemDictionary  extends BaseDomain {
    private String sn;//编号
    private String title;//标题
    public String getJsonString(){
        Map<String,Object> map  = new HashMap<String,Object>();
        map.put("id",super.getId());
        map.put("sn",this.sn);
        map.put("title",this.title);
        return JSON.toJSONString(map);
    }
}
