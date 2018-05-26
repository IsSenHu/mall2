package com.husen.mall2.util;

import com.husen.mall2.vo.JsonResult;

/**
 * 生成Json返回格式结果的工具类
 * @author husen
 */
public class JsonResultUtil<T> {

    public static JsonResult success(Object object, String description){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(200);
        jsonResult.setDescription(description);
        jsonResult.setData(object);
        return jsonResult;
    }

    public static JsonResult faile(Object object, String description){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(400);
        jsonResult.setDescription(description);
        jsonResult.setData(object);
        return jsonResult;
    }
}
