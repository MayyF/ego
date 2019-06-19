package com.ego.manage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @Auther:S
 * @Date:19/6/19
 */
public interface PicService {
    Map<String,Object>upload(MultipartFile file)throws IOException;
}
