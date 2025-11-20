package com.anxu.smarthomeunity.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.Random;
/**
 * 验证码工具类
 *
 * @Author: haoanxu
 * @Date: 2025/11/20 15:54
 */
@Component
public class CodeUtils {
    public static String generateVerifyCode(){
        // 生成6位随机数字验证码
        return RandomStringUtils.randomNumeric(6);
    }
}
