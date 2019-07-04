package com.hypersmart.usercenter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: liyong
 * @CreateDate: 2019/7/2 17:35
 * @Version: 1.0
 */
@ConfigurationProperties(prefix = "xlsx")
@Component
public class SysProperties {
    private String template;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
