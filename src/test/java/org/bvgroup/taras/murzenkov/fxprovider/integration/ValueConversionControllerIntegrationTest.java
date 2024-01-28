package org.bvgroup.taras.murzenkov.fxprovider.integration;

import org.bvgroup.taras.murzenkov.fxprovider.model.response.FxRate;
import org.bvgroup.taras.murzenkov.fxprovider.model.response.ValueConversion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = RANDOM_PORT,
        properties = "spring.profiles.active=test")
class ValueConversionControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldBulkConvertValues() {
        final var headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", APPLICATION_JSON_VALUE);
        final var httpEntity = new HttpEntity<>("""
                {
                    "currencies": [
                        {
                            "currencyTo": "GBP",
                            "amount": 10
                        }                      
                    ]
                }
                """, headers);
        final var response = restTemplate
                .exchange("/api/v1/fx-rates/value-conversion/USD",
                        POST,
                        httpEntity,
                        new ParameterizedTypeReference<List<ValueConversion>>() {
                        });
        assertThat(response.getBody())
                .isEqualTo(List.of(new ValueConversion("USD", "GBP", BigDecimal.valueOf(10))));
    }

}
