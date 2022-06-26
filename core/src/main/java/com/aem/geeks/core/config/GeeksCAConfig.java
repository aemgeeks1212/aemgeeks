package com.aem.geeks.core.config;

import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

@Configuration(label="Geeks - Context Aware Configuration", description="Context Aware Configuration for AEM Geeks.")
public @interface GeeksCAConfig {

    @Property(label = "Geeks Country Site",
            description = "Geeks Site Name")
    String siteCountry() default "us";

    @Property(label = "Geeks Site Locale",
            description = "Geeks Site for for different languages")
    String siteLocale() default "en";

    @Property(label = "Geeks Site Admin",
            description = "Admin for updating country site.")
    String siteAdmin() default "aem-geeks";

    @Property(label = "Site Section",
            description = "Site section for geeks site.")
    String siteSection() default "aem";
}


