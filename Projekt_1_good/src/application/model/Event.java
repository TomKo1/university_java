package application.model;

import java.io.Serializable;

public class Event implements Serializable {
	private static final long serialVersionUID = -2834156851804821525L;
	
	private Integer eventID;
	private String name;
	private boolean book;
	private Client owner;
	private Client booker;
	
	
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
	
	public Client getBooker() {
		return booker;
	}
	
	@Override
	public String toString() {
		return eventID + " -> " + name +  " " + (book ?  "[booked]  by client with id: "+ booker.getId() : "[not booked]");
	}
}
