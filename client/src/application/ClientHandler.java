package application;


import application.model.Client;
import application.reponse.DisconnectResponse;
import application.reponse.Response;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientHandler implements Runnable {
	private Client client;
	
	public ClientHandler(Client client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		try {
			ObjectInputStream is = client.getInput();
		
			Response response;
			while((response = (Response) is.readObject()) != null) {
				System.out.println("\n" + response.getMessage());
				
				if(response instanceof DisconnectResponse) {
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				client.disconnect();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

}
