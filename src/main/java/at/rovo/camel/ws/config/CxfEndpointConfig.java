package at.rovo.camel.ws.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.xml.namespace.QName;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.CxfSpringEndpoint;
import org.apache.camel.component.cxf.DataFormat;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import at.rovo.camel.ws.EnhancedDeliveryEndpoint;

@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
public class CxfEndpointConfig
{
	@Resource
	protected Environment env;
	
	private final List<String> schemas = new ArrayList<>();
	
	public CxfEndpointConfig()
	{					
		schemas.add("classpath:/wsdl/hub/Message.xsd");
		schemas.add("classpath:/wsdl/hub/MessageBody.xsd");
		schemas.add("classpath:/wsdl/hub/EBDH.xsd");
		schemas.add("classpath:/wsdl/hub/Failure.xsd");
		schemas.add("classpath:/wsdl/hub/Acknowledgment.xsd");
		schemas.add("classpath:/wsdl/hub/messaginghub.xsd");
	}
	
	@Bean(name = "deliveryService")
	public CxfEndpoint deliveryService() throws Exception 
	{
		final CxfSpringEndpoint svrFactory = new CxfSpringEndpoint();		
		svrFactory.setSchemaLocations(schemas);
		svrFactory.setAddress(env.getProperty("services.deliveryfetch.path")+"/delivery");
		svrFactory.setServiceClass(EnhancedDeliveryEndpoint.class);
		svrFactory.setWsdlURL("classpath:/wsdl/hub.wsdl");
		svrFactory.setEndpointName(new QName("http://namespace.hub.com/hub", "DeliveryServicePort", "hub"));
		svrFactory.setServiceName(new QName("http://namespace.hub.com/hub", "DeliveryService", "hub"));
		svrFactory.setDataFormat(DataFormat.POJO);
		final Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("schema-validation-enabled", "true");
		properties.put("allowStreaming", "true");

		svrFactory.setProperties(properties);
		svrFactory.getInInterceptors().add(new LoggingInInterceptor());
		svrFactory.getOutInterceptors().add(new LoggingOutInterceptor());
		return svrFactory;
	}
}
