package com.aem.geeks.core.services.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationStatus;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.aem.geeks.core.services.ComponentsInfoService;
import com.aem.geeks.core.utils.ResolverUtil;

@Component(immediate = true, service = ComponentsInfoService.class, name = "com.aem.geeks.core.services.ComponentsInfo", configurationPid = "com.aem.geeks.core.services.impl.ComponentsInfoImpl",
		property = {
		"service.vendor=AEM GEEKS",
		"service.description=AEM GEEKS Training" }, configurationPolicy = ConfigurationPolicy.OPTIONAL)
public class ComponentsInfoServiceImpl implements ComponentsInfoService {

	private static final Logger LOG = LoggerFactory.getLogger(ComponentsInfoServiceImpl.class);

	public static final String MMM_DD_YYYY = "MMM dd,yyyy";
	public static final String MM_DD_YYYY = "MM/dd/yyyy";
	static final DateFormat DATE_FORMAT = new SimpleDateFormat(MM_DD_YYYY, Locale.ENGLISH);

	@ObjectClassDefinition(name = "Reports Tool - Component information service", description = "This service used to get components information.")
	public static @interface ComponentInfoServiceConfig {

		/**
		 * @return max press release teasers to show
		 */
		@AttributeDefinition(name = "Max Items", description = "Default is 4 teasers.", type = AttributeType.INTEGER)
		int defaultMaxItems() default 4;

		/**
		 * @return Default rows per page
		 */
		@AttributeDefinition(name = "Default Rows Per Page", description = "Please enter the default.", type = AttributeType.INTEGER)
		int defaultPerPage() default 10;

	} // PressReleaseListServiceConfig

	/**
	 * configured OSGI property values
	 */
	private ComponentInfoServiceConfig componentServiceConfig = null;

	/**
	 * QueryBuilder variable.
	 */
	@Reference
	private QueryBuilder builder;

	/**
	 * ResourceResolverFactory variable.
	 */
	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	/**
	 * This method is used to assign OSGI config values.
	 * 
	 * @param serviceConfig config properties
	 */
	@Activate
	@Modified
	public void activate(final ComponentInfoServiceConfig serviceConfig) {

		// update OSGI config properties
		LOG.info("\n ---------------START ACTIVE---------------");
		setComponentServiceConfig(serviceConfig);
		LOG.info("\n ---------------END ACTIVE---------------");

	} // activate

