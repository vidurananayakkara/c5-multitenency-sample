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

import org.wso2.carbon.security.caas.user.core.service.RealmService;

/**
 * Data holder for identity gateway (Singleton class).
 */
final class IdentityDataHolder {

    /**
     * User Core API which is exposed to external users.
     * Each store can be accessed through the realm service
     */
    private RealmService carbonRealmService;

    /**
     * Identity holder singleton instance.
     */
    private static IdentityDataHolder instance = new IdentityDataHolder();

    /**
     * Private constructor.
     */
    private IdentityDataHolder() {
        super();
    }

    /**
     * Get instance of the identity data holder.
     *
     * @return IdentityDataHolder
     */
    static IdentityDataHolder getInstance() {

        return instance;
    }

    /**
     * Register carbon realm service.
     *
     * @param realmService RealmService
     */
    void registerCarbonRealmService(final RealmService realmService) {
        this.carbonRealmService = realmService;
    }

    /**
     * Un Register carbon realm service.
     */
    void unRegisterCarbonRealmServer() {
        this.carbonRealmService = null;
    }

    /**
     * Get carbon realm service.
     *
     * @return RealmService
     */
    RealmService getCarbonRealmService() {
        return this.carbonRealmService;
    }
}
