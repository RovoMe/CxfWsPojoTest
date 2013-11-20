package at.rovo.camel.ws.route.beans;

import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.rovo.camel.ws.ErrorCodes;
import at.rovo.camel.ws.MessagingException;
import com.hub.namespace.hub.AbstractRequestType.RequestMetaData;
import com.hub.namespace.hub.DeliverMessageRequest;

public class GetDelivery
{
	private static final Logger LOG = LoggerFactory.getLogger(GetDelivery.class);
			
	public void getDelivery(@Headers Map<String, Object> headers, Exchange exchange)
		throws Exception
	{
		DeliverMessageRequest request =
				exchange.getIn().getBody(DeliverMessageRequest.class);
		
		RequestMetaData metaData = request.getRequestMetaData();
		String appKey = metaData.getAppKey();
		
		if (!"Test".equals(appKey))
		{
			LOG.warn("Invalid application key received: {}", appKey);
			throw new MessagingException(ErrorCodes.APPKEY_INVALID, 
					"Invalid application key found:" + appKey);
		}
		
		String sender = request.getDeliveryParameters().getSender();
		String receiver = request.getDeliveryParameters().getSender();
		
		if (sender == null || sender.equals(""))
		{
			LOG.warn("Invalid sender name received");
			throw new MessagingException(ErrorCodes.SENDER_INVALID, 
					"Invalid sender found");
		}
		
		if (receiver == null || receiver.equals(""))
		{
			LOG.warn("INVALID receiver name received");
			throw new MessagingException(ErrorCodes.RECEIVER_INVALID, 
					"Invalid receiver found");
		}
		
		
	}
}
