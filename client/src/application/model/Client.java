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
	private String name;
	
	public Client(int id, Socket socket, String name) throws IOException {
		this.id = id;
		this.socket = socket;
	}

	public Client(int id, Socket socket) throws IOException {
		this.id = id;
		this.socket = socket;
		this.name = name;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
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
