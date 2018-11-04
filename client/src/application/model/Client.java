package application.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	private int id;
	private final Socket socket;
	private ObjectOutputStream output = null;
	private ObjectInputStream input = null;
	
	public Client(int id, Socket socket) throws IOException {
		this.id = id;
		this.socket = socket;
	}

	public int getId() {
		return id;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public ObjectOutputStream getOutput() throws IOException {
		return output == null ? output = new ObjectOutputStream(socket.getOutputStream()) : output;
	}

	public ObjectInputStream getInput() throws IOException {
		return input == null ? input = new ObjectInputStream(socket.getInputStream()) : input;
	}
	
	public void disconnect() throws IOException {
		if(input != null) input.close();
		if(output != null) output.close();
		if(socket != null) socket.close();
	}
}
