import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

class AirlineManagementSystem implements Serializable{
    List<Flight> flights;
    List<Costumer> costumers;
    List<Admin> admins;

    public AirlineManagementSystem() {
        this.flights = new ArrayList<>();
        this.costumers = new ArrayList<>();
        this.admins = new ArrayList<>();
    }

    // Method to add flight
    public void showTickets_List(List<Ticket> tickets) {
        // Header
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-10s %-15s %-10s %-15s\n",
                          "Flight ID", "Departure Place", "Departure Time", "Arrival Place", "Arrival Time", "Plane Size", "Company", "Seat Level", "Check In Status");
    
        // Rows
        for (Ticket ticket : tickets) {
            System.out.printf("%-15s %-15s %-15s %-15s %-15s %-10d %-15s %-10s %-15s\n",
                              ticket.flight.flightId, ticket.flight.departurePlace, ticket.flight.departureTime, ticket.flight.arrivalPlace, ticket.flight.arrivalTime, ticket.flight.planeSize, ticket.flight.company, ticket.seatlevel, ticket.checkInStatus);
        }
    }

    public void showFlights_List(List<Flight> flights) {
        // Header
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-10s %-15s\n",
                          "Flight ID", "Departure Place", "Departure Time", "Arrival Place", "Arrival Time", "Plane Size", "Company");
    
        // Rows
        for (Flight flight : flights) {
            System.out.printf("%-15s %-15s %-15s %-15s %-15s %-10d %-15s\n",
                              flight.flightId, flight.departurePlace, flight.departureTime, flight.arrivalPlace, flight.arrivalTime, flight.planeSize, flight.company);
        }
    }

    public List<Flight> findFlights_by_departure_arrival_place(String departurePlace, String arrivalPlace){ 
        List<Flight> available_flights = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.departurePlace.equals(departurePlace) && flight.arrivalPlace.equals(arrivalPlace)) {
                available_flights.add(flight);
            }else{
                System.out.println("No such flight");
            }
        }
        return available_flights;
    }

    public Flight findFlight_by_id(int flightId) {
        for (Flight flight : flights) {
            if (flight.flightId==flightId) {
                return flight;
            }
        }
        return null;
    }

    public Admin admin_login(){
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your name:");
            String name = sc.next();
            System.out.println("Enter your worker id:");
            String workerId = sc.next();
            System.out.println("Enter the current time:");
            int current_time = sc.nextInt();
            System.out.println("Enter the passward:");
            String passward = sc.next();
            while (!passward.equals("1234")) {
                System.out.println("Wrong passward! Enter Again:");
                passward = sc.next();
            }
            System.out.println("Login successfully!");
            return new Admin(name, workerId, current_time);
        

    }

    public Costumer costumer_login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name:");
        String name = sc.next();
        System.out.println("Enter your id:");
        String id = sc.next();
        System.out.println("Enter your money:");
        double money = sc.nextDouble();
        System.out.println("Enter the current time:");
        int current_time = sc.nextInt();
        System.out.println("Enter the passward:");
        String passward = sc.next();
        System.out.println("Login successfully!");
        return new Costumer(name, id, money, current_time, passward);
        
    }


    public void removeflight(Flight flight, int flight_id){
        this.flights.remove(flight);
        for (Costumer user : flight.costumers) {
            for (Ticket ticket : user.current_tickets) {
                if (ticket.flight.flightId == flight_id) {
                    user.current_tickets.remove(ticket);
                    if (ticket.seatlevel == 1) {
                        user.money += Flight.businessPrice;
                    } else {
                        user.money += Flight.economyPrice;
                    }

                }
            }
        }
        System.out.println("Flight cancelled successfully, and refund is applied");
    }

    public void addNew(Flight flight){
        this.flights.add(flight);
        System.out.println("Flight added successfully!");
    }

    public void change_time(Flight flight, int current_time){
        Scanner sc = new Scanner(System.in);
        if (flight == null) {
            System.out.println("Flight not found!");
        } else {
            if (flight.departureTime < current_time + 2) {
                System.out.println("It is too late to change departure time!");
            } else {
                System.out.println("Enter the new departure time:");
                int new_departure_time = sc.nextInt();
                int old_departure_time=flight.departureTime;
                flight.departureTime = new_departure_time;
                flight.arrivalTime=flight.arrivalTime+(new_departure_time-old_departure_time);
                System.out.println("Departure time changed successfully!");
            }
        }
    }

    public Flight choose_flight(int flight_id, List<Flight> available_flights, int current_time){
        for (Flight flight:available_flights){
            if (flight.flightId==flight_id){
                if (flight.departureTime<current_time+1){
                    System.out.println("The flight has already departed!");
                    return null;
                }
                else{
                    return flight;}
                }
            }
        return null;
    }

    public Ticket choose_ticket(int flight_id, List<Ticket> ticket_list){
        for (Ticket ticket : ticket_list) {
            if (ticket.flight.flightId == flight_id){
                return ticket;
            }
        }
        return null;
    }

    public void delete_costumer(Costumer costumer, Ticket target_ticket){
        target_ticket.flight.costumers.remove(costumer);
    }
    public void readFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.bin"))) {
            this.flights = (List<Flight>) ois.readObject();
            System.out.println("Flight list successfully loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }
    public static void main(String[] args){
        AirlineManagementSystem ams = new AirlineManagementSystem();
        ams.readFile();
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("1. Admin login");
            System.out.println("2. User login");
            System.out.println("3. Exit");
            int choice = sc.nextInt();
            if (choice == 1) {
                
                Admin admin = ams.admin_login();
                ams.admins.add(admin);
                while (true) {
                    System.out.println("1. Browse flights");
                    System.out.println("2. Cancel flight");
                    System.out.println("3. Add flight");
                    System.out.println("4. Change departure time");
                    System.out.println("5. Exit");
                    int admin_choice = sc.nextInt();
                    if (admin_choice == 1) {
                        admin.browseFlights(ams);
                    } else if (admin_choice == 2) {
                        admin.cancel(ams);
                    } else if (admin_choice == 3) {
                        admin.addFlight(ams);
                    } else if (admin_choice == 4) {
                        admin.change_departure_time(ams);
                    } else if (admin_choice == 5) {
                        break;
                    }
        

                }
                
            } else if (choice == 2) {
                Costumer user = ams.costumer_login();
                ams.costumers.add(user);
                while (true) {
                    System.out.println("1. Book ticket");
                    System.out.println("2. Check in");
                    System.out.println("3. Cancel");
                    System.out.println("4. Exit");
                    int user_choice = sc.nextInt();
                    if (user_choice == 1) {
                        user.bookTicket(ams);
                    } else if (user_choice == 2) {
                        System.out.println("Enter the current time:");
                        int current_time = sc.nextInt();
                        Passenger passenger=user.switch_status(current_time);
                        passenger.check_in(ams);
                    } else if (user_choice == 3) {
                        user.cancelTicket(ams);
                    } else if (user_choice == 4) {
                        break;
                    }
                }
                
            } else if (choice == 3) {
                break;
            }
        
        }
        

    }

    

}