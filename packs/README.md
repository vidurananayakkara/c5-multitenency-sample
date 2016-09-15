Installing WSO2 MSF4J (Micro-Services Framework for Java) framework into WSO2 Carbon Kernel
===========================================================================================

Download the latest source code of the WSO2 Carbon Kernel. This can be done via

* Download the WSO2 Carbon Kernel source code zip file from https://github.com/wso2/carbon-kernel

<center>OR</center>

* Git clone WSO2 Carbon Kernel
    * Navigate to the directory you want to git clone WSO2 Carbon Kernel
    * Open a terminal window and type `git clone https://github.com/wso2/carbon-kernel.git`
    
The directory of the WSO2 Carbon Kernel sources (typically known as "carbon-kernel") will referred to as <PRODUCT_HOME> from here onwards.

Follow the following steps in order to install the WSO2 MSF4J feature and it's dependencies to the WSO2 Carbon Kernel.

* Navigate to <PRODUCT_HOME>/distribution directory.

* Open pom.xml file and do the following changes

    * Add the following properties to the "properties" section of the pom.xml file. This is to specify the versions of the artifacts being used in the solution. (Versions may change. You may also specify version numbers in the `version` tag for appropriate package)
    
    ```xml
            <carbon.datasources.version>1.0.0</carbon.datasources.version>
            <carbon.jndi.version>1.0.0</carbon.jndi.version>
            <carbon.messaging.version>1.0.4</carbon.messaging.version>
            <carbon.metrics.version>2.0.1-SNAPSHOT</carbon.metrics.version>
            <carbon.transport.http.netty.version>2.1.2</carbon.transport.http.netty.version>
            <msffourj.version>2.0.0</msffourj.version>
    ```

    * Add the following dependencies to the "dependencies" section of the pom.xml file
    
    ```xml
            <!-- MSF4J -->
            <!-- Dependancies -->
            <dependency>
                <groupId>org.wso2.carbon.metrics</groupId>
                <artifactId>org.wso2.carbon.metrics.core.feature</artifactId>
                <version>${carbon.metrics.version}</version>
                <type>zip</type>
            </dependency>
            <dependency>
                <groupId>org.wso2.carbon.metrics</groupId>
                <artifactId>org.wso2.carbon.metrics.jdbc.core.feature</artifactId>
                <version>${carbon.metrics.version}</version>
                <type>zip</type>
            </dependency>
            <dependency>
                <groupId>org.wso2.carbon.metrics</groupId>
                <artifactId>org.wso2.carbon.metrics.das.core.feature</artifactId>
                <version>${carbon.metrics.version}</version>
                <type>zip</type>
            </dependency>
    
            <dependency>
                <groupId>org.wso2.carbon.messaging</groupId>
                <artifactId>org.wso2.carbon.messaging.feature</artifactId>
                <version>${carbon.messaging.version}</version>
                <type>zip</type>
            </dependency>
    
            <dependency>
                <groupId>org.wso2.carbon.jndi</groupId>
                <artifactId>org.wso2.carbon.jndi.feature</artifactId>
                <version>${carbon.jndi.version}</version>
                <type>zip</type>
            </dependency>
    
            <dependency>
                <groupId>org.wso2.carbon.datasources</groupId>
                <artifactId>org.wso2.carbon.datasource.core.feature</artifactId>
                <version>${carbon.datasources.version}</version>
                <type>zip</type>
            </dependency>
    
            <dependency>
                <groupId>org.wso2.carbon.transport</groupId>
                <artifactId>org.wso2.carbon.transport.http.netty.feature</artifactId>
                <version>${carbon.transport.http.netty.version}</version>
                <type>zip</type>
            </dependency>
    
            <!-- MSF4J -->
            <dependency>
                <groupId>org.wso2.msf4j</groupId>
                <artifactId>org.wso2.msf4j.feature</artifactId>
                <version>${msffourj.version}</version>
                <type>zip</type>
            </dependency>
    
    ```


    * Navigate to the "plugins" section of the pom.xml file and find the plugin with the following information
    
    **Group ID:** `org.wso2.carbon.maven`
    **Artifact ID:** `carbon-feature-plugin`
    
    Add the following features inside `executions` > `execution` (goal = "generate-repo") > `configuration` > `features` element
    
    ``` xml
            <!-- MSF4J Features -->
            <feature>
                <id>org.wso2.carbon.metrics.core.feature</id>
                <version>${carbon.metrics.version}</version>
            </feature>
            <feature>
                <id>org.wso2.carbon.metrics.jdbc.core.feature</id>
                <version>${carbon.metrics.version}</version>
            </feature>
            <feature>
                <id>org.wso2.carbon.metrics.das.core.feature</id>
                <version>${carbon.metrics.version}</version>
            </feature>
            <feature>
                <id>org.wso2.carbon.messaging.feature</id>
                <version>${carbon.messaging.version}</version>
            </feature>
            <feature>
                <id>org.wso2.carbon.jndi.feature</id>
                <version>${carbon.jndi.version}</version>
            </feature>
            <feature>
                <id>org.wso2.carbon.datasource.core.feature</id>
                <version>${carbon.datasources.version}</version>
            </feature>
            <feature>
                <id>org.wso2.carbon.transport.http.netty.feature</id>
                <version>${carbon.transport.http.netty.version}</version>
            </feature>

            <!-- MSF4J -->
            <feature>
                <id>org.wso2.msf4j.feature</id>
                <version>${msffourj.version}</version>
            </feature>
    ```


    * Navigate to the "plugins" section of the pom.xml file and find the plugin with the following information (same plugin as the previous step)
    
    **Group ID:** `org.wso2.carbon.maven`
    **Artifact ID:** `carbon-feature-plugin`
    
    Add the following features inside `executions` > `execution` (goal = "install") > `configuration` > `features` element
    
    ``` xml
            <!-- MSF4J feature -->
            <feature>
                <id>org.wso2.carbon.metrics.core.feature.group</id>
                <version>${carbon.metrics.version}</version>
            </feature>
            <feature>
                <id>org.wso2.carbon.metrics.jdbc.core.feature.group</id>
                <version>${carbon.metrics.version}</version>
            </feature>
            <feature>
                <id>org.wso2.carbon.metrics.das.core.feature.group</id>
                <version>${carbon.metrics.version}</version>
            </feature>
            <feature>
                <id>org.wso2.carbon.messaging.feature.group</id>
                <version>${carbon.messaging.version}</version>
            </feature>

            <feature>
                <id>org.wso2.carbon.jndi.feature.group</id>
                <version>${carbon.jndi.version}</version>
            </feature>
            <feature>
                <id>org.wso2.carbon.datasource.core.feature.group</id>
                <version>${carbon.datasources.version}</version>
            </feature>
            <feature>
                <id>org.wso2.carbon.transport.http.netty.feature.group</id>
                <version>${carbon.transport.http.netty.version}</version>
            </feature>

            <!-- MSF4J -->
            <feature>
                <id>org.wso2.msf4j.feature.group</id>
                <version>${msffourj.version}</version>
            </feature>
    ```
    
