package application;

import application.model.Client;
import application.model.Event;
import application.request.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// "normalny" użytkownik
public class GoodClientMock implements Runnable {

    private ObjectOutputStream os;
    private static final String NAME = "GOODCLIENTMOCK";


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

            System.out.println("Normal client connected!");

            simulateServiceCreation();
            simulateServiceBooking();

            os.writeObject( new DisconnectRequest());
            os.flush();
            // use join to wait for thread for messages to finish
            thread.join();
            System.out.println("Good client reached end!");
        } catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void simulateServiceCreation() throws IOException {
        Event event = new Event();
        event.setName("Service of good client");
        event.setBook(false);
        event.setTimeFrom(parseDate("2018-01-12 13:00"));
        event.setTimeTo(parseDate("2018-01-12 14:00"));
        os.writeObject(new NewEventRequest(event));
        os.flush();
    }

    private void simulateServiceBooking() throws IOException, InterruptedException {
        // warning: magic numbers
        Request request = new BookEventRequest(1);
        System.out.println("Good client thinks which object to book, in the same time the object that he wants to book was booked");
        Thread.sleep(5000);
        os.writeObject(request);
        os.flush();
    }


    //TODO: Extract it to separate class
    private LocalDateTime parseDate(String date) {
        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(date, dateTimeFormatter);
    }
}
