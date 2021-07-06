package com.aem.geeks.core.services;

public interface OSGiConfig {
    public String getServiceName();
    public int getServiceCount();
    public boolean isLiveData();
    public String[] getCountries() ;
    public String getRunModes();
}
