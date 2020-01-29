package com.ego.manage.service.impl;

import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.redis.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TbItemServiceImpl implements TbItemService{
	@Reference
	private TbItemDubboService tbItemDubboService;

	@Reference
	private TbItemDescDubboService tbItemDescDubboService;

	@Value("${search.url}")
	private String url;

	@Value("${redis.item.key}")
	private String itemKey;

	@Resource
	private JedisDao jedisDaoImpl;


	@Override
	public EasyUIDataGrid show(int page, int rows) {
		return tbItemDubboService.show(page, rows);
	}


	@Override
	public int update(String ids, byte status) {
		int index = 0 ;
		TbItem item = new TbItem();
		String[] idsStr = ids.split(",");
		for (String id : idsStr) {
			item.setId(Long.parseLong(id));
			item.setStatus(status);
			index +=tbItemDubboService.updItemStatus(item);

			if(status==2||status==3){
				jedisDaoImpl.del(itemKey+id);
			}

		}
		if(index==idsStr.length){
			return 1;
		}
		return 0;
	}

	@Override
	public int sava(TbItem item, String desc,String itemParams) throws Exception {
		long id= IDUtils.genItemId();
		item.setId(id);
		Date date=new Date();
		item.setCreated(date);
		item.setUpdated(date);
		item.setStatus((byte)1);
		TbItemDesc itemDesc=new TbItemDesc();
		itemDesc.setItemId(id);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);

		TbItemParamItem param=new TbItemParamItem();
		param.setUpdated(date);
		param.setCreated(date);
		param.setItemId(id);
		param.setParamData(itemParams);


		int index=0;
		index=tbItemDubboService.insTbItemDesc(item,itemDesc,param);
		System.out.println("index:"+index);

		new Thread(){
			public void run(){
				Map<String,Object>map=new HashMap<>();
				map.put("item",item);
				map.put("desc",desc);
				HttpClientUtil.doPostJson(url, JsonUtils.objectToJson(map));
			}
		}.start();
		return index;
	}
}
