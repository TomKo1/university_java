package application.simulation;

import application.ClientHandler;
import application.model.Client;
import application.model.Event;
import application.request.BookEventRequest;
import application.request.DisconnectRequest;
import application.request.NewEventRequest;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MockBehaviour {

    public static void main(String[] args) {
        MockBehaviour mockBehaviour = new MockBehaviour();
        mockBehaviour.startSimulation();
    }

    private  void startSimulation() {
        try {
            creationAtSameTime();
            makeBookingAthTheSameTime();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

     }

     //TODO: use ExecutorService
     private void creationAtSameTime() throws InterruptedException {
         printSimulationInformation("Creating event at the same time books only one");

         Runnable client1 = () -> {
            makeCreationRequest("Service client1", "2019-01-12 13:00", "2019-01-12 14:00", "client1");
        };

        Runnable client2 = () -> {
           makeCreationRequest("Service client2", "2019-01-12 13:10", "2019-01-12 13:55", "client2");
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
             Client client = new Client(0, socket);
             Thread thread = new Thread(new ClientHandler(client));
             thread.start();

             ObjectOutputStream os = client.getOutput();

             Event event = new Event();
             event.setBook(false);
             event.setName(name);
             event.setTimeFrom(parseDate(dateFrom));
             event.setTimeTo(parseDate(dateTo));

             os.writeObject(new NewEventRequest(event));
             os.flush();

               /*
                THIS REQUEST SOMETIMES CAUSES:
                - StreamCorruptedException
                - SocketClosed - server side
                - BrokenPipe - server side
                POSSIBLE ISSUE:
                - socket is on one side (probably server) is closed
                earlier - wrong synchronization
              */
             os.writeObject(new DisconnectRequest());
             os.flush();

             thread.join();
         } catch(IOException | InterruptedException e) {
             e.printStackTrace();
         }
     }

     private void makeBookingAthTheSameTime()  throws InterruptedException  {
         printSimulationInformation("Booking event at the same time books only one");

         Runnable client1 = () -> {
             makeBookingRequest();
         };

         Runnable client2 = () -> {
             makeBookingRequest();
         };

         Thread client1Thread = new Thread(client1);
         Thread client2Thread = new Thread(client2);

         // bad idea
         client1Thread.start();
         client2Thread.start();
         client1Thread.join();
         client2Thread.join();
     }

     private void  makeBookingRequest() {
         try(Socket socket = new Socket("localhost", 6666)) {
             Client client = new Client(0, socket);
             Thread thread = new Thread(new ClientHandler(client));
             thread.start();

             ObjectOutputStream os = client.getOutput();


             os.writeObject(new BookEventRequest(1));
             os.flush();

            /*
                THIS REQUEST SOMETIMES CAUSES:
                - StreamCorruptedException
                - SocketClosed - server side
                - BrokenPipe - server side
                POSSIBLE ISSUE:
                - socket is on one side (probably server) is closed
                earlier - wrong synchronization
                and second side writes/reads to/from it
              */
             os.writeObject(new DisconnectRequest());
             os.flush();

             thread.join();
         } catch(IOException | InterruptedException e) {
             e.printStackTrace();
         }
     }

     private void printSimulationInformation(String simulationInfo) {
         System.out.println("----------------");
         System.out.println(simulationInfo);
         System.out.println("----------------");
     }

    // odrezerwowywanie
    // usuwanie
    // printAllRequest
    //TODO: extract this to another class
    private static LocalDateTime parseDate(String date) {
        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(date, dateTimeFormatter);
    }
}
