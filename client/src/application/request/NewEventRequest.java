package application.request;


import application.dao.EventDao;
import application.model.Event;
import application.reponse.CreateResponse;
import application.reponse.KnownResponse;
import application.reponse.Response;
import application.model.Client;

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
		if(eventDao.checkIfTimeFree(event)) {
			event.setOwner(executor);
			eventDao.create(event);

			return new CreateResponse(1, "New service "+ event +" created!", Response.Level.ALL);
		} else {
			return KnownResponse.TIME_NOT_FREE;
		}
	}
}
