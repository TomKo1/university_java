package application.request;


import application.dao.EventDao;
import application.model.Client;
import application.model.Event;
import application.reponse.Response;
import application.reponse.UnknownResponse;

import java.util.List;

public class PrintAllRequest implements Request {
	private static final long serialVersionUID = -4730845050534233918L;

	@Override
	public Response execute(Client executor, EventDao eventDao) {
		List<Event> events = eventDao.findAll();
		
		StringBuilder sb = new StringBuilder();
		for(Event event : events) {
			sb.append(event + "\n");
		}
		
		return new UnknownResponse(0, sb.toString(), Response.Level.PRIVATE);
	}
}
