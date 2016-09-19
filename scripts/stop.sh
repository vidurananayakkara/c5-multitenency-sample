#!/usr/bin/env bash

# ------------------------------------------------------------------------
#
#  Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
#
#  WSO2 Inc. licenses this file to you under the Apache License,
#  Version 2.0 (the "License"); you may not use this file except
#  in compliance with the License.
#  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
#
# ------------------------------------------------------------------------

# Change directory to the scripts directory
pushd $(dirname $0) > /dev/null
SCRIPT_PATH=$(pwd -P)
popd > /dev/null
cd ${SCRIPT_PATH}

# Exporting properties
source properties.sh

# Stop docker container
# $1 is the docker image name
stopDockerContainer(){

    echo "Stopping docker containers for "${1}" ..."
    docker ps -a | awk '{ print $1,$2 }' | grep ${1} | awk '{print $1 }' | xargs -I {} docker stop {}
}

# Remove docker container
# $1 is the docker image name
removeDockerContainer(){

    echo "Removing docker containers for "${1}" ..."
    docker ps -a | awk '{ print $1,$2 }' | grep ${1} | awk '{print $1 }' | xargs -I {} docker rm {}
}

# Stop and remove auth service
stopDockerContainer ${DOCKER_IMAGE_NAME_AUTH_SERVICE}
removeDockerContainer ${DOCKER_IMAGE_NAME_AUTH_SERVICE}
