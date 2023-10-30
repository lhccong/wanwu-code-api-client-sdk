package com.cong.wanwu.api.client.sdk;

import com.cong.wanwu.api.client.sdk.client.CongApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author lhc
 * @date 2022-11-10 11:36
 */
@ConfigurationProperties("wanwu.api.client")
@Configuration
@Data
@ComponentScan
public class CongApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public CongApiClient congApiClient() {
        return new CongApiClient(accessKey, secretKey);
    }

}
