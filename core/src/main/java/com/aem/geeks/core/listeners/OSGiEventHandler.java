/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.aem.geeks.core.listeners;

import com.aem.geeks.core.utils.ResolverUtil;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;

@Component(service = EventHandler.class,
           immediate = true,
           property = {
                   EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/ADDED",
                   EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/CHANGED",
                   EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/REMOVED",
                   EventConstants.EVENT_FILTER +"=(path=/content/aemgeeks/us/en/author/*)"
           })
public class OSGiEventHandler implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(OSGiEventHandler.class);

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    public void handleEvent(final Event event) {
        LOG.info("\n Resource event: {} at: {}", event.getTopic(), event.getProperty(SlingConstants.PROPERTY_PATH));
        try {
            ResourceResolver resourceResolver=ResolverUtil.newResolver(resourceResolverFactory);
            Resource resource=resourceResolver.getResource(event.getProperty(SlingConstants.PROPERTY_PATH).toString());
            Node node=resource.adaptTo(Node.class);
            node.setProperty("eventhandlertask","Event "+event.getTopic()+" by "+resourceResolver.getUserID());

            for(String prop : event.getPropertyNames()){
                LOG.info("\n Property : {} , Value : {} ", prop,event.getProperty(prop));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

