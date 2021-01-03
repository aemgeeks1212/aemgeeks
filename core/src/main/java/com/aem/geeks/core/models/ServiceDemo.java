package com.aem.geeks.core.models;

import com.day.cq.wcm.api.Page;

import java.util.Iterator;
import java.util.List;

public interface ServiceDemo {

    public Iterator<Page> getPagesList();
    public List<String> getPageTitleList();

    public String getNameFromService();
    public String getNameFromServiceB();
    public String getNameWithReference();
}
