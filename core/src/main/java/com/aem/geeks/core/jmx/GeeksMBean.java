package com.aem.geeks.core.jmx;

import com.adobe.granite.jmx.annotation.Description;
public interface GeeksMBean {
    @Description("Please enter Author Name")
    String getAuthorName(String authorName);
}
