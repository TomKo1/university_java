package application;

import application.model.Client;
import application.model.Event;
import application.request.ClientNameRequest;
import application.request.DisconnectRequest;
import application.request.NewEventRequest;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MockBehaviour {

    public static void main(String[] args) {
        startSimulation();
    }

    private static void startSimulation() {
        try {
            Thread client1 = new Thread(new GoodClientMock());
            Thread client2 = new Thread(new BadClientMock());
            client1.start();
            client2.start();
            client1.join();
            client2.join();
            creationAtSameTime();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

     }

     //TODO: use ExecutorService
     private static void creationAtSameTime() throws InterruptedException {
        System.out.println("----------------");
        System.out.println("Creating objects at the same time creates only one");
        System.out.println("----------------");

        Runnable client1 = () -> {
            new MockBehaviour().makeCreationRequest("Service client1", "2019-01-12 13:00", "2019-01-12 14:00", "client1");
        };

        Runnable client2 = () -> {
            new MockBehaviour().makeCreationRequest("Service client2", "2019-01-12 13:10", "2019-01-12 13:55", "client2");
        };

        Thread client1Thread = new Thread(client1);
        Thread client2Thread = new Thread(client2);

        client1Thread.start();
        client2Thread.start();
        client1Thread.join();
        client2Thread.join();
     }

     private void makeCreationRequest(String name, String dateFrom, String dateTo, String nameClient) {
         try(Socket socket = new Socket("localhost", 6666)) {
             Client client = new Client(0, socket, nameClient);
             Thread thread = new Thread(new ClientHandler(client));
             thread.start();

             ObjectOutputStream os = client.getOutput();
             os.writeObject(new ClientNameRequest(nameClient));
             os.flush();

             Event event = new Event();
             event.setBook(false);
             event.setName(name);
             event.setTimeFrom(parseDate(dateFrom));
             event.setTimeTo(parseDate(dateTo));

             os.writeObject(new NewEventRequest(event));
             os.flush();

//             os.writeObject(new DisconnectRequest());
//             os.flush();

             thread.join();
         } catch(IOException | InterruptedException e) {
             e.printStackTrace();
         }
     }

    //TODO: extract this to second class
    private static LocalDateTime parseDate(String date) {
        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(date, dateTimeFormatter);
    }
}
