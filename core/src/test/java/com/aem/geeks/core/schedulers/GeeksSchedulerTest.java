package com.aem.geeks.core.schedulers;

import com.aem.geeks.core.config.SchedulerConfiguration;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.osgi.service.component.annotations.Reference;
import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.lang.annotation.Annotation;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(AemContextExtension.class)
class GeeksSchedulerTest {

    AemContext aemContext=new AemContext();
    private TestLogger LOG ;
    private GeeksScheduler schedulerTest;
    SchedulerConfiguration schedulerConfiguration;
    int schedulerId;
    /*@Mock
    ScheduleOptions scheduleOptions;

    @Mock
    Scheduler scheduler;*/

    @BeforeEach
    void setUp() {
        schedulerTest=aemContext.registerService(new GeeksScheduler());
        LOG=TestLoggerFactory.getTestLogger(schedulerTest.getClass());
        schedulerConfiguration=mock(SchedulerConfiguration.class);
        when(schedulerConfiguration.schedulerName()).thenReturn("Geeks Scheduler");
        when(schedulerConfiguration.cronExpression()).thenReturn("0/20 * * * * ?");

    }

/*
    @Test
    void run() {
        schedulerTest.run();
        List<LoggingEvent> logEvents = LOG.getLoggingEvents();
        assertEquals(1,logEvents.size());
        assertEquals(Level.INFO,logEvents.get(0).getLevel());
        assertEquals("\n ====> RUN METHOD",logEvents.get(0).getMessage());
        assertEquals(schedulerConfiguration.schedulerName(),"Geeks Scheduler");
    }
*/

}