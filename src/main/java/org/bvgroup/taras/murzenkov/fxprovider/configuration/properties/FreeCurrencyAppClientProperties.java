package org.bvgroup.taras.murzenkov.fxprovider.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fx.providers.freecurrencyap")
@Getter
@Setter
public class FreeCurrencyAppClientProperties {
    private String host;
    private String apiKey;
}
