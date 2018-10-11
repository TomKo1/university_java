package application;


import application.dao.EventStorage;
import application.model.Client;
import application.reponse.DisconnectResponse;
import application.reponse.Response;
import application.request.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientHandler implements Runnable {
	private Client client;
	private Dispatcher dispatcher;
	
	public ClientHandler(Client client, Dispatcher dispatcher) {
		this.client = client;
		this.dispatcher = dispatcher;
	}
	
	@Override
	public void run() {		
		try {
			ObjectOutputStream os = client.getOutput();
			ObjectInputStream is = client.getInput();
			
			System.out.println("New client with id: " + client.getId() + " connected");
			
			Request request;
			while( (request = (Request) is.readObject()) != null) {
				Response response
					= request.execute(client, EventStorage.getInstance());
				
				if(response.getLevel() == Response.Level.PRIVATE) {
					os.writeObject(response);
					os.flush();
				}
				else if(response.getLevel() == Response.Level.ALL) {
					// when message is supposed to go to every
					// client use dispatcher
					dispatcher.addResponse(response);
				}
				
				if(response instanceof DisconnectResponse) {
					break;
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			dispatcher.removeClient(client);
			try {
				client.disconnect();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}