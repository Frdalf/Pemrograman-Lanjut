package refactoring;

public class MainApp {
    public static void main(String[] args) {
        TaxiTicket ticket = new TaxiTicket(new TicketInfo("Alice", "Downtown", "Airport", 50.0, 30.0, 60.0));

        ticket.detailedInfo(); // Display full info

        ticket.checkStatus(); // Check taxi status

        // Display route and estimated duration
        ticket.displayRoute();
        ticket.displayEstimatedDuration();

        // Simulate slowing down and speeding up
        ticket.slowDown(20);
        ticket.speedUp(15);
    }
}
