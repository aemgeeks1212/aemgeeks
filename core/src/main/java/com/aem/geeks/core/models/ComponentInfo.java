package com.aem.geeks.core.models;

import java.util.List;
import java.util.Map;

public interface ComponentInfo  {

    /**
     * Returns the image path for the app item.
     *
     * @return image.
     */
    String getImage();
    
    /**
     * Returns the title for the app item.
     *
     * @return the title.
     */
    String getTitle();
    
    /**
     * Returns the description for the app item.
     *
     * @return the description.
     */
    String getDescription();
    
    /**
	 * Returns the string value to display the alt text in the app item component.
	 *
	 * @return the altText.
	 */
     String getAlt();
     
     List<Map<String, String>> getComponents();
}