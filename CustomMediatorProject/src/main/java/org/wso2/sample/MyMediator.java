package org.wso2.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext; 
import org.apache.synapse.mediators.AbstractMediator;

public class MyMediator extends AbstractMediator { 

	
	private static final Log log = LogFactory.getLog(MyMediator.class);
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean mediate(MessageContext context) { 
		
		String apiName = context.getProperty("SYNAPSE_REST_API").toString(); //get API NAME
        String name = "APIName::" + apiName + "::"; //get 
		 
		System.out.println(sayHello(getMessage()));
		log.info(sayHello(getMessage()));
		return true;
	}
	
	public String sayHello(String message) {
		return "The message is " + message;
	}
}
