package org.apereo.cas.web;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.entity.SamlIdentityProviderEntity;
import org.apereo.cas.entity.SamlIdentityProviderEntityParser;
import org.apereo.cas.services.SamlIdentityProviderDiscoveryFeedService;
import org.apereo.cas.services.UnauthorizedServiceException;
import org.apereo.cas.validation.DelegatedAuthenticationAccessStrategyHelper;
import org.apereo.cas.web.support.ArgumentExtractor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.client.Clients;
import org.pac4j.core.context.JEEContext;
import org.pac4j.core.util.InitializableObject;
import org.pac4j.saml.client.SAML2Client;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is {@link SamlIdentityProviderDiscoveryFeedController}.
 *
 * @author Misagh Moayyed
 * @since 6.1.0
 */
@RestController("identityProviderDiscoveryFeedController")
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/idp/discovery")
public class SamlIdentityProviderDiscoveryFeedController {
    private final CasConfigurationProperties casProperties;

    private final SamlIdentityProviderDiscoveryFeedService samlIdentityProviderDiscoveryFeedService;


    @GetMapping(path = "/feed", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<SamlIdentityProviderEntity> getDiscoveryFeed() {
    	return samlIdentityProviderDiscoveryFeedService.getDiscoveryFeed();
    }

    /**
     * Home.
     *
     * @return the model and view
     */
    @GetMapping
    public ModelAndView home() {
        val model = new HashMap<String, Object>();

        val entityIds = samlIdentityProviderDiscoveryFeedService.getEntityIds();

        LOGGER.debug("Using service provider entity id [{}]", entityIds);
        model.put("entityIds", entityIds);

        model.put("casServerPrefix", casProperties.getServer().getPrefix());
        return new ModelAndView("saml2-discovery/casSamlIdPDiscoveryView", model);
    }

    /**
     * Redirect.
     *
     * @param entityID            the entity id
     * @param httpServletRequest  the http servlet request
     * @param httpServletResponse the http servlet response
     * @return the view
     */
    @GetMapping(path = "redirect")
    public View redirect(@RequestParam("entityID") final String entityID,
                         final HttpServletRequest httpServletRequest,
                         final HttpServletResponse httpServletResponse) {
        val provider = samlIdentityProviderDiscoveryFeedService.getProvider(entityID, httpServletRequest, httpServletResponse);

        return new RedirectView('/' + provider.getRedirectUrl(), true, true, true);
    }
}
