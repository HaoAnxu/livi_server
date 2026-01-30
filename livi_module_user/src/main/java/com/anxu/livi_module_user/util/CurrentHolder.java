package com.anxu.livi_module_user.util;

/**
 * ThreadLocal 工具类
 *
 * @Author: haoanxu
 * @Date: 2025/11/20 15:54
 */
public class CurrentHolder {
    // 1. 定义一个 ThreadLocal 变量，泛型为 Integer（存储ID）
    // 用 private static final 修饰，确保全局唯一且不可修改
    private static final ThreadLocal<Integer> CURRENT_LOCAL = new ThreadLocal<>();

    // 2. 向当前线程的 ThreadLocal 中存储当前用户ID
    public static void setCurrentId(Integer currentId) {
        CURRENT_LOCAL.set(currentId);
    }

    // 3. 从当前线程的 ThreadLocal 中获取当前用户ID
    public static Integer getCurrentId() {
        return CURRENT_LOCAL.get();
    }

    // 4. 移除当前线程的 ThreadLocal 中的值（必须手动调用，否则可能内存泄漏）
    public static void remove() {
        CURRENT_LOCAL.remove();
    }
}
