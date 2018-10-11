package application.request;


import application.dao.EventDao;
import application.model.Event;
import application.reponse.Response;
import application.reponse.UnknownResponse;
import application.model.Client;

import java.util.List;

public class PrintOwnRequest implements Request {
	private static final long serialVersionUID = 938798505900150333L;

	@Override
	public Response execute(Client owner, EventDao eventDao) {
		List<Event> events = eventDao.findByOwner(owner);
		
		StringBuilder sb = new StringBuilder();
		for(Event event : events) {
			sb.append(event + "\n");
		}
		
		return new UnknownResponse(0, sb.toString(), Response.Level.PRIVATE);
	}
}