	/**
	 * @param path          the base press releases page path, if configured
	 * @return list of press releases found
	 */
	public List<Map<String, String>> getComponents(final String path) {
		LOG.info("\n -------- GET COMPONENT METHOD----------- ");
		// local variables
		final List<Map<String, String>> componentList = new ArrayList<Map<String, String>>();

		ResourceResolver resolver = null;
		try {
			LOG.info("\n ---resolver not found---> ");
			resolver = ResolverUtil.newResolver(resourceResolverFactory);
			LOG.info("\n ---resolver HIT ---> " + resolver.getUserID());
			final Session session = resolver.adaptTo(Session.class);
			final PageManager pageManager = resolver.adaptTo(PageManager.class);

			Map<String, String> map = buildQueryMap(path);
			Query query = builder.createQuery(PredicateGroup.create(map), session);
			SearchResult result = query.getResult();

			long hitNum = 0;
			LOG.info("\n ---TOTOAL HIT ---> " + result.getTotalMatches());
			Iterator<Resource> componentResource = result.getResources();
			while (componentResource.hasNext()) {
				Map<String, String> componentMap = new HashMap<String, String>();
				Resource component = componentResource.next();
				String title = component.getValueMap().get("jcr:title").toString();
				String componentGroup = component.getValueMap().get("componentGroup").toString();
				String componentPath = StringUtils.substringAfter(component.getPath(), "/apps/");
				componentMap.put("title", title);
				componentMap.put("group", componentGroup);
				componentMap.put("name", component.getName());
				componentMap.put("path", componentPath);
				componentMap.put("pages", getComponentReferenceCount(componentPath,session));
				componentList.add(componentMap);
				//LOG.info("\n ------> " + componentPath + " : " + componentGroup);
			}

		} catch (LoginException e) {
			LOG.error("LoginException occurred exception :{}", e);
		} catch (Exception e) {
			LOG.error("LoginException occurred exception :{}", e);
		} finally {
			if (resolver != null && resolver.isLive()) {
				resolver.close();
			} // if{}
		} // try-catch{}

		return componentList;
	} // getPressReleaseList()

	
	public String getComponentReferenceCount(final String component,Session session) {
		LOG.info("\n -------- GET COMPONENT METHOD----------- ");
		// local variables
		final List<Map<String, String>> componentList = new ArrayList<Map<String, String>>();
		long references=0;
		ResourceResolver resolver = null;
		try {
			resolver = ResolverUtil.newResolver(resourceResolverFactory);

			//final Session session = resolver.adaptTo(Session.class);
			//final PageManager pageManager = resolver.adaptTo(PageManager.class);

			Map<String, String> map = buildQueryReferenceMap(component);
			Query query = builder.createQuery(PredicateGroup.create(map), session);
			SearchResult result = query.getResult();

		    references =result.getTotalMatches();
			LOG.info("\n ---TOTOAL HIT ---> " + result.getTotalMatches());
			Iterator<Resource> componentResource = result.getResources();


		} catch (LoginException e) {
			LOG.error("LoginException occurred , exception :{}", e);
		} catch (Exception e) {
			LOG.error("LoginException occurred , exception :{}", e);
		} finally {
			if (resolver != null && resolver.isLive()) {
				resolver.close();
			} // if{}
		} // try-catch{}

		return Long.toString(references);
	} // getPressReleaseList()
	
	
	
	
	public List<Map<String, String>> getComponentReferences(final String component) {
		LOG.info("\n -------- GET COMPONENT METHOD----------- ");
		// local variables
		final List<Map<String, String>> pagetList = new ArrayList<Map<String, String>>();

		ResourceResolver resolver = null;
		try {
			resolver = ResolverUtil.newResolver(resourceResolverFactory);

			final Session session = resolver.adaptTo(Session.class);
			final PageManager pageManager = resolver.adaptTo(PageManager.class);

			Map<String, String> map = buildQueryReferenceMap(component);
			Query query = builder.createQuery(PredicateGroup.create(map), session);
			SearchResult result = query.getResult();

			long hitNum = 0;
			LOG.info("\n ---TOTOAL Pages ---> "+component+" : " +result.getTotalMatches());
			Iterator<Resource> pageResources = result.getResources();
			while (pageResources.hasNext()) {
				Map<String, String> pageMap = new HashMap<String, String>();
				Resource pageResource = pageResources.next();
				String resourcePath=StringUtils.substringBefore(pageResource.getPath(), "jcr:content");
				Page page=pageManager.getPage(resourcePath);
				ReplicationStatus replicationStatus=page.adaptTo(ReplicationStatus.class);
				String activate="Published";
                if(!replicationStatus.isActivated()) {activate="unPublished";}
				pageMap.put("title", page.getTitle());
				pageMap.put("name", page.getName());
				pageMap.put("activated", activate);
				pageMap.put("path",page.getPath()+".html");
				pagetList.add(pageMap);
				LOG.info("\n ------> " + activate);
			}

		} catch (LoginException e) {
			LOG.error("LoginException occurred , exception :{}", e);
		} catch (Exception e) {
			LOG.error("LoginException occurred , exception :{}", e);
		} finally {
			if (resolver != null && resolver.isLive()) {
				resolver.close();
			} // if{}
		} // try-catch{}

		return pagetList;
	} // getPressReleaseList()
	/**
	 * @return the query builder map.
	 */

	private Map<String, String> buildQueryMap(final String path) {

		final Map<String, String> map = new HashMap<String, String>();
		map.put("path", path);
		map.put("type", "cq:Component");
		map.put("p.limit", Long.toString(-1)); // ALL_PAGES_LIMIT_VALUE ); //
		return map;
	} // buildQueryMap()
	
	private Map<String, String> buildQueryReferenceMap(final String component) {

		final Map<String, String> map = new HashMap<String, String>();
		map.put("path", "/content");
		map.put("type", "nt:unstructured");
		map.put("property", "sling:resourceType");
		map.put("property.value",component);
		map.put("p.limit", Long.toString(-1)); // ALL_PAGES_LIMIT_VALUE ); //
		return map;
	} // buildQueryMap()

	/**
	 * @return the press release list service config
	 */
	private ComponentInfoServiceConfig getComponentServiceConfig() {
		return componentServiceConfig;
	}

	/**
	 * @param componentServiceConfig the press release list service config to set
	 */
	private void setComponentServiceConfig(final ComponentInfoServiceConfig componentServiceConfig) {
		this.componentServiceConfig = componentServiceConfig;
	}

} // PressReleaseListServiceImpl()
