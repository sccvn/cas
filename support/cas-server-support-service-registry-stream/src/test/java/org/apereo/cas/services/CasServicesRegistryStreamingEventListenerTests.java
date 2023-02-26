package org.apereo.cas.services;

import org.apereo.cas.config.CasServicesStreamingConfiguration;
import org.apereo.cas.support.events.service.CasRegisteredServiceDeletedEvent;
import org.apereo.cas.support.events.service.CasRegisteredServiceLoadedEvent;
import org.apereo.cas.support.events.service.CasRegisteredServiceSavedEvent;

import lombok.val;
import org.apereo.inspektr.common.web.ClientInfoHolder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is {@link CasServicesRegistryStreamingEventListenerTests}.
 *
 * @author Misagh Moayyed
 * @since 6.2.0
 */
@SpringBootTest(classes = {
    RefreshAutoConfiguration.class,
    CasServicesStreamingConfiguration.class
}, properties = "cas.service-registry.stream.core.enabled=true")
@Tag("RegisteredService")
public class CasServicesRegistryStreamingEventListenerTests {
    @Autowired
    @Qualifier("casServicesRegistryStreamingEventListener")
    private CasServicesRegistryStreamingEventListener casServicesRegistryStreamingEventListener;

    @Test
    public void verifyDeleted() {
        val service = RegisteredServiceTestUtils.getRegisteredService();
        val clientInfo = ClientInfoHolder.getClientInfo();
        assertDoesNotThrow(() -> casServicesRegistryStreamingEventListener.handleCasRegisteredServiceDeletedEvent(
            new CasRegisteredServiceDeletedEvent(this, service, clientInfo)));
    }

    @Test
    public void verifyLoaded() {
        val service = RegisteredServiceTestUtils.getRegisteredService();
        val clientInfo = ClientInfoHolder.getClientInfo();
        assertDoesNotThrow(() -> casServicesRegistryStreamingEventListener.handleCasRegisteredServiceLoadedEvent(
            new CasRegisteredServiceLoadedEvent(this, service, clientInfo)));
    }

    @Test
    public void verifySaved() {
        val service = RegisteredServiceTestUtils.getRegisteredService();
        assertDoesNotThrow(() -> casServicesRegistryStreamingEventListener.handleCasRegisteredServiceSavedEvent(
            new CasRegisteredServiceSavedEvent(this, service)));
    }
}
