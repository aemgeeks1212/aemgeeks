package com.aem.geeks.core.models;

import com.aem.geeks.core.services.OSGiFactoryConfig;

import java.util.List;

public interface OSGiConfigDemo {
    /*--------Start Tutorial #31--------*/
    public String getServiceName();
    public int getServiceCount();
    public boolean isLiveData();
    public String[] getCountries() ;
    public String getRunModes();
    /*---------End Tutorial #31---------*/

    /*--------Start Tutorial #32--------*/
    public int getServiceId();
    public String getServiceNameModule() ;
    public String getServiceURL() ;
    /*---------End Tutorial #32---------*/

    /*--------Start Tutorial #33--------*/
    public List<OSGiFactoryConfig> getAllOSGiConfigs();
    /*---------End Tutorial #33---------*/
}
