package com.ego.search.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.search.pojo.TbItemChild;
import com.ego.search.service.TbItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther:S
 * @Date:20/1/25
 */

@Service
public class TbItemServiceImpl implements TbItemService {

    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;

    @Reference(timeout = 30000)
    private TbItemCatDubboService tbItemCatDubboServiceImpl;

    @Reference
    private TbItemDescDubboService tbItemDescDubboServiceImpl;

    @Resource
    private CloudSolrClient solrClient;


    @Override
    public void init() throws SolrServerException, IOException {
        //查询所有正常的商品
        List<TbItem> items=tbItemDubboServiceImpl.sellAllByStatus((byte) 1);

        for (TbItem item: items){

            //商品对应的类目信息
            TbItemCat itemCat=tbItemCatDubboServiceImpl.selById(item.getCid());

            //商品对应的描述信息
            TbItemDesc itemDesc=tbItemDescDubboServiceImpl.selByItemid(item.getId());

            SolrInputDocument doc=new SolrInputDocument();

            doc.addField("id",item.getId());
            doc.addField("item_title",item.getTitle());
            doc.addField("item_sell_point",item.getSellPoint());
            doc.addField("item_price",item.getPrice());
            doc.addField("item_image",item.getImage());
            doc.addField("item_category_name",itemCat.getName());
            doc.addField("item_desc",itemDesc.getItemDesc());

            solrClient.add(doc);
        }

        solrClient.commit();
    }

    @Override
    public Map<String, Object> selByQuery(String query, int page, int rows) throws SolrServerException, IOException {

        SolrQuery params=new SolrQuery();
        //设置分页条件
        params.setStart(rows*(page-1));
        params.setRows(rows);
        //设置条件
        params.setQuery("item_keywords:"+query);

        //按照"item_updated"列排序
        params.setSort("item_updated", SolrQuery.ORDER.desc);

        //设置高亮
        params.setHighlight(true);
        params.addHighlightField("item_title");
        params.setHighlightSimplePre("<span style='color:red'>");
        params.setHighlightSimplePost("</span>");

        QueryResponse response=solrClient.query(params);

        List<TbItemChild>itemChildren=new ArrayList<>();

        SolrDocumentList docList=response.getResults();

        Map<String,Map<String,List<String>>>hh=response.getHighlighting();

        for (SolrDocument doc:docList){
            TbItemChild child=new TbItemChild();

            child.setId(Long.parseLong(doc.getFieldValue("id").toString()));
            List<String>list=hh.get(doc.getFieldValue("id")).get("item_title");

            if(list!=null&&list.size()>0){
                child.setTitle(list.get(0));
            }else{
                child.setTitle(doc.getFieldValue("item_title").toString());
            }
            child.setPrice((Long)doc.getFieldValue("item_price"));
            Object img=doc.getFieldValue("item_image");

            child.setImages(img==null||img.equals("")?new String[1]:img.toString().split(","));
            child.setSellPoint(doc.getFieldValue("item_sell_point").toString());

            itemChildren.add(child);
        }

        Map<String,Object>resultMap=new HashMap<>();
        resultMap.put("itemList",itemChildren);
        long leaf=docList.getNumFound()%rows;
        long pages=docList.getNumFound()/rows;
        resultMap.put("totalPages",leaf==0?pages:pages+1);

        return resultMap;

    }

    @Override
    public int add(Map<String, Object> map, String desc) throws SolrServerException, IOException {

        SolrInputDocument doc=new SolrInputDocument();

        doc.setField("id",map.get("id"));
        doc.setField("item_title",map.get("title"));
        doc.setField("item_sell_point",map.get("sellPoint"));
        doc.setField("item_price",map.get("price"));
        doc.setField("item_image",map.get("image"));
        doc.setField("item_category_name",tbItemCatDubboServiceImpl.selById((Integer)map.get("cid")).getName());
        doc.setField("item_desc",desc);

        UpdateResponse response=solrClient.add(doc);
        solrClient.commit();
        if(response.getStatus()==0){
            return 1;
        }
        return 0;

    }
}
