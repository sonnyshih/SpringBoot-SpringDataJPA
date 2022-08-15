package com.example.testProperties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "system")
@Component
public class SystemProperties {

    private String name;    // system.name
    private String url;     // system.url
    private String port;    // system.port

}
