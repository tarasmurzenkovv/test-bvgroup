package org.bvgroup.taras.murzenkov.fxprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@EnableConfigurationProperties
@SpringBootApplication
public class FxProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxProviderApplication.class, args);
    }

}
