package at.rovo.camel.ws.route.beans;

import java.net.HttpURLConnection;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.cxf.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.rovo.camel.ws.ErrorCodes;
import at.rovo.camel.ws.MessagingException;
import com.hub.namespace.failure.Failure;
import com.hub.namespace.hub.MessagingFault;

public class SendFailureResponse
{
	private static final Logger LOG = 
			LoggerFactory.getLogger(SendFailureResponse.class);
	
	@Handler 
	public void sendFailureResponse(final Exception ex, final Exchange exchange)
		throws Exception
	{
		Failure failure;
		if (ex instanceof MessagingException)
		{
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			final MessagingException mex = (MessagingException) ex;
			failure = new Failure();
			failure.setCode(mex.getCode());
			failure.setReason(mex.getReason());
			failure.setDetail(mex.getDetail());
			failure.setTimestamp(datatypeFactory.newXMLGregorianCalendar(new GregorianCalendar()));
			if (ErrorCodes.USER_NOT_PERMITTED.equals(mex.getCode()))
			{
				exchange.getOut().setHeader(Message.RESPONSE_CODE, HttpURLConnection.HTTP_FORBIDDEN);
			}
		}
		else
		{
			LOG.error(
					"An unexpected (i.e., non-MessagingException occured. The user will receive an "
							+ ErrorCodes.INTERNAL_SERVER_ERROR + ". See the stacktrace below:\n ",
							ex);
			failure = new Failure();
			failure.setCode(ErrorCodes.INTERNAL_SERVER_ERROR);
		}
		
		MessagingFault fault = new MessagingFault(ex.getMessage(), failure);
		exchange.getOut().setBody(fault);
		exchange.getOut().setFault(true);
		
		LOG.debug("Created failure: {} for exception {}", fault, ex.getMessage());
	}
}
