package at.rovo.camel.ws;

public class MessagingException extends Exception
{
	private static final long serialVersionUID = 1653675278481825514L;
	private String code;
	private String detail;
	private String reason;

	public MessagingException(String code)
	{
		super(code + " | Reason: " + ErrorCodes.getErrorCodeDescription(code));
		this.code = code;
		this.reason = ErrorCodes.getErrorCodeDescription(code);
	}
	
	public MessagingException(String code, String detail) 
	{
		super(code + " | Reason: " 
				+ ErrorCodes.getErrorCodeDescription(code) 
				+ " | Detail: "	+ detail);
		this.code = code;
		this.detail = detail;
		this.reason = ErrorCodes.getErrorCodeDescription(code);
	}
	
	public String getCode() 
	{
		return code;
	}
	
	public String getDetail() 
	{
		return detail;
	}
	
	public String getReason()
	{
		return reason;
	}
}
