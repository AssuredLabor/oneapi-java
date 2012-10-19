package oneapi.scenarios;

import org.apache.log4j.BasicConfigurator;

import oneapi.PropertyLoader;
import oneapi.client.impl.SMSClient;
import oneapi.config.Configuration;
import oneapi.model.SMSRequest;
import oneapi.model.common.DeliveryInfoList;

/**
 * To run this example follow these 3 steps:
 *
 *  1.) Download 'OneApi C# library' - available at github.com/parseco
 *
 *  2.) Open 'scenarios.SendSMS_CheckDeliveryStatus' class to edit where you should populate the following fields: 
 *		'SENDER'    'USERNAME'
 *		'MESSAGE'   'PASSWORD' 
 *		'DESTINATION'	
 *
 *  3.) Run the example class by right click it and select 'Run As -> Java Application'     
 **/

public class SendSMS_CheckDeliveryStatus {

	// ----------------------------------------------------------------------------------------------------
	// TODO: Fill you own values here or create/change the example.properties file:
	// ----------------------------------------------------------------------------------------------------

	private static final String USERNAME = PropertyLoader.loadProperty("example.properties", "username");
	private static final String PASSWORD = PropertyLoader.loadProperty("example.properties", "password");
	private static String SENDER = PropertyLoader.loadProperty("example.properties", "sender");
	private static final String DESTINATION = PropertyLoader.loadProperty("example.properties", "destination");
	private static final String MESSAGE = "Hello"; 

	public static void main(String[] args) throws Exception {
		
		// Configure logger
		BasicConfigurator.configure();
		
		
		// example:initialize-sms-client
		Configuration configuration = new Configuration(USERNAME, PASSWORD);
		SMSClient smsClient = new SMSClient(configuration);
		// ----------------------------------------------------------------------------------------------------

		// example:prepare-message-without-notify-url
		SMSRequest smsRequest = new SMSRequest(SENDER, MESSAGE, DESTINATION);
		// ----------------------------------------------------------------------------------------------------

		// example:send-message
		// Store request id because we can later query for the delivery status with it:
		String requestId = smsClient.getSMSMessagingClient().sendSMS(smsRequest);
		// ----------------------------------------------------------------------------------------------------

		// Few seconds later we can check for the sending status   
		Thread.sleep(10000);

		// example:query-for-delivery-status
		DeliveryInfoList deliveryInfoList = smsClient.getSMSMessagingClient().queryDeliveryStatus(SENDER, requestId);
		String deliveryStatus = deliveryInfoList.getDeliveryInfo().get(0).getDeliveryStatus();
		// ----------------------------------------------------------------------------------------------------
		System.out.println(deliveryStatus);
		
	}
}
