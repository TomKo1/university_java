package application.request;


import application.dao.EventDao;
import application.model.Event;
import application.reponse.CreateResponse;
import application.reponse.KnownResponse;
import application.reponse.Response;
import application.model.Client;

import java.util.concurrent.Semaphore;

public class NewEventRequest implements Request {
	private static final long serialVersionUID = 754456367039923742L;
	private Event event;
	private static final Semaphore semaphore = new Semaphore(1);
	
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
		Response response = null;
		try {
			semaphore.acquire();
		} catch(InterruptedException e) {
			return  KnownResponse.ERROR_CREATION;
		}
		boolean wynik = eventDao.checkIfTimeFree(event);
		System.out.println(Thread.currentThread().getName() + " " + wynik);
		if (wynik) {
			event.setOwner(executor);
			eventDao.create(event);
			response = new CreateResponse(1, "New service " + event + " created!", Response.Level.ALL);
		} else {
			response = KnownResponse.TIME_NOT_FREE;
		}
		semaphore.release();
		return response;
	}
}
