package com.aem.geeks.core.models.impl;

import com.adobe.granite.security.user.UserManagementService;
import com.adobe.granite.security.user.UserProperties;
import com.adobe.granite.security.user.UserPropertiesManager;
import com.aem.geeks.core.models.UserInfo;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.oak.spi.security.user.UserConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = UserInfo.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class UserInfoImpl implements UserInfo {
    private static final Logger LOG = LoggerFactory.getLogger(UserInfoImpl.class);

    private String userID;

    private String userName;
    private List<String> userGroupsList =new ArrayList<>();

    @SlingObject
    ResourceResolver resourceResolver;

    @OSGiService
    UserManagementService userManagementService;


    @Override
    public String getUserId() {
        return userID;
    }

    @Override
    public String getUserName(){
        return userName;
    }

    @Override
    public List<String> getUserGroups() {
        return userGroupsList;
    }

    @PostConstruct
    void init(){
        try {
            String anonymousID = userManagementService != null ? userManagementService.getAnonymousId() : UserConstants.DEFAULT_ANONYMOUS_ID;
            userID=resourceResolver.getUserID();
            UserManager userManager=resourceResolver.adaptTo(UserManager.class);

            if(userManager!=null){
                Iterator<Group> userGroups=  userManager.getAuthorizable(userID).memberOf();
                while (userGroups.hasNext()){
                    Group group=userGroups.next();
                    userGroupsList.add(group.getID());
                }

            }
            UserPropertiesManager userPropertiesManager=resourceResolver.adaptTo(UserPropertiesManager.class);
            if (userPropertiesManager != null) {
                UserProperties userProperties = userPropertiesManager.getUserProperties(userID, "profile");
                for (String userProperty : userProperties.getPropertyNames()) {
                    LOG.info("\n User Properties {} : {} ",userProperty,userProperties.getProperty(userProperty));
                }
                userName=userProperties.getDisplayName();

            }

        }catch (Exception e){
            LOG.info("\n USER Info ERROR - {}",e.getMessage());
        }
    }

}
