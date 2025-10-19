package refactoring;

public abstract class Ticket {
    public String passengerName;
    public String startLocation;
    public String destination;
    public double price;

    public Ticket(String passengerName, String startlocation, String destination, double price) {
        this.passengerName = passengerName;
        this.startLocation = startlocation;
        this.destination = destination;
        this.price = price;
    }

    // Method to display basic info passenger and trip
    public abstract void displayInfo();
}
