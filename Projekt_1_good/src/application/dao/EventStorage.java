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
		System.out.println(Thread.currentThread().getName() + " tworzy " +event.getName());
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
			if(timeNotFreeHelper(e, event) || timeNotFreeHelper(event, e)){
				return false;
			}
		}
		return true;
	}

	// todo: sortowac eventy po datach jakos
	private boolean timeNotFreeHelper(Event e1, Event e2) {
		if((e1.getTimeFrom().equals(e2.getTimeFrom()) || e1.getTimeFrom().isAfter(e2.getTimeFrom())) && (e1.getTimeTo().equals(e2.getTimeTo()) || e1.getTimeTo().isBefore(e2.getTimeTo()))) {
			return true;
		}
		return false;
	}
}
