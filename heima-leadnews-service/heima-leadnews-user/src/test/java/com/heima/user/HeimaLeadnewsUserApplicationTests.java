package com.heima.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class HeimaLeadnewsUserApplicationTests {

    @Test
    void contextLoads() {
        String s = DigestUtils.md5DigestAsHex("abc123abc".getBytes());
        System.out.println("==============");
        System.out.println(s);
    }
/**
 * 批量导入用户
 */
}
