package application;

import application.model.Client;
import application.model.Event;
import application.request.ClientNameRequest;
import application.request.DisconnectRequest;
import application.request.NewEventRequest;
import application.request.Request;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GoodClientMock implements Runnable {

    private ObjectOutputStream os;
    private static final String NAME = "GOODClientMock";


    @Override
    public void run() {
        makeConnection();
    }


    private void makeConnection() {
        ClientOperational bankClient = new ClientOperational();

        try(Socket socket = new Socket("localhost", 6666)) {
            Client client = new Client(0, socket, NAME);
            Thread thread = new Thread(new ClientHandler(client));
            thread.start();

            ObjectOutputStream os = client.getOutput();
            os.writeObject(new ClientNameRequest(NAME));

            Request request;
            System.out.println("Normal client connected!");
              Event event = new Event();
              event.setName("Service of good client");
              event.setBook(false);
              event.setTimeFrom(parseDate("2018-01-12 13:00"));
              event.setTimeTo(parseDate("2018-01-12 14:00"));
              os.writeObject(new NewEventRequest(event));
              os.flush();

            System.out.println("Good client reached end!");
            os.writeObject( new DisconnectRequest());
            // use join to wait for thread for messages to finish
            thread.join();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }

    private LocalDateTime parseDate(String date) {
        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(date, dateTimeFormatter);
    }
}
