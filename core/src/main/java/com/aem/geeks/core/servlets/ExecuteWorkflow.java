package com.aem.geeks.core.servlets;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Component(service = Servlet.class)
@SlingServletPaths(
        value = {"/bin/executeworkflow","/geeks/executeworkflow"}
)
public class ExecuteWorkflow extends SlingSafeMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ExecuteWorkflow.class);
    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        String status="Workflow Executing";

        final ResourceResolver resourceResolver = req.getResourceResolver();

        String payload=req.getRequestParameter("page").getString();
        try {
            if(StringUtils.isNotBlank(payload)) {

                WorkflowSession workflowSession = resourceResolver.adaptTo(WorkflowSession.class);

                WorkflowModel workflowModel = workflowSession.getModel("/var/workflow/models/geeks-page-version");

                WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH", payload);

                status=workflowSession.startWorkflow(workflowModel, workflowData).getState();
            }

        } catch (Exception e) {
            LOG.info("\n ERROR IN WORKFLOW {} ", e.getMessage());
        }
        resp.setContentType("application/json");
        resp.getWriter().write(status);
    }

}
