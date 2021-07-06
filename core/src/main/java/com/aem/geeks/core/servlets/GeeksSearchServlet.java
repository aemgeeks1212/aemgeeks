package com.aem.geeks.core.servlets;

import com.aem.geeks.core.services.SearchService;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


@Component(service = Servlet.class)
@SlingServletPaths(
        value = {"/geeks/search"}
)
public class GeeksSearchServlet extends SlingAllMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(GeeksSearchServlet.class);

    @Reference
    SearchService searchService;

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        JSONObject searchResult=null;
        try {
            String searchtext = req.getRequestParameter("searchText").getString();
            int pageNumber = Integer.parseInt(req.getRequestParameter("pageNumber").getString())-1;
            int resultPerPage = Integer.parseInt(req.getRequestParameter("resultPerPage").getString());
            int startResult=pageNumber*resultPerPage;
            searchResult=searchService.searchResult(searchtext,startResult,resultPerPage);
        } catch (Exception e) {
            LOG.info("\n ERROR {} ", e.getMessage());
        }

        resp.setContentType("application/json");
        resp.getWriter().write(searchResult.toString());
    }

}
