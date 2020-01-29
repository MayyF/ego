package com.ego.manage.service.impl;

import com.ego.commons.utils.FtpUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.manage.service.PicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther:S
 * @Date:19/6/19
 */
@Service
//@PropertySource()
public class PicServiceImpl implements PicService {

    @Value("${ftpClient.host}")
    private String host;

    @Value("${ftpClient.port}")
    private int port;

    @Value("${ftpClient.usr}")
    private String usr;

    @Value("${ftpClient.pwd}")
    private String pwd;

    @Value("${ftpClient.basePath}")
    private String basePath;

    @Value("${ftpClient.filePath}")
    private String filePath;


    @Override
    public Map<String, Object> upload(MultipartFile file) throws IOException {
        String genImageName= IDUtils.genImageName()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        boolean result= FtpUtil.uploadFile(host,port,usr,pwd,basePath,filePath,genImageName,file.getInputStream());
        Map<String,Object>map=new HashMap<>();
        if(result){
            map.put("error",0);
            map.put("url","http://"+host+"/"+genImageName);
        }else{
            map.put("error",1);
            map.put("message","图片上传失败");
        }
        return map;

    }
}
