package application;

public class MockBehaviour {

    public static void main(String[] args) {
        simulateSameTimeReservation();
    }

    public static void simulateSameTimeReservation() {
        new Thread(new GoodClientMock()).start();
        new Thread(new BadClientMock()).start();
    }

}
