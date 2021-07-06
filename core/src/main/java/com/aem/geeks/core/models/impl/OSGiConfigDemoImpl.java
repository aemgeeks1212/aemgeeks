package com.aem.geeks.core.models.impl;

import com.aem.geeks.core.models.OSGiConfigDemo;
import com.aem.geeks.core.models.ServiceDemo;
import com.aem.geeks.core.services.OSGiConfig;
import com.aem.geeks.core.services.OSGiConfigModule;
import com.aem.geeks.core.services.OSGiFactoryConfig;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = OSGiConfigDemo.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class OSGiConfigDemoImpl implements OSGiConfigDemo{


    /*--------Start Tutorial #31--------*/
    @OSGiService
    OSGiConfig oSGiConfig;

    @Override
    public String getServiceName() {
        return oSGiConfig.getServiceName();
    }

    @Override
    public int getServiceCount() {
        return oSGiConfig.getServiceCount();
    }

    @Override
    public boolean isLiveData() {
        return oSGiConfig.isLiveData();
    }

    @Override
    public String[] getCountries() {
        return oSGiConfig.getCountries();
    }

    @Override
    public String getRunModes() {
        return oSGiConfig.getRunModes();
    }
    /*--------End Tutorial #31--------*/

    /*--------Start Tutorial #32--------*/
    @OSGiService
    OSGiConfigModule oSGiConfigModule;

    @Override
    public int getServiceId() {
        return oSGiConfigModule.getServiceId();
    }
    @Override
    public String getServiceNameModule() {
        return oSGiConfigModule.getServiceName();
    }
    @Override
    public String getServiceURL() {
        return oSGiConfigModule.getServiceURL();
    }
    /*--------End Tutorial #32--------*/

    /*--------Start Tutorial #33--------*/
    @OSGiService
    OSGiFactoryConfig oSGiFactoryConfig;

    @Override
    public List<OSGiFactoryConfig> getAllOSGiConfigs() {
        return oSGiFactoryConfig.getAllConfigs();
    }
    /*--------End Tutorial #33--------*/

}
