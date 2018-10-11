package application;


import application.model.Client;
import application.reponse.Response;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Dispatcher implements Runnable {
	private List<Client> clients;
	// blocking queue because multiple threads put there
	private BlockingQueue<Response> responses;
	
	public Dispatcher() {
		this.clients= new LinkedList<Client>();
		this.responses= new LinkedBlockingQueue<Response>();
	}
	
	public synchronized void addClient(Client client) {
		clients.add(client);
	}
	
	public synchronized void removeClient(Client client) {
		clients.remove(client);
	}
	
	public synchronized void addResponse(Response response) {
		try {
			responses.put(response);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void sendToAll(Response response) throws IOException {
		for(Client client : clients) {
			client.getOutput().writeObject(response);
			client.getOutput().flush();
		}
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				sendToAll(responses.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
