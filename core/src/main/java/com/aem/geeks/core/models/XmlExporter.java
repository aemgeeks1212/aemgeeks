package com.aem.geeks.core.models;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public interface XmlExporter {
    public String getTitle();
    public String getDescription();
    public List<String> getBooks();
    public Calendar getDate();
}
