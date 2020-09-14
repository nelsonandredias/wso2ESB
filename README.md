# wso2ESB
repository with several exercises about WSO2 ESB


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
