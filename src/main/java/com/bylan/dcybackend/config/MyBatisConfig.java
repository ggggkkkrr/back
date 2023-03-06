package com.bylan.dcybackend.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis相关配置
 *
 * @author Rememorio
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.bylan.dcybackend.dao"})
public class MyBatisConfig {
}
