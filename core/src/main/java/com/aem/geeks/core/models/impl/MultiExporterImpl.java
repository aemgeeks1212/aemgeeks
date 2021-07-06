package com.aem.geeks.core.models.impl;

import com.aem.geeks.core.models.MultiExporter;
import com.aem.geeks.core.models.XmlExporter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.naming.Name;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@Exporters({
        @Exporter(name = "jackson",extensions = "json",selector = "geeksjson",
        options = {
                @ExporterOption(name = "SerializationFeature.WRAP_ROOT_VALUE", value="true")
        }),
        @Exporter(name = "geeksxml",extensions = "xml",selector = "geeksxml")
})
@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = MultiExporter.class,
        resourceType = MultiExporterImpl.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@JsonRootName("json-exporter")
@XmlRootElement(name = "xml-exporter")
public class MultiExporterImpl implements MultiExporter {
    static final String RESOURCE_TYPE="aemgeeks/components/content/multiexporter";
    private static final Logger LOG = LoggerFactory.getLogger(MultiExporterImpl.class);

    @Inject
    Resource componentResource;

    @ValueMapValue
    String title;

    @ValueMapValue
    String description;

    @ValueMapValue
    Calendar date;

    @ValueMapValue
    private List<String> books;

    List<Map<String, String>> bookDetailsMap;

    @Override
    @JsonProperty(value = "json-title")
    @XmlElement(name = "xml-title")
    public String getTitle() {
        return title;
    }

    @Override
    @JsonProperty(value = "json-description")
    @XmlElement(name = "xml-description")
    public String getDescription() {
        return description;
    }

    @Override
    @JsonProperty(value = "json-books")
    @XmlElementWrapper(name = "xml-books")
    @XmlElement(name="book")
    public List<String> getBooks() {
        if (books != null) {
            return new ArrayList<String>(books);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    @JsonProperty(value = "json-date")
    @XmlElement(name = "xml-date")
    public Calendar getDate() {
        return date;
    }

    @JsonProperty(value = "json-name")
    @XmlElement(name = "xml-name")
    public String getAuthorName(){
        return "AEM GEEKS";
    }

}
