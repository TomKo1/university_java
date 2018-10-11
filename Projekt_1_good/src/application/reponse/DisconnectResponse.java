package application.reponse;

public class DisconnectResponse extends UnknownResponse {
	private static final long serialVersionUID = -7910912097512078074L;

	public DisconnectResponse(int code, String message, Level level) {
		super(code, message, level);
	}
}
