package application;

import application.model.Client;
import application.model.Event;
import application.request.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// "uposledzony" uzytkownik, ktory wszystko opoznia
public class BadClientMock implements Runnable {
    private ObjectOutputStream os;
    private static final String NAME = "BADClientMock";

    @Override
    public void run() {
        makeConnection();
    }


    private void makeConnection() {
        try(Socket socket = new Socket("localhost", 6666)) {
            Client client = new Client(0, socket, NAME);
            Thread thread = new Thread(new ClientHandler(client));
            thread.start();

            os = client.getOutput();
            os.writeObject(new ClientNameRequest(NAME));
            os.flush();

            simulateServiceCreation();
            simulateServiceBooking();

            os.writeObject(new DisconnectRequest());
            os.flush();
            thread.join();
            System.out.println("Bad client reached end!");
        } catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void simulateServiceCreation() throws IOException, InterruptedException {
        System.out.println("Bad client connected!");
        Event event = new Event();
        event.setName("Service of bad client");
        event.setBook(false);
        System.out.println("Bad client thinks about the date - in this time good client should create event in the same period of time");
        Thread.sleep(5000);
        event.setTimeFrom(parseDate("2018-01-12 13:05"));
        event.setTimeTo(parseDate("2018-01-12 14:00"));
        os.writeObject(new NewEventRequest(event));
        os.flush();
    }

    private void simulateServiceBooking() throws IOException {
        Request request = new BookEventRequest(1);
        os.writeObject(request);
        os.flush();
    }

    //TODO: Extract it to separate class
    private LocalDateTime parseDate(String date) {
        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(date, dateTimeFormatter);
    }
}
