package com.aem.geeks.core.utils;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import java.util.HashMap;
import java.util.Map;


/**
 *  resource resolver factory helper class
 */
public final class ResolverUtil {

    private ResolverUtil() {

    }

	public static final String GEEKS_SERVICE_USER = "geeksserviceuser";
    /**
     * @param  resourceResolverFactory factory
     * @return new resource resolver for Sony service user 
     * @throws LoginException if problems
     */
    public static ResourceResolver newResolver( ResourceResolverFactory resourceResolverFactory ) throws LoginException {
        final Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put( ResourceResolverFactory.SUBSERVICE, GEEKS_SERVICE_USER );

        // fetches the admin service resolver using service user.
        ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
        return resolver;
    }
    
	
}