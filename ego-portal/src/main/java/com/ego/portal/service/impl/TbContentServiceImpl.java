package com.ego.portal.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.portal.service.TbContentService;
import com.ego.redis.JedisDao;
import com.ego.redis.impl.JedisDaoImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther:S
 * @Date:19/8/2
 */

@Service
public class TbContentServiceImpl implements TbContentService {

    @Reference
    private TbContentDubboService tbContentDubboServiceImpl;

    @Resource
    private JedisDao jedisDaoImpl;

    @Value("${redis.bigpic.key}")
    private String key;
    @Override
    public String showBigPic() {
        if(jedisDaoImpl.exists(key)) {
            String value = jedisDaoImpl.get(key);
            if (value != null && !"".equals(value)) {
                return value;
            }
        }
        List<TbContent>list=tbContentDubboServiceImpl.selByCount(6,true);
        List<Map<String,Object>>listRes=new ArrayList<>();
        for(TbContent tc:list){
            Map<String,Object>map=new HashMap<>();
            map.put("srcB",tc.getPic2());
            map.put("height",240);
            map.put("alt","error,load failed");
            map.put("width",670);
            map.put("src",tc.getPic());
            map.put("widthB",550);
            map.put("href",tc.getUrl());
            map.put("heightB",240);
            listRes.add(map);

        }

        String listJson= JsonUtils.objectToJson(listRes);
        if(listJson!=null){
            jedisDaoImpl.set(key,listJson);
        }

        return listJson;
    }
}
