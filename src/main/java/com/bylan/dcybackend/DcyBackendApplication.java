package com.bylan.dcybackend;

import com.bylan.dcybackend.component.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * 启动类
 *
 * @author Rememorio
 */
@SpringBootApplication
public class DcyBackendApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(DcyBackendApplication.class, args);
        WebSocketServer.setApplicationContext(applicationContext);

    }

}
