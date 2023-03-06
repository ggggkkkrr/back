package com.bylan.dcybackend.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class SecurityConfigTest {

    @Test
    void encode() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String[] accounts = {
                "202001",
                "202002",
                "202101",
                "202102",
                "202103",
                "202104",
                "202105",
        };
        for (String account : accounts) {
            System.out.println(account + ":" + bCryptPasswordEncoder.encode(account));
        }
    }

}