# wso2ESB
repository with several exercises about WSO2 ESB


Ex2- Store Message with WSO2 Broker queue

Create a queue in the WSO2 Broker: 

Configuring qpid-virtualhosts.xml:
This configuration file contains details of all queues and topics, and associated properties, to be created on broker startup. 

Add a new queue:
<queue>
  <name>my-simple-queue</name>
	<my-simple-queue>
	<exchange>amq.direct</exchange>
	<durable>true</durable>
	</my-simple-queue>
</queue>

Specify the queue type of exchange:
<exchange>
  <type>direct</type>
	<name>amq.direct</name>
	<durable>true</durable>
</exchange>
