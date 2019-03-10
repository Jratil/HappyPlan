package top.ratil.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: HappyPlan
 * @description: 封装返回给前端的信息
 * @author: Ratil
 * @create: 2018-09-02 12:35
 **/
@Component
public class PackageJsonUtil<T> {

    LinkedHashMap<String, Object> messageMap = new LinkedHashMap<>();

    public PackageJsonUtil() {

    }

    /**
     * 请求失败,添加错误信息
     *
     * @param errorMap 错误信息
     */
    public Map<String, Object> failStatus(Map<String, Object> errorMap) {
        messageMap.put("code", 400);
        if(errorMap == null) {
            messageMap.put("message", "失败");
        } else {
            messageMap.put("message", errorMap);
        }
        messageMap.put("data", new HashMap<>());
        return messageMap;
    }

    /**
     * 请求成功,如果要返回数据怎么添加
     *
     * @param list 要返回的数据, 没有则设为null
     */
    public Map<String, Object> successStatus(List<T> list) {
        messageMap.put("code", 200);
        messageMap.put("message", "成功");
        //data有数据则添加
        if (list != null) {
            Map<String, Object> listMap = new HashMap<>();
            listMap.put("list", list);
            messageMap.put("data", listMap);
        } else {
            messageMap.put("data", new HashMap<>());
        }
        return messageMap;
    }
}
