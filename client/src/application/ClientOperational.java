package application;

import application.model.Client;
import application.model.Event;
import application.request.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ClientOperational {
	private Scanner sc = new Scanner(System.in);
	private enum Operation { DELETE, BOOK, NBOOK };

	private void displayMenu() {
		String[] options = {
			"------MENU------",
				"1) Display all services",
				"2) Display my services",
				"3) Create new service",
				"4) Book new service",
				"5) Cancel booking",
				"6) Remove service",
				"7) Log out"
		};
		for(String option: options) {
			System.out.println(option);
		}
	}
	
	public Request menu() {
		int answer;
		
		do {
			displayMenu();
			System.out.print("Pick option: ");
			try {
				answer = Integer.parseInt(sc.nextLine());
			} catch(NumberFormatException e) {
				answer = 0;
			}
			
			switch(answer) {
				case 1:
					return new PrintAllRequest();
				case 2:
					return new PrintOwnRequest();
				case 3:
					return createEvent();
				case 4:
					return manageEvent(Operation.BOOK);
				case 5:
					return manageEvent(Operation.NBOOK);
				case 6:
					return manageEvent(Operation.DELETE);
				case 7:
					return new DisconnectRequest();
				default:
					System.out.println("No such option");
					break;
			}
		} while(true);
	}
	
	private Request createEvent() {
		String name = null;
		LocalDateTime dateFrom = null;
		LocalDateTime dateTo = null;
		while(name == null || name.isEmpty()) {
			System.out.print("Name of the service: ");
			name = sc.nextLine();
			dateFrom = askForDate("Date from in format yyyy-MM-dd HH:mm");
			dateTo = askForDate("Date to in format yyyy-MM-dd HH:mm");
		}
		
		Event event = new Event();
		event.setName(name);
		event.setBook(false);
		event.setTimeFrom(dateFrom);
		event.setTimeTo(dateTo);
		
		return new NewEventRequest(event);
	}
	
	private Request manageEvent(Operation operation) {
		int answer;
		while(true) {
			try {
				System.out.print("ID: ");
				answer = Integer.parseInt(sc.nextLine());
				
				if(answer > 0) break;
			} catch(NumberFormatException e) {
				System.out.println("This was not a number!");
			}
		}
		
		if(operation == Operation.BOOK)
			return new BookEventRequest(answer);
		else if(operation == Operation.NBOOK)
			return new NBookEventRequest(answer);
		else
			return new RemoveEventRequest(answer);
	}
	
	public static void main(String[] args) {
		ClientOperational bankClient = new ClientOperational();
		
		try(Socket socket = new Socket("localhost", 6666)) {
			Client client = new Client(0, socket);
			new Thread(new ClientHandler(client)).start();
			
			ObjectOutputStream os = client.getOutput();
			
			Request request;
			System.out.println("New connection established! Our server supports RODO! :)");
			while((request = bankClient.menu()) != null) {
				os.writeObject(request);
				os.flush();

				Thread.sleep(500);

				if(request instanceof DisconnectRequest) {
					break;
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	private LocalDateTime askForDate(String request) {
		boolean shouldRepeat;
		LocalDateTime dateTime = null;
		do {
			shouldRepeat = false;
			System.out.println(request);
			String dateStr = sc.nextLine();
			DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			try {
				dateTime = LocalDateTime.parse(dateStr, dateTimeFormatter);
			} catch(DateTimeParseException e) {
				System.out.println("The date is not in valid format.");
				shouldRepeat = true;
			}
		} while(shouldRepeat);

		return dateTime;
	}
}
