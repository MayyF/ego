package com.ego.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * @Auther:S
 * @Date:19/8/2
 */
public class JsonUtils {

    private static final ObjectMapper MPR=new ObjectMapper();

    public static String objectToJson(Object data){
        String str=null;
        try {
            str=MPR.writeValueAsString(data);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }finally {
            return str;
        }
    }

    public static <T> T jsonToPojo(String jsonData,Class<T>beanType){

        try {
            T t = MPR.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static <T>List<T> JsonToList(String jsonData,Class<T>beanType){
        JavaType javaType=MPR.getTypeFactory().constructParametricType(List.class,beanType);
        try {
            List<T>list=MPR.readValue(jsonData,javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
