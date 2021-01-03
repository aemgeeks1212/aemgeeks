package com.aem.geeks.core.services.impl;


import com.aem.geeks.core.services.MultiService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = MultiService.class,immediate = true,name = "serviceA")
@ServiceRanking(1000)
public class MultiServiceAImpl implements MultiService{
    private static final Logger LOG= LoggerFactory.getLogger(MultiServiceAImpl.class);


    @Override
    public String getName() {
        return "MultiServiceAImpl";
    }


}
