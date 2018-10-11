package application.dao;


import application.model.Client;
import application.model.Event;

import java.util.List;

public interface EventDao {
	Event findById(Integer id);
	List<Event> findByOwner(Client owner);
	List<Event> findAll();
	void create(Event event);
	void delete(Event event);
	void book(Event event);
	void nbook(Event event);
}
