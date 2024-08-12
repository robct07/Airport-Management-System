import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


class Costumer {
    String name;
    String id;
    double money;
    int current_time;
    String passward;
    List<Ticket> current_tickets = new ArrayList<>();
    


    public Costumer(String name, String id, double money, int current_time, String passward) {
        this.name = name;
        this.id = id;
        this.money = money;
        this.current_time = current_time;
        this.passward = passward;
    }
    public Costumer(String name, String id, double money,int current_time,List<Ticket> current_tickets) {
        this.name=name;
        this.id=id;
        this.money=money;
        this.current_time=current_time;
        this.current_tickets=current_tickets;
    }

    public void bookTicket(AirlineManagementSystem ams) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Book by departure place/arrival place(1) or plane id(2)?");
        int choice=sc.nextInt();
        if (choice==1){
            System.out.println("Enter the departure place:");
            String departurePlace=sc.next();
            System.out.println("Enter the arrival place:");
            String arrivalPlace=sc.next();
            List<Flight> available_flights=ams.findFlights_by_departure_arrival_place(departurePlace, arrivalPlace);
            ams.showFlights_List(available_flights);
            
            System.out.println("Enter the flight id to book:");
            int flight_id=sc.nextInt();
            Flight targetFlight = null;
            targetFlight = ams.choose_flight(flight_id,available_flights,this.current_time);
                    System.out.println("Enter the class to book(1 for business, 2 for economy):");
                    int class_choice=sc.nextInt();
                    
                    if (class_choice==1){
                        if (targetFlight.remainingBusinessSeats>0){
                            if (this.current_time>targetFlight.departureTime-1){
                                System.out.println("Purchase time has passed!");
                            }
                            else{
                            if (this.money<Flight.businessPrice){
                                System.out.println("Not enough money!");
                                
                       
                            }
                    
                            targetFlight.remainingBusinessSeats--;
                            Ticket t = new Ticket(targetFlight, 1);
                            add_ticket(t, targetFlight);

                        }
                    }
                        else{
                            System.out.println("No business seats available!");
                        }
                    }
                    else if (class_choice==2){
                        if (targetFlight.remainingEconomySeats>0){
                            if (this.current_time>targetFlight.departureTime-1){
                                System.out.println("Purchase time has passed!");
                            }
                            else{
                            if (this.money<Flight.economyPrice){
                                System.out.println("Not enough money!");
                            }
                            targetFlight.remainingEconomySeats--;
                            Ticket t = new Ticket(targetFlight, 1);
                            add_ticket(t, targetFlight);
                        }
                    }
                        else{
                            System.out.println("No economy seats available!");
                        }

                    }
                    else{
                        System.out.println("Invalid choice!");
                    }



            
            
        

    
}
    else if (choice==2){
        System.out.println("Enter the flight id:");
        int flight_id=sc.nextInt();
        Flight flight=ams.findFlight_by_id(flight_id);
        if (flight==null){
            System.out.println("Invalid flight id!");
        }
        else{
            System.out.println("Enter the class to book(1 for business, 2 for economy):");
            int class_choice=sc.nextInt();
            if (class_choice==1){
                if (flight.remainingBusinessSeats>0){
                    if (this.current_time>flight.departureTime-1){
                        System.out.println("Purchase time has passed!");
                    }
                    else{
                    if (this.money<Flight.businessPrice){
                        System.out.println("Not enough money!");
                    }
                    flight.remainingBusinessSeats--;
                    current_tickets.add(new Ticket(flight, 1));
                    System.out.println("Booked successfully!");
                    flight.costumers.add(this);
                }
            }
                else{
                    System.out.println("No business seats available!");
                }
                
            }
            else if (class_choice==2){
                if (flight.remainingEconomySeats>0){
                    if (this.current_time>flight.departureTime-1){
                        System.out.println("Purchase time has passed!");
                    }
                    else{
                    if (this.money<Flight.economyPrice){
                        System.out.println("Not enough money!");
                    }
                    flight.remainingEconomySeats--;
                    current_tickets.add(new Ticket(flight, 2));
                    System.out.println("Booked successfully!");
                    flight.costumers.add(this);
                }
            }
                else{
                    System.out.println("No economy seats available!");
                }
            }
            else{
                System.out.println("Invalid choice!");
            }
        }
    }
    else{
        System.out.println("Invalid choice!");

    }


}
public Passenger switch_status(int current_time){
    return new Passenger(this.name, this.id, this.money, current_time,this.current_tickets);
}

public void cancelTicket(AirlineManagementSystem ams){
    Scanner sc=new Scanner(System.in);
    System.out.println("Enter the flight id to cancel:");
    int flight_id=sc.nextInt();
    Ticket target_ticket = delete_Ticket(flight_id);
    ams.delete_costumer(this, target_ticket);

}


public Ticket delete_Ticket(int flight_id){
    Ticket target_ticket = null;
    for (Ticket ticket:current_tickets){
        if (ticket.flight.flightId==flight_id){
            target_ticket = ticket;}else{
                System.out.println("No such flight with that id");
                break;
            }
}
        if (target_ticket.flight.departureTime<this.current_time+1){
            System.out.println("You can't cancel your flight at this time!");
        }
        else{
        if (target_ticket.checkInStatus){
            System.out.println("You have already checked in!");
        }
        else{
            if (target_ticket.seatlevel==1){
                target_ticket.flight.remainingBusinessSeats++;
            }
            else{
                target_ticket.flight.remainingEconomySeats++;
            }
            current_tickets.remove(target_ticket);
            System.out.println("Cancelled successfully!");
        }
    }

    return target_ticket;
}

public void add_ticket(Ticket t, Flight targetFlight){
    current_tickets.add(t);
    System.out.println("Booked successfully!");
    targetFlight.costumers.add(this);
}


}


