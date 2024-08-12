import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Flight implements Serializable{
    String departurePlace;
    int departureTime;
    String arrivalPlace;
    int arrivalTime;
    int flightId;
    //price for business and economy class is independent of the plane size and the flight company
    static int businessPrice = 1000;
    static int economyPrice = 500;
    int planeSize;//size has 1,2,3 as the size
    //remaining seats is highly depend on the plane size
    int remainingBusinessSeats;
    int remainingEconomySeats;
    //can passenger still check in or not
    boolean checkInStatus;
    List<Costumer> costumers;
    String company;
    static int luggageWeightThreshold = 30;
    public Flight(String departurePlace, int departureTime, String arrivalPlace, int arrivalTime, int flightId, int planeSize, String company) {
        this.departurePlace = departurePlace;
        this.departureTime = departureTime;
        this.arrivalPlace = arrivalPlace;
        this.arrivalTime = arrivalTime;
        this.flightId = flightId;
        this.planeSize = planeSize;
        this.company = company;
        this.costumers = new ArrayList<>();
        this.checkInStatus = false;
        //assign the remaining seats based on the plane size 
        switch(planeSize) {
            case 1:
                this.remainingBusinessSeats = 10;
                this.remainingEconomySeats = 50;
                break;
            case 2:
                this.remainingBusinessSeats = 20;
                this.remainingEconomySeats = 100;
                break;
            case 3:
                this.remainingBusinessSeats = 30;
                this.remainingEconomySeats = 150;
                break;
        }
    }
    
}