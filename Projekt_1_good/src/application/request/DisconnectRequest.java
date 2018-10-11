package application.request;


import application.dao.EventDao;
import application.model.Client;
import application.reponse.DisconnectResponse;
import application.reponse.Response;

public class DisconnectRequest implements Request {
	private static final long serialVersionUID = -3796160609126657776L;

	@Override
	public Response execute(Client executor, EventDao eventDao) {
		return new DisconnectResponse(100, "Goodbye", Response.Level.PRIVATE);
	}
	
}
