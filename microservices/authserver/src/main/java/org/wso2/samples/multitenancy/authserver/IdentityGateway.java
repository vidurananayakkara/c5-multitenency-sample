/*
*  Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.samples.multitenancy.authserver;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.kernel.context.PrivilegedCarbonContext;
import org.wso2.carbon.messaging.CarbonMessage;
import org.wso2.carbon.messaging.DefaultCarbonMessage;
import org.wso2.carbon.security.caas.api.ProxyCallbackHandler;
import org.wso2.carbon.security.caas.user.core.bean.User;
import org.wso2.carbon.security.caas.user.core.exception.IdentityStoreException;
import org.wso2.carbon.security.caas.user.core.exception.UserNotFoundException;
import org.wso2.carbon.security.caas.user.core.service.RealmService;
import org.wso2.carbon.security.caas.user.core.store.IdentityStore;
import org.wso2.msf4j.Microservice;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Identity management API in user core.
 */
@Component(
        name = "IdentityGateway",
        service = Microservice.class,
        immediate = true
)
@Path("/identity-gateway")
public class IdentityGateway implements Microservice {

    /**
     * Logger for IdentityGateway class.
     */
    private static final org.slf4j.Logger LOGGER =
            LoggerFactory.getLogger(IdentityGateway.class);

    /**
     * Actions when activating OSGI bundle.
     *
     * @param context BundleContext
     */
    @Activate
    protected void activate(BundleContext context) {

        LOGGER.info("Identity gateway activated.");
    }

    /**
     * Operations to perform when the bundle is de-activated.
     *
     * @param bundleContext bundle context
     */
    @Deactivate
    protected void deactivate(BundleContext bundleContext) {
        LOGGER.info("Identity gateway deactivated");
    }

    /**
     * Register realm service.
     *
     * @param carbonRealmService RealmService
     */
    @Reference(
            name = "org.wso2.carbon.security.CarbonRealmServiceImpl",
            service = RealmService.class,
            cardinality = ReferenceCardinality.AT_LEAST_ONE,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unRegisterCarbonRealm"
    )
    public void registerCarbonRealm(RealmService carbonRealmService) {
        IdentityDataHolder
                .getInstance()
                .registerCarbonRealmService(carbonRealmService);
        LOGGER.info("Realm service successfully registered.");
    }

    /**
     * Un register realm service.
     */
    public void unRegisterCarbonRealm(RealmService carbonRealmService) {
        IdentityDataHolder
                .getInstance()
                .unRegisterCarbonRealmServer();
        LOGGER.info("Realm service successfully unregistered.");
    }

    /**
     * Authenticate user.
     *
     * @return Response
     */
    @GET
    @Path("authenticate/")
    public Response authenticate() {

        try {
            PrivilegedCarbonContext.destroyCurrentContext();

            CarbonMessage carbonMessage = new DefaultCarbonMessage();
            carbonMessage
                    .setHeader("Authorization", "Basic " + Base64.getEncoder()
                            .encodeToString("admin:admin".getBytes("UTF-8")));
            ProxyCallbackHandler callbackHandler =
                    new ProxyCallbackHandler(carbonMessage);

            LoginContext loginContext =
                    new LoginContext("CarbonSecurityConfig", callbackHandler);
            loginContext.login();

        } catch (LoginException e) {
            return Response.serverError().build();
        } catch (UnsupportedEncodingException e) {
            return Response.serverError().build();
        }

        return Response.accepted().build();
    }

    /**
     * Get user authenticated.
     *
     * @param symbol String
     * @return Response
     */
    @GET
    @Path("get-user/{symbol}")
    @Produces({"application/json", "text/xml"})
    public Response getUser(@PathParam("symbol") String symbol) {

        IdentityStore identityStore =
                IdentityDataHolder
                        .getInstance()
                        .getCarbonRealmService()
                        .getIdentityStore();
        try {
            User user = identityStore.getUser(symbol);
            return Response
                    .ok(user.getUserId(), MediaType.APPLICATION_JSON_TYPE)
                    .build();
        } catch (IdentityStoreException e) {
            return Response.serverError().build();
        } catch (UserNotFoundException e) {
            return Response.serverError().build();
        }
    }

    /**
     * Operations to perform when starting micro-service.
     */
    @PostConstruct
    public void init() {

        LOGGER.info("Auth Service started successfully");
    }

    /**
     * Operations to perform when exiting micro-service.
     */
    @PreDestroy
    public void close() {

        LOGGER.info("Auth Service shutdown completed");
    }
}
