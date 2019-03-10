package top.ratil.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: HappyPlan
 * @description: 获取验证中得错误
 * @author: Ratil
 * @create: 2018-08-19 21:09
 **/
public class FieldErrorUtil {
    public static Map<String, Object> fieldError(BindingResult errorResult) {
        Map<String, Object> errorMap = new HashMap<>();
        if (errorResult.hasErrors()) {
            for (FieldError field: errorResult.getFieldErrors()) {
                System.out.println(field.getDefaultMessage());
                errorMap.put(field.getField(), field.getDefaultMessage());
            }
        }
        return errorMap;
    }
}
