public class Ticket {
    Flight flight;
    //1 for business, 2 for economy
    boolean checkInStatus;
    int seatlevel;
    public Ticket(Flight flight, int seatlevel) {
        this.flight = flight;
        this.seatlevel = seatlevel;

    }
}
