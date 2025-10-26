package com.heima.admin;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest(classes = AdminApplication.class)
public class PasswordTest {
    @Test
    public void test(){
        String password = "abc";
        String salt = "123abc";
        String md5Password = DigestUtils.md5DigestAsHex((password+salt).getBytes());
        System.out.println(md5Password);
    }
}
