package com.anxu.smarthomeunity.reflect;

import com.anxu.smarthomeunity.model.entity.user.UserInfoEntity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @Author: haoanxu
 * @Date: 2025/12/1 10:38
 */
public class ReflectAdvanceUtil {
    public static void main(String[] args) throws IllegalAccessException {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
//        userInfoEntity.setId(1001);
//        userInfoEntity.setUsername("zhangsan");
//        userInfoEntity.setPassword("123456");
//        userInfoEntity.setEmail("zhangsan@example.com");
//        userInfoEntity.setGender(0);
//        userInfoEntity.setSignature("一个普通的人");
        List<Map<String, Object>> userFieldInfo = getAllFieldInfo(userInfoEntity);
        System.out.println("===== User字段信息 =====");
        for (Map<String, Object> map : userFieldInfo) {
            System.out.println(map);
        }
    }

    /**
     * 获取对象的所有字段信息，包括字段名、描述、修饰符、值
     * @param obj 目标对象
     * @return 字段信息列表
     * @throws IllegalAccessException 访问字段时可能抛出
     */
    public static List<Map<String,Object>> getAllFieldInfo(Object obj) throws IllegalAccessException {
        if (obj == null) {
            throw new IllegalArgumentException("入参obj不能为null");
        }
        List<Map<String,Object>> fieldInfoList = new ArrayList<>();

        //反射入口
        Class<?> clazz = obj.getClass();
        //获取所有字段，包括private，不包括父类
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if(Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)){
                continue;
            }
            String fieldName = field.getName();
            String modifierStr = Modifier.toString(modifiers);
            FieldDesc fieldDescAnnotation = field.getAnnotation(FieldDesc.class);
            String fieldDesc = fieldDescAnnotation != null ? fieldDescAnnotation.value() : "无描述";
            field.setAccessible(true);
            Object fieldValue;
            if (Modifier.isStatic(modifiers)) {
                // 静态字段：get的第一个参数传null
                fieldValue = field.get(null);
            } else {
                // 实例字段：get的第一个参数传obj
                fieldValue = field.get(obj);
            }
            Map<String, Object> fieldInfoMap = new HashMap<>(4);
            fieldInfoMap.put("fieldName", fieldName);
            fieldInfoMap.put("fieldDesc", fieldDesc);
            fieldInfoMap.put("modifier", modifierStr);
            fieldInfoMap.put("value", fieldValue);
            fieldInfoList.add(fieldInfoMap);
        }
        return fieldInfoList;
    }
    /**
     * 调用任意方法，包括private方法
     * @param object 目标对象
     * @param methodName 方法名
     * @param paramTypes 参数类型数组
     * @param args 参数数组
     * @return 方法返回值
     */
    public static Object invokeAnyMethod(Object object,String methodName,Class<?>[] paramTypes,Object[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //object可以为空，因为调用静态方法时，object传null即可
        if (methodName == null || methodName.trim().isEmpty()) {
            throw new IllegalArgumentException("方法名methodName不能为空");
        }
        //处理paramType/args的null，转为空数组
        Class<?>[] finalParamTypes = (paramTypes == null) ? new Class[0] : paramTypes;
        Object[] finalArgs = (args == null) ? new Object[0]:args;

        Method method = object.getClass().getDeclaredMethod(methodName, finalParamTypes);
        method.setAccessible(true);
        return method.invoke(object, args);
    }
}
