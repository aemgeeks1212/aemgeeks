package com.aem.geeks.core.services;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ComponentsInfoService {

   /**
     * @param path       the base press releases page path, if configured
     * @return newest press release items
     */
    List<Map<String, String>> getComponents(final String path);
    
    List<Map<String, String>> getComponentReferences(final String component);

    

}
