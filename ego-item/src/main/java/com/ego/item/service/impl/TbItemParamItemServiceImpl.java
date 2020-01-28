package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemParamItemDubboSerice;
import com.ego.item.pojo.ParamItem;
import com.ego.item.service.TbItemParamItemService;
import com.ego.pojo.TbItemParamItem;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.tags.Param;

import java.util.List;

/**
 * @Auther:S
 * @Date:20/1/27
 */

@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {

    @Reference
    private TbItemParamItemDubboSerice tbItemParamItemDubboSericeImpl;


    @Override
    public String showParam(long itemId) {
        TbItemParamItem item=tbItemParamItemDubboSericeImpl.selByItemId(itemId);
        List<ParamItem>list= JsonUtils.JsonToList(item.getParamData(),ParamItem.class);
        System.out.println(list);
        StringBuffer sb=new StringBuffer();
        for (ParamItem param:list){
            sb.append("<table width='500' style='color:gray;'>");
            for (int i=0; i<param.getParams().size(); i++){
                if(i==0){
                    sb.append("<tr>");
                    sb.append("<td align='right' width='30%'>"+param.getGroup()+"</td>");
                    sb.append("<td align='right' width='30%'>"+param.getParams().get(i).getK()+"</td>");
                    sb.append("<td>"+param.getParams().get(i).getV()+"</td>");
                    sb.append("</tr>");
                }else{
                    sb.append("<tr>");
                    sb.append("<td></td>");
                    sb.append("<td align='right' >"+param.getParams().get(i).getK()+"</td>" );
                    sb.append("<td>"+param.getParams().get(i).getV()+"</td>");
                    sb.append("</tr>");
                }
            }
            sb.append("</table>");
            sb.append("<hr style='color:gray;'>");
        }
        return sb.toString();
    }
}
