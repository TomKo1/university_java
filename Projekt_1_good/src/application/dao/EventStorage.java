package application.dao;

import application.model.Client;
import application.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EventStorage implements EventDao {
	private static final AtomicInteger count = new AtomicInteger(0);
	private static volatile EventStorage instance = null;
	private List<Event> events;
	
	private EventStorage() {
		events	= new ArrayList<Event>();
	}
	
	public static EventStorage getInstance() {
		if(instance == null) {
			synchronized(EventStorage.class) {
				if(instance == null)
					instance = new EventStorage();
			}
		}
		
		return instance;
	}
	
	@Override
	public Event findById(Integer id) throws IndexOutOfBoundsException {
		Event result = null;
		for(Event event : events) {
			if(event.getEventID().equals(id))
				result = event;
		}
		
		if(result == null)
			throw new IndexOutOfBoundsException();
		
		return result;
	}
	
	@Override
	public List<Event> findByOwner(Client owner) {
		List<Event> ownerEvents = new ArrayList<Event>();
		for(Event event : events) {
			if(event.getOwner() == owner)
				ownerEvents.add(event);
		}
		
		return ownerEvents;
	}
	
	@Override
	public List<Event> findAll() {
		return events;
	}
	
	@Override
	public synchronized void create(Event event) {
		if(event.getEventID() == null)
			event.setEventID(count.incrementAndGet());
		
		events.add(event);
	}
	
	@Override
	public synchronized void delete(Event event) {
		events.remove(event);
	}
	
	@Override
	public synchronized void book(Event event) throws UnsupportedOperationException {
		if(event.isBook())
			throw new UnsupportedOperationException();
		
		event.setBook(true);
	}
	
	@Override
	public synchronized void nbook(Event event) {
		if(!event.isBook())
			throw new UnsupportedOperationException();
		
		event.setBook(false);
	}

	@Override
	public synchronized boolean checkIfTimeFree(Event event) {
		for(Event e : events) {
			if( (event.getTimeFrom().equals(e.getTimeFrom()) || event.getTimeFrom().isAfter(e.getTimeFrom())) && (event.getTimeTo().equals(e.getTimeTo()) || event.getTimeTo().isBefore(e.getTimeTo()))) {
				return false;
			}
		}
		return true;
	}
}
