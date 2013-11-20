package at.rovo.camel.ws.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import at.rovo.camel.ws.config.ServicesEndpoints;
import at.rovo.camel.ws.route.beans.GetDelivery;
import at.rovo.camel.ws.route.beans.SendFailureResponse;

public class DeliveryRoute extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		from(ServicesEndpoints.DELIVERY_SERVICE)
			.routeId("01-Delivery")
			.onException(Exception.class)
				.handled(true)
				.bean(SendFailureResponse.class)
			.end()
			
			.choice()
				.when(header(CxfConstants.OPERATION_NAME).isEqualTo("deliverMessage"))
					.to(ServicesEndpoints.DELIVER_MESSAGE)
				.when(header(CxfConstants.OPERATION_NAME).isEqualTo("listMessages"))
					.to(ServicesEndpoints.LIST_MESSAGES)
				.when(header(CxfConstants.OPERATION_NAME).isEqualTo("getMessageDetail"))
					.to(ServicesEndpoints.GET_MESSAGE_DETAIL)
			.endChoice()
		.end();
		
		from(ServicesEndpoints.DELIVER_MESSAGE)
			.bean(GetDelivery.class)
		.end();
		
		from(ServicesEndpoints.LIST_MESSAGES)
		.end();
		
		from(ServicesEndpoints.GET_MESSAGE_DETAIL)
		.end();
	}
}
