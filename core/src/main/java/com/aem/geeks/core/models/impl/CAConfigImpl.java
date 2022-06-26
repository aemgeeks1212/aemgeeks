package com.aem.geeks.core.models.impl;

import com.aem.geeks.core.config.GeeksCAConfig;
import com.aem.geeks.core.models.CAConfig;
import com.day.cq.wcm.api.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.caconfig.ConfigurationResolver;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables = {SlingHttpServletRequest.class},
        adapters = {CAConfig.class},
        resourceType = {CAConfigImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class CAConfigImpl implements CAConfig {
    private static final Logger LOG = LoggerFactory.getLogger(CAConfigImpl.class);
    protected static final String RESOURCE_TYPE = "aemgeeks/components/content/card";

    @SlingObject
    ResourceResolver resourceResolver;

    @ScriptVariable
    Page currentPage;

    @OSGiService
    ConfigurationResolver configurationResolver;

    private String siteCountry;
    private String siteLocale;
    private String siteAdmin;
    private String siteSection;
    private GeeksCAConfig geeksCAConfig;

    @Override
    public String getSiteCountry() {
        return siteCountry;
    }

    @Override
    public String getSiteLocale() {
        return siteLocale;
    }

    @Override
    public String getSiteAdmin() {
        return siteAdmin;
    }

    @Override
    public String getSiteSection() {
        return siteSection;
    }

    @PostConstruct
    public void postConstruct() {
    GeeksCAConfig caConfig=getContextAwareConfig(currentPage.getPath(),resourceResolver);
        siteCountry=caConfig.siteCountry();
        siteLocale=caConfig.siteLocale();
        siteAdmin=caConfig.siteAdmin();
        siteSection=caConfig.siteSection();
    }

    public GeeksCAConfig getContextAwareConfig(String currentPage, ResourceResolver resourceResolver) {
        String currentPath = StringUtils.isNotBlank(currentPage) ? currentPage : StringUtils.EMPTY;
        Resource contentResource = resourceResolver.getResource(currentPath);
        if (contentResource != null) {
           ConfigurationBuilder configurationBuilder = contentResource.adaptTo(ConfigurationBuilder.class);
            if (configurationBuilder != null) {
                return configurationBuilder.as(GeeksCAConfig.class);
            }
        }
        return null;
    }
}
