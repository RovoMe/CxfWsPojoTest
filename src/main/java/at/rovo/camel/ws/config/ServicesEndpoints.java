package at.rovo.camel.ws.config;

public class ServicesEndpoints
{
	public static final String DELIVERY_SERVICE = "cxf:bean:deliveryService";
	
	public static final String DELIVER_MESSAGE = "direct:deliverMessage";
	
	public static final String LIST_MESSAGES = "direct:listMessages";
	
	public static final String GET_MESSAGE_DETAIL = "direct:getMessageDetail";
}
