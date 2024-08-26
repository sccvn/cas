package org.apereo.cas.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.test.CasTestExtension;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import software.amazon.awssdk.services.clouddirectory.CloudDirectoryClient;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This is {@link CloudDirectoryAuthenticationConfigurationTests}.
 *
 * @author Misagh Moayyed
 * @since 6.2.0
 */
@SpringBootTest(classes = {
    RefreshAutoConfiguration.class,
    WebMvcAutoConfiguration.class,
    EndpointAutoConfiguration.class,
    WebEndpointAutoConfiguration.class,
    CasCoreAuthenticationAutoConfiguration.class,
    CasCoreServicesAutoConfiguration.class,
    CasCoreTicketsAutoConfiguration.class,
    CasCoreUtilAutoConfiguration.class,
    CasCoreScriptingAutoConfiguration.class,
    CasCoreWebAutoConfiguration.class,
    CasPersonDirectoryAutoConfiguration.class,
    CasCoreLogoutAutoConfiguration.class,
    CasCoreAutoConfiguration.class,
    CasCoreNotificationsAutoConfiguration.class,
    CasCloudDirectoryAuthenticationAutoConfiguration.class
},
    properties = {
        "cas.authn.cloud-directory.username-attribute-name=username",
        "cas.authn.cloud-directory.password-attribute-name=password",
        "cas.authn.cloud-directory.endpoint=http://127.0.0.1:1234"
    })
@EnableConfigurationProperties(CasConfigurationProperties.class)
@Tag("AmazonWebServices")
@ExtendWith(CasTestExtension.class)
class CloudDirectoryAuthenticationConfigurationTests {
    @Autowired
    @Qualifier("amazonCloudDirectory")
    private CloudDirectoryClient amazonCloudDirectory;

    @Test
    void verifyOperation() throws Throwable {
        assertNotNull(amazonCloudDirectory);
    }
}
