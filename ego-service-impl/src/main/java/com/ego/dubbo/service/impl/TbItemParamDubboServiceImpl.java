package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:S
 * @Date:19/6/20
 */
public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {


    @Resource
    private TbItemParamMapper tbItemParamMapper;


    @Override
    public EasyUIDataGrid showPage(int page, int rows) {
        //设置分页
        PageHelper.startPage(page,rows);

        //如果表中有一个或一个以上的列是text类型. 生成的方法 xxxxxxxxWithBlobs()
        //如果不实用xxxWithBlobs()方法则不包含带有text的字段

        //List<TbItemParam>itemParams=tbItemParamMapper.selectByExample(new TbItemParamExample());

        List<TbItemParam>itemParams=tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
        EasyUIDataGrid dataGrid=new EasyUIDataGrid();

        PageInfo<TbItemParam>pi=new PageInfo<>(itemParams);
        dataGrid.setTotal(pi.getTotal());
        dataGrid.setRows(pi.getList());

        return dataGrid;

    }

    @Override
    public int delete(String ids) throws Exception {
        String []idStr=ids.split(",");
        int index=0;
        for(String id:idStr){
            index+=tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(id));
        }
        if(index==idStr.length){
            return 1;
        }else {
            throw new Exception("删除失败，可能原因：数据不存在");
        }
    }

    @Override
    public TbItemParam selByCatId(long id) {
        TbItemParamExample example=new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(id);
        List<TbItemParam>list=tbItemParamMapper.selectByExampleWithBLOBs(example);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public int insertParam(TbItemParam param) {
        return tbItemParamMapper.insertSelective(param);
    }

}
