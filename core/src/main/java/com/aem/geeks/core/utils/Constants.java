package com.aem.geeks.core.utils;

/*
 * Commonly used model constants
 */
public final class Constants {

    /**
     * Name of exporter used for exporting components properties.
     */
    public static final String EXPORTER_NAME = "jackson";

    /**
     * Extension to register exports
     */
    public static final String EXPORTER_EXTENSION = "json";


    /**
     * Header and Footer default properties
     */
    public static final String DEFAULT_HEADER_FOOTER_KEY = "NA_SCA_AEM";
    public static final String DEFAULT_HEADER_FOOTER_TYPE = "responsive";
    
    /**
     * Model constants
     */
    public static final String RESOURCE= "resource";
    public static final String SLING_HTTP_SERVLET_REQUEST= "SlingHttpServletRequest";
    public  static final String LINK_LABEL_KEY = "label";
    public  static final String CTA_KEY = "cta";
    public  static final String URL_KEY = "url";
    public  static final String TARGET_BLANK_KEY = "targetBlank";
    public  static final String HASH = "#";
    public  static final String APPS = "/apps/";

    private Constants() {
    }

}
