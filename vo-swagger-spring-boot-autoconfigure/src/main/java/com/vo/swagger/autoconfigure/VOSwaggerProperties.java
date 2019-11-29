
package com.vo.swagger.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the vo swagger
 */
@ConfigurationProperties(prefix = "swagger")
public class VOSwaggerProperties {

    private String version = "v1";
    private String title = "Microservice";
    private String description = "Microservice";
    private Boolean domainInHeader = true;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDomainInHeader() {
        return domainInHeader;
    }

    public void setDomainInHeader(Boolean domainInHeader) {
        this.domainInHeader = domainInHeader;
    }
}
