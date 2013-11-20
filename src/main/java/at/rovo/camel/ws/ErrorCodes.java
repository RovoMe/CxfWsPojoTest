package at.rovo.camel.ws;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodes
{
	public static final String APPKEY_INVALID = "100";
	public static final String SENDER_INVALID = "260";
    public static final String RECEIVER_INVALID = "261";
    public static final String DOUBLE_SUBMISSION = "361";
    public static final String USER_NOT_PERMITTED = "104";
    public static final String INTERNAL_SERVER_ERROR = "300";
    
    private static final Map<String, String> errorCodeDescriptions;
	
    static
    {
    	errorCodeDescriptions = new HashMap<>();
    	errorCodeDescriptions.put(SENDER_INVALID, "SENDER_INVALID");
    	errorCodeDescriptions.put(RECEIVER_INVALID, "RECEIVER_INVALID");
    	errorCodeDescriptions.put(DOUBLE_SUBMISSION, "DOUBLE_SUBMISSION");
    	errorCodeDescriptions.put(USER_NOT_PERMITTED, "USER_NOT_PERMITTED");
    	errorCodeDescriptions.put(INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR");
    	errorCodeDescriptions.put(APPKEY_INVALID, "APPKEY_INVALID");
    }
    
	public static String getErrorCodeDescription(String code)
	{
		return errorCodeDescriptions.get(code);
	}
}
