package application.request;


import application.dao.EventDao;
import application.model.Client;
import application.model.Event;
import application.reponse.KnownResponse;
import application.reponse.Response;

public class NewEventRequest implements Request {
	private static final long serialVersionUID = 956456367039923741L;
	
	private Event event;
	
	public NewEventRequest(Event event) {
		this.event = event;
	}
	
	public void setEvent(Event event) {
		this.event = event;
	}
	
	public Event getEvent() {
		return event;
	}
	
	@Override
	public Response execute(Client executor, EventDao eventDao) {
		event.setOwner(executor);
		eventDao.create(event);
		
		return KnownResponse.CREATE_EVENT;
	}
}
