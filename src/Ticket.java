import java.io.Serializable;
import java.util.Date;

public class Ticket implements Serializable, Comparable<Ticket> {
    @Override
    public int compareTo(Ticket o) {
        return this.date_of_purchase.compareTo(o.getDate_of_purchase());
    }

    private final Cinema cinema;
    private final Session session;
    private final CinemaHall cinemaHall;
    private final Film film;
    private final int row;
    private final int place;
    private Date date_of_purchase;
    private int price;

    public int getPrice() {
        return price;
    }

    public Date getDate_of_purchase() {
        return date_of_purchase;
    }

    public void setDate_of_purchase(Date date_of_purchase) {
        this.date_of_purchase = date_of_purchase;
    }

    public Ticket(Cinema cinema, Session session, CinemaHall cinemaHall, Film film, int row, int place, User client,
    Date date, int price){
        this.cinemaHall = cinemaHall;
        this.film = film;
        this.session = session;
        this.row = row;
        this.place = place;
        this.cinema = cinema;
        date_of_purchase = date;
        this.price = price;
        client.add_tickets(this);

    }

    public Cinema getCinema() {
        return cinema;
    }

    public Session getSession() {
        return session;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public Film getFilm() {
        return film;
    }

    public int getRow() {
        return row;
    }

    public int getPlace() {
        return place;
    }
}
