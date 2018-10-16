package application.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Event implements Serializable {
	private static final long serialVersionUID = -2834156851804821525L;
	
	private Integer eventID;
	private String name;
	private boolean book;
	private Client owner;
	private Client booker;
	private LocalDateTime timeFrom;
	private LocalDateTime timeTo;

	public void setTimeFrom(LocalDateTime timeFrom) {
		this.timeFrom = timeFrom;
	}

	public void setTimeTo(LocalDateTime timeTo) {
		this.timeTo = timeTo;
	}

	public void setEventID(Integer eventID) {
		this.eventID = eventID;
	}
	
	public Integer getEventID() {
		return eventID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setBook(boolean book) {
		this.book = book;
	}
	
	public boolean isBook() {
		return book;
	}
	
	public void setOwner(Client owner) {
		this.owner = owner;
	}
	
	public Client getOwner() {
		return owner;
	}	
	
	public void setBooker(Client booker) {
		this.booker = booker;
	}

	public LocalDateTime getTimeFrom() {
		return timeFrom;
	}

	public LocalDateTime getTimeTo() {
		return timeTo;
	}
	
	public Client getBooker() {
		return booker;
	}

	@Override
	public String toString() {
		return eventID + " -> " + name +  " created by client with id: "+ owner.getId() + " " +(book ?  "[booked]  by client with id: "+ booker.getId() : "[not booked]");
	}
}
