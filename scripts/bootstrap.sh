#!/usr/bin/env bash

# ------------------------------------------------------------------------
#
#  Copyright (c) 2005-2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

# Function to run docker images
# $1 is the docker file path
# $2 is the docker image name
# $3 is the service name
runDockerImage(){

    echo "Starting to run "${3}" micro-service ..."

    # Go to docker image location
    cd ${1}
    # Check for docker image
    # If the docker image exists ask user his / her choice
    if docker history -q ${2} >/dev/null 2>&1; then
        while true; do
            echo "Docker image "${2}" exists (service: "${3}"). Do you want to re-build docker image (Y/y or N/n)?"
            read -p "" yn
            case ${yn} in
                [Yy]* ) docker build -t ${2} .; break;;
                [Nn]* ) break;;
                * ) echo "Please answer (Y/y or N/n)";;
            esac
        done
    else
        # Build docker image
        docker build -t ${2} .
    fi

    # Run docker container
#    CONTAINER_ID=`docker run -d ${2}`
    CONTAINER_ID=`docker run ${2}`

    # Display docker container ip address information
    displayContainerIdAndIpAddress ${CONTAINER_ID}
}

# Function to display container ip address and container id
# $1 is docker container id
# $2 is the service name
displayContainerIdAndIpAddress(){

    # Get container ip address
    IP_ADDRESS=`docker inspect --format '{{ .NetworkSettings.IPAddress }}' ${1}`

    # Display container ip address and container id
    echo "Service "${2}" started: [IP_ADDRESS] ${IP_ADDRESS} [CONTAINER_ID] ${1}"
}

# Start auth service
runDockerImage ${DOCKER_PATH_AUTH_SERVICE} ${DOCKER_IMAGE_NAME_AUTH_SERVICE} ${DOCKER_SERVICE_NAME}
