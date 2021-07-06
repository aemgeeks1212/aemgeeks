package com.aem.geeks.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

    @ObjectClassDefinition(name="AEM Geeks - Modular OSGi Configuration",
            description = "Modular OSGi Configuration demo.")
    public @interface GeeksOSGiConfig {

        @AttributeDefinition(
                name = "Service ID",
                description = "Enter service id.",
                type = AttributeType.INTEGER)
        int serviceID();

        @AttributeDefinition(
                name = "Service Name",
                description = "Enter service name.",
                type = AttributeType.STRING)
        public String serviceName() default "AEM Geeks Service";

        @AttributeDefinition(
                name = "Service URL",
                description = "Add Service URL.",
                type = AttributeType.STRING
        )
        String serviceURL() default "localhost";


    }
