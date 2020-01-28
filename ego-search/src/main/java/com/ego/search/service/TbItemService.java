package com.ego.search.service;

import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.Map;

/**
 * @Auther:S
 * @Date:20/1/25
 */
public interface TbItemService {

    /**
     * 初始化
     * @throws SolrServerException
     * @throws IOException
     */
    public void init() throws SolrServerException, IOException;

    /**
     * 分页查询
     * @param query
     * @return
     */
    public Map<String,Object>selByQuery(String query, int page, int rows) throws SolrServerException, IOException;

    /**
     * 新增
     * @param item
     * @return
     */

    public int add(Map<String,Object>map,String des)throws SolrServerException,IOException;

}
