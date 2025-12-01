package com.anxu.smarthomeunity.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test1 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        // 练习1：正确
        Class<?> clazz1 = Student.class;
        Student student = new Student("张三", 18);
        Class<?> clazz2 = student.getClass();
        Class<?> clazz3 = Class.forName("com.anxu.smarthomeunity.reflect.Student");
        System.out.println(clazz1 + "-" + clazz2 + "-" + clazz3);

        // 练习2：正确
        Student stu1 = new Student("小明", 19);
        Field fieldName = stu1.getClass().getDeclaredField("name");
        fieldName.setAccessible(true);
        System.out.println(fieldName.get(stu1)); // 输出：小明

        // 练习3：修正静态字段操作
        Field fieldSchool = Student.class.getDeclaredField("school"); // 直接用类获取更规范
        fieldSchool.setAccessible(true);
        fieldSchool.set(null, "清华大学"); // 静态字段传null
        System.out.println(fieldSchool.get(null)); // 输出：清华大学

        // 练习4：正确
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.age = 18;
        Method methodGetInfo = stu2.getClass().getDeclaredMethod("getInfo");
        methodGetInfo.setAccessible(true);
        Object info = methodGetInfo.invoke(stu2);
        System.out.println(info); // 输出：姓名：小红，年龄：18

        // 练习5：修正获取私有构造器
        Class<?> clazz4 = Student.class;
        Constructor<?> constructor = clazz4.getDeclaredConstructor(String.class, int.class); // 改用getDeclaredConstructor
        constructor.setAccessible(true);
        Student stu3 = (Student) constructor.newInstance("小李", 20);
        System.out.println(stu3.getName() + "-" + stu3.age); // 输出：小李-20
    }
}