package com.aem.geeks.core.models;

import com.day.cq.wcm.api.Page;

import java.util.Iterator;
import java.util.List;

public interface UserInfo {

    public String getUserId();

    public String getUserName();

    public List<String> getUserGroups();
}