* Build WSO2 Carbon Kernel by opening a terminal window and typing `mvn clean install`. The following prerequisites are required to complete this step 

    * Oracle Java SE Development Kit (JDK) version 1.8.*
    * Apache Maven version 3.3.x
    
* Navigate to <PRODUCT_HOME>/distribution/target and find the `wso2carbon-kernel-{version}.zip` file. This version of the WSO2 Carbon Kernel now has the WSO2 MSF4J feature installed.

* In order to run the WSO2 Carbon Kernel, extract the above zip file to a location of preference, navigate to the /bin directory and run `carbon.sh` (Linux) or `carbon.bat` (Windows).

Installing WSO2 Carbon Security CAAS feature into WSO2 Carbon Kernel
--------------------------------------------------------------------

* Install WSO2 MSF4J feature to WSO2 Carbon Kernel as instructed above.

* Open pom.xml file and do the following changes

    * Add the following properties to the "properties" section of the pom.xml file. This is to specify the versions of the artifacts being used in the solution. (Versions may change. You may also specify version numbers in the `version` tag for appropriate package)
    
    ```xml
            <org.wso2.carbon.caching.version>1.1.2-SNAPSHOT</org.wso2.carbon.caching.version>
    ```

    * Add the following dependencies to the "dependencies" section of the pom.xml file
    
    ```xml
            <!-- org.wso2.carbon.security.caas -->
            <dependency>
                <groupId>org.wso2.carbon.caching</groupId>
                <artifactId>org.wso2.carbon.caching.feature</artifactId>
                <version>${org.wso2.carbon.caching.version}</version>
                <type>zip</type>
            </dependency>
    
    ```


    * Navigate to the "plugins" section of the pom.xml file and find the plugin with the following information
    
    **Group ID:** `org.wso2.carbon.maven`
    **Artifact ID:** `carbon-feature-plugin`
    
    Add the following features inside `executions` > `execution` (goal = "generate-repo") > `configuration` > `features` element
    
    ``` xml
            <!-- org.wso2.carbon.security.caas -->
            <feature>
                <id>org.wso2.carbon.caching.feature</id>
                <version>${org.wso2.carbon.caching.version}</version>
            </feature>
    ```


    * Navigate to the "plugins" section of the pom.xml file and find the plugin with the following information (same plugin as the previous step)
    
    **Group ID:** `org.wso2.carbon.maven`
    **Artifact ID:** `carbon-feature-plugin`
    
    Add the following features inside `executions` > `execution` (goal = "install") > `configuration` > `features` element
    
    ``` xml
            <!-- org.wso2.carbon.security.caas -->
            <feature>
                <id>org.wso2.carbon.caching.feature.group</id>
                <version>${org.wso2.carbon.caching.version}</version>
            </feature>
    ```

