import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Admin {
    String name;
    String workerId;
    int current_time;

    public Admin(String name, String workerId,int current_time) {
        this.name = name;
        this.workerId = workerId;
        this.current_time = current_time;

    }
    public void browseFlights(AirlineManagementSystem ams) {
        ams.showFlights_List(ams.flights);
    }   

    public void cancel(AirlineManagementSystem ams) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the flight id that you want to cancel:");
        int flight_id = sc.nextInt();
        Flight flight = ams.findFlight_by_id(flight_id);
        if (flight == null) {
            System.out.println("Flight not found!");
        } else {
            if (flight.departureTime < this.current_time + 2) {
                System.out.println("It is too late to cancel!");
            } else {
                ams.removeflight(flight,flight_id);
                writeFile(ams);
            }
        }

    }

    public void addFlight(AirlineManagementSystem ams) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the departure place:");
        String departurePlace = sc.next();
        System.out.println("Enter the departure time:");
        int departureTime = sc.nextInt();
        System.out.println("Enter the arrival place:");
        String arrivalPlace = sc.next();
        System.out.println("Enter the arrival time:");
        int arrivalTime = sc.nextInt();
        System.out.println("Enter the flight id:");
        int flightId = sc.nextInt();
        System.out.println("Enter the plane size:");
        int planeSize = sc.nextInt();
        System.out.println("Enter the company:");
        String company = sc.next();
        Flight flight = new Flight(departurePlace, departureTime, arrivalPlace, arrivalTime, flightId, planeSize, company);
        ams.addNew(flight);
        writeFile(ams);
    }
    
    public void change_departure_time(AirlineManagementSystem ams) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the flight id that you want to change departure time:");
        int flight_id = sc.nextInt();
        Flight flight = ams.findFlight_by_id(flight_id);
        ams.change_time(flight, this.current_time);
    }


    public void writeFile(AirlineManagementSystem ams) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.bin"))) {
            oos.writeObject(ams.flights);
            System.out.println("Flight list successfully saved to file.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


} 
    
