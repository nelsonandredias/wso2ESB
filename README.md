# wso2ESB
repository with several exercises about WSO2 ESB


## Enable WIRE LOGS 

The Wire logs play a major role in the troubleshooting process of the WSO2 servers.
Basically, with the wire logs we can get more informations about all incoming and outgoing messages in the runtime server.

In order to enable it, go to ***´\conf\log4j2.properties*** and do the following changes:

```
# Following are to log HTTP headers and messages
logger.synapse-transport-http-headers.name=org.apache.synapse.transport.http.headers
logger.synapse-transport-http-headers.level=DEBUG

logger.synapse-transport-http-wire.name=org.apache.synapse.transport.http.wire
logger.synapse-transport-http-wire.level=DEBUG

Then add the ‘synapse-wire’ to the ‘loggers’ section as below.
loggers = AUDIT_LOG, trace-messages, …. correlation, synapse-transport-http-wire
```


## Use Micro Integrator as a JMS Producer and Consumer in the ActiveMQ Broker

1. Install ActiveMQ in localmachine

2. Launch ActiveMQ broker by running the bat
```
C:\DevTools\apache-activemq-5.16.0\bin>activemq.bat start
```

***Note:*** if there is an error about JVM address already in use:
```
java.io.IOException: Transport Connector could not be registered in JMX: java.io.IOException: Failed to bind to server socket: amqp://0.0.0.0:5672?maximumConnections=1000&wireFormat.maxFrameSize=104857600 due to: java.net.BindException: Address already in use: JVM_Bind
```

In order to change the port, go to ***C:\DevTools\apache-activemq-5.16.0\conf\activemq.xml*** and update the following line:
```
<transportConnectors>
    <!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
    ...
    <transportConnector name="amqp" uri="amqp://0.0.0.0:5673?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    ...
</transportConnectors>
```

3. Launch ActiveMQ Broker Dashboard (localhost:8161)

***Note:*** the port could be changed in ***C:\DevTools\apache-activemq-5.16.0\conf\jetty.xml***
```
<bean id="jettyPort" class="org.apache.activemq.web.WebConsolePort" init-method="start">
    <!-- the default port number for the web console -->
    <property name="host" value="127.0.0.1"/>
    <property name="port" value="8161"/>
</bean>
```

4. Copy ActiveMQ jars libraries (C:\DevTools\apache-activemq-5.16.0\lib) to the respective lib folder of the Intregation Studio(C:\DevTools\IntegrationStudio\runtime\microesb\lib) or Micro Integrator(C:\Program Files\WSO2\Enterprise Integrator\7.1.0\micro-integrator\wso2\lib):
```
activemq-broker-5.8.0.jar
activemq-client-5.8.0.jar
activemq-kahadb-store-5.8.0.jar
geronimo-jms_1.1_spec-1.1.1.jar
geronimo-j2ee-management_1.1_spec-1.0.1.jar
geronimo-jta_1.0.1B_spec-1.0.1.jar
hawtbuf-1.9.jar
Slf4j-api-1.6.6.jar
activeio-core-3.1.4.jar (available in the ACTIVEMQ_HOME/lib/optional directory)
 ```


## Ex2- Store Message with WSO2 Broker queue

In order to avoid ***´error: no route for message error code 312: no route´***, it is important to create the testing queue in the WSO2 Broker: 

1. Update qpid-virtualhosts.xml:

This configuration file contains details of all queues and topics, and associated properties, to be created on broker startup. 

Add a new queue:
```
<queue>
  <name>my-simple-queue</name>
	<my-simple-queue>
	<exchange>amq.direct</exchange>
	<durable>true</durable>
	</my-simple-queue>
</queue>
```

Specify the queue type of exchange:
```
<exchange>
  <type>direct</type>
	<name>amq.direct</name>
	<durable>true</durable>
</exchange>
```
2. Update xis2.xml

Enable JMS Transport Receiver for WSO2 Broker
```
<!--Uncomment this and configure as appropriate for JMS transport support with WSO2 MB 2.x.x -->
   <transportReceiver name="jms" class="org.apache.axis2.transport.jms.JMSListener">
       <parameter name="myTopicConnectionFactory" locked="false">
          <parameter name="java.naming.factory.initial" locked="false">org.wso2.andes.jndi.PropertiesFileInitialContextFactory</parameter>
           <parameter name="java.naming.provider.url" locked="false">repository/conf/jndi.properties</parameter>
           <parameter name="transport.jms.ConnectionFactoryJNDIName" locked="false">TopicConnectionFactory</parameter>
           <parameter name="transport.jms.ConnectionFactoryType" locked="false">topic</parameter>
       </parameter>
  
       <parameter name="myQueueConnectionFactory" locked="false">
           <parameter name="java.naming.factory.initial" locked="false">org.wso2.andes.jndi.PropertiesFileInitialContextFactory</parameter>
           <parameter name="java.naming.provider.url" locked="false">repository/conf/jndi.properties</parameter>
           <parameter name="transport.jms.ConnectionFactoryJNDIName" locked="false">QueueConnectionFactory</parameter>
          <parameter name="transport.jms.ConnectionFactoryType" locked="false">queue</parameter>
       </parameter>
  
       <parameter name="default" locked="false">
           <parameter name="java.naming.factory.initial" locked="false">org.wso2.andes.jndi.PropertiesFileInitialContextFactory</parameter>
           <parameter name="java.naming.provider.url" locked="false">repository/conf/jndi.properties</parameter>
           <parameter name="transport.jms.ConnectionFactoryJNDIName" locked="false">QueueConnectionFactory</parameter>
           <parameter name="transport.jms.ConnectionFactoryType" locked="false">queue</parameter>
       </parameter>
   </transportReceiver>
```

Enable JMS Transport Sender

```
 <!-- uncomment this and configure to use connection pools for sending messages>-->
<transportSender name="jms" class="org.apache.axis2.transport.jms.JMSSender"/>
```
