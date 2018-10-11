package application;


import application.model.Client;
import java.io.IOException;
import java.net.ServerSocket;

public class ServerInitialize {
	public static void main(String[] args) {		
		try(ServerSocket serverSocket = new ServerSocket(6666)) {
		    // dispatcher manages connection of clients
            // and message sending
			Dispatcher dispatcher = new Dispatcher();
			new Thread(dispatcher).start();
			// id of client
            System.out.println("Running on localhost:6666");
			int i = 0;
			while(true) {
				try {
					Client client = new Client(i++, serverSocket.accept());
					new Thread(new ClientHandler(client, dispatcher)).start();
					dispatcher.addClient(client);
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
