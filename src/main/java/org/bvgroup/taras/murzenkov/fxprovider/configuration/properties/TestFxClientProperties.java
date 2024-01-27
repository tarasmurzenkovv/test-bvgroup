package org.bvgroup.taras.murzenkov.fxprovider.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fx.providers.test")
@Getter
@Setter
public class TestFxClientProperties {
    private String host;
}
