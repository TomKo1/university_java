package application.reponse;

public enum KnownResponse implements Response {
	INVALID_ID(10, "ID is not valid.", Level.PRIVATE),
	PERMISSION_DENIED(20, "Access denied", Level.PRIVATE),
	BOOK_ERROR(30, "Service is already booked.", Level.PRIVATE),
	TIME_NOT_FREE(40, "Time is not free", Level.PRIVATE);

	private final int code;
	private final String message;
	private final Level level;
	
	KnownResponse(int code, String message, Level level) {
		this.code = code;
		this.message = message;
		this.level = level;
	}
	
	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	@Override
	public Level getLevel() {
		return level;
	}
	
	public static KnownResponse formCode(int code) {
		for(KnownResponse value : values()) {
			if(value.getCode() == code)
				return value;
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		return "(" + level + ") " + message;
	}
}
