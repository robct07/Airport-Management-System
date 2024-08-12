import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Passenger extends Costumer {

    double luggageWeight;

    public Passenger(String name, String id, double money, int current_time, List<Ticket> current_tickets) {
        super(name, id, money, current_time, current_tickets);

    }

    public void check_in(AirlineManagementSystem ams) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter the weight of your luggage:");
        this.luggageWeight = sc.nextDouble();
        ams.showTickets_List(this.current_tickets);
        System.out.println("Enter the flight id that you want to check in:");
        int flight_id = sc.nextInt();
        Ticket ticket = ams.choose_ticket(flight_id, this.current_tickets);
        ticket.flight.checkInStatus = (ticket.flight.departureTime < this.current_time + 1);    
        if (ticket.flight.checkInStatus){
            System.out.println("You can't check in because the gate is closed!");
        }
        else{
            change_checkin_status(ticket);
        }
            
        upgrade_seat(ticket);
    }



public void change_checkin_status(Ticket ticket){
    if (ticket.checkInStatus){
        System.out.println("You have already checked in!");
    }
    else{
        ticket.checkInStatus = true;
        if (this.luggageWeight > ticket.flight.luggageWeightThreshold){
            System.out.println("Your luggage is overweight!");
            int overweight = (int) (this.luggageWeight - ticket.flight.luggageWeightThreshold);
            int fee= overweight * 2;
            System.out.println("You need to pay " + overweight * 2 + " for the overweight luggage!");  
            if (this.money < fee){
                System.out.println("Not enough money!");

            }else{
                this.money -= fee;
                System.out.println("Additional Fee:"+fee+ " is charged, Check in successfully!");
            }
        }
        else{
        System.out.println("Check in successfully!");
        }
    }
}

public void upgrade_seat(Ticket ticket){
    Scanner sc = new Scanner(System.in);
    System.out.println("Exit(1) or upgrade the class of seat(2)?");
    int choice = sc.nextInt();
    if (choice == 2){
        if(ticket.seatlevel==1){
            System.out.println("You can't upgrade the class of seat because you are already at business class!");   
        }
        else{
            if (ticket.flight.remainingBusinessSeats > 0){
                if (this.money < Flight.businessPrice - Flight.economyPrice){
                    System.out.println("Not enough money!");
                }
                else{
                    this.money -= Flight.businessPrice - Flight.economyPrice;
                    ticket.seatlevel = 1;
                    ticket.flight.remainingBusinessSeats--;
                    ticket.flight.remainingEconomySeats++;
                    System.out.println("Upgrade successfully!");
                }
            }
            else{
                System.out.println("No more business class seats!");
            }
        }


}
}


} 
    

