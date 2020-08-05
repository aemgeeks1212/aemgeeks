package com.aem.geeks.core.models.impl;

import com.aem.geeks.core.models.ComponentInfo;
import com.aem.geeks.core.services.ComponentsInfoService;
import com.aem.geeks.core.utils.Constants;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;


import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@Model(adaptables = SlingHttpServletRequest.class, adapters = ComponentInfo.class, resourceType = ComponentInfoImpl.RESOURCE_TYPE)
@Exporter(name = Constants.EXPORTER_NAME, extensions = Constants.EXPORTER_EXTENSION)
public class ComponentInfoImpl implements ComponentInfo {

    static final String RESOURCE_TYPE = "reportstool/components/content/notifications";


    @Inject
    private ResourceResolver resourceResolver;
    
    @Inject
    private ComponentsInfoService componentsInfoService;
    


    @Inject
    @Via(Constants.RESOURCE)
    @Optional
    private String image;

    @Inject
    @Via(Constants.RESOURCE)
    @Optional
    private String title;

    @Inject
    @Via(Constants.RESOURCE)
    @Optional
    private String description;
    
    @Inject
    @Via(Constants.RESOURCE)
    @Optional
    private String alt;

    @Inject
    @Via(Constants.RESOURCE)
    @Optional
    private String contentName;	

    /**
     * Returns the image path for the app item.
     *
     * @return image.
     */
    public String getImage() {
        //return ImageUtil.getImagePath(IMAGE_TRANSFORM, image, resourceResolver);
    	return image;
    }

    /**
     * Returns the title for the app item.
     *
     * @return the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the description for the app item.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the string value to display the alt text in the app item component.
     *
     * @return the altText.
     */
    public String getAlt() {
        return alt;
    }

    /**
     * @return the content name of this component, if configured.
     */
    public String getContentName(){
        return contentName;
    }
   
    public List<Map<String, String>> getComponents() {
    	return componentsInfoService.getComponents("/apps/aemgeeks");
    }

}
