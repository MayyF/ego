package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.ego.redis.JedisDao;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther:S
 * @Date:19/7/24
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
    public EasyUIDataGrid showContent(long categoryId, int page, int rows) {
        return tbContentDubboServiceImpl.selContentByPage(categoryId,page,rows);
    }

    @Override
    public int save(TbContent tbContent) {
        Date date=new Date();
        tbContent.setUpdated(date);
        tbContent.setCreated(date);

        int idx=tbContentDubboServiceImpl.insContent(tbContent);
        if(jedisDaoImpl.exists(key)){
            String value=jedisDaoImpl.get(key);
            if(value!=null&&!"".equals(value)){
                List<HashMap>list= JsonUtils.JsonToList(value,HashMap.class);
                HashMap<String,Object>map=new HashMap<>();
                map.put("srcB",tbContent.getPic2());
                map.put("height",240);
                map.put("alt","error,load failed");
                map.put("width",670);
                map.put("src",tbContent.getPic());
                map.put("widthB",550);
                map.put("href",tbContent.getUrl());
                map.put("heightB",240);
                if(list.size()==6){
                    list.remove(5);
                }
                list.add(0,map);
                jedisDaoImpl.set(key,JsonUtils.objectToJson(list));
            }
        }
        return idx;
    }


}
