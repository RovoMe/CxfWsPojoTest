package at.rovo.camel.ws.config;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.management.DefaultManagementAgent;
import org.apache.camel.spi.ManagementAgent;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.apache.cxf.bus.spring.SpringBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import at.rovo.camel.ws.route.DeliveryRoute;

@Configuration
@Import({ CxfEndpointConfig.class })
public class ServiceSpringConfig extends CamelConfiguration 
{
	private static final Logger LOG = LoggerFactory.getLogger(ServiceSpringConfig.class);
	
	@Resource
	protected Environment env;
	
	@Resource(name = "cxf")
	protected SpringBus bus;
	
	@Override
	protected void setupCamelContext(CamelContext camelContext) throws Exception {
		super.setupCamelContext(camelContext);

        /*
		 * Starting the agent is required in order to get the
		 * XMLSchemaCacheMBean added to the JMX context. why ever this is
		 * needed...
		 */
        final ManagementAgent agent = new DefaultManagementAgent(camelContext);
        agent.setCreateConnector(true);
        agent.setUsePlatformMBeanServer(true);
        camelContext.getManagementStrategy().setManagementAgent(agent);

        // and a simple naming pattern for all the other Camel JMX managed parts
        camelContext.getManagementNameStrategy().setNamePattern("#name#");

        camelContext.setUseMDCLogging(true);
        camelContext.setUseBreadcrumb(true);

		camelContext.setUseMDCLogging(true);
		camelContext.setUseBreadcrumb(true);

		final PropertiesComponent pc = new PropertiesComponent("classpath:"	+ env.getProperty("propertyfile"));
		camelContext.addComponent("properties", pc);
		
		LOG.info("Camel context initiated");
	}

	@Override
	public List<RouteBuilder> routes()
	{
		List<RouteBuilder> routes = new ArrayList<>(1);
		routes.add(new DeliveryRoute());
		return routes;
	}
}
