package com.heima.wemedia;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = WemediaApplication.class)
public class PasswordTest {
    @Test
    public void test(){
        String salt = "123456";
        String password = "123456";
        String md5Password = DigestUtils.md5Hex(password + salt);
        System.out.println(md5Password);
    }
}
