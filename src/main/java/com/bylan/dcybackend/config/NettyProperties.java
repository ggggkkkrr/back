package com.bylan.dcybackend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wuhuaming
 * @date 2022/06/04 16:46
 */
@Data
@Component("nettyProperties")
@ConfigurationProperties(prefix = "netty")
public class NettyProperties {

    private Integer port;

    private String websocketPath;
}
