package at.rovo.camel.ws;

import javax.jws.WebService;
import org.apache.cxf.annotations.FastInfoset;
import org.apache.cxf.annotations.GZIP;
import org.apache.cxf.annotations.SchemaValidation;
import com.hub.namespace.hub.DeliveryEndpoint;

@WebService(targetNamespace = "http://namespace.hub.com/hub", name = "DeliveryEndpoint")
@FastInfoset
@SchemaValidation(enabled = true)
@GZIP(threshold = 500)
public interface EnhancedDeliveryEndpoint extends DeliveryEndpoint {

}
