package com.aem.geeks.core.services.impl;

import com.aem.geeks.core.services.SearchService;
import com.aem.geeks.core.utils.ResolverUtil;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = SearchService.class, immediate = true)
public class SearchServiceImpl implements SearchService{

    private static final Logger LOG= LoggerFactory.getLogger(SearchServiceImpl.class);

    @Reference
    QueryBuilder queryBuilder;

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Activate
    public void activate(){
        LOG.info("\n ----ACTIVATE METHOD----");
    }

    public Map<String,String> createTextSearchQuery(String searchText,int startResult,int resultPerPage){
        Map<String,String> queryMap=new HashMap<>();
        queryMap.put("path","/content/we-retail");
        queryMap.put("type","cq:Page");
        queryMap.put("fulltext",searchText);
        queryMap.put("p.offset",Long.toString(startResult));
        queryMap.put("p.limit",Long.toString(resultPerPage));
        return queryMap;
    }

    @Override
    public JSONObject searchResult(String searchText,int startResult,int resultPerPage){
        LOG.info("\n ----SEARCH RESULT--------");
        JSONObject searchResult=new JSONObject();
        try {
            ResourceResolver resourceResolver = ResolverUtil.newResolver(resourceResolverFactory);
            final Session session = resourceResolver.adaptTo(Session.class);
            Query query = queryBuilder.createQuery(PredicateGroup.create(createTextSearchQuery(searchText,startResult,resultPerPage)), session);


            SearchResult result = query.getResult();

            int perPageResults = result.getHits().size();
            long totalResults = result.getTotalMatches();
            long startingResult = result.getStartIndex();
            double totalPages = Math.ceil((double) totalResults / (double) resultPerPage);

            searchResult.put("perpageresult",perPageResults);
            searchResult.put("totalresults",totalResults);
            searchResult.put("startingresult",startingResult);
            searchResult.put("pages",totalPages);


            List<Hit> hits =result.getHits();
            JSONArray resultArray=new JSONArray();
            for(Hit hit: hits){
                Page page=hit.getResource().adaptTo(Page.class);
                JSONObject resultObject=new JSONObject();
                resultObject.put("title",page.getTitle());
                resultObject.put("path",page.getPath());
                resultArray.put(resultObject);
                LOG.info("\n Page {} ",page.getPath());
            }
            searchResult.put("results",resultArray);

        }catch (Exception e){
            LOG.info("\n ----ERROR -----{} ",e.getMessage());
        }
        return searchResult;
    }
}
