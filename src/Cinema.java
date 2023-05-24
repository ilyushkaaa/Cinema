import java.io.Serializable;
import java.util.*;

public class Cinema implements Serializable {
    private int num_of_halls;
    private String name;
    private int max_visitors;
    private String address;
    private String[] formats;
    private int sell_tickets;
    private int earned_money;
    private List<CinemaHall> our_halls;
    private List<Session> sessions;
    private  List<Film> films;

    private List<Integer> all_prices;

    private List<String> all_time;
    public void delete_film(int j){
        films.remove(j);
    }

    public int getEarned_money() {
        return earned_money;
    }

    public void setEarned_money(int earned_money) {
        this.earned_money = earned_money;
    }

    public int getSell_tickets() {
        return sell_tickets;
    }

    public void setSell_tickets(int sell_tickets) {
        this.sell_tickets = sell_tickets;
    }

    public String getName() {
        return name;
    }

    public int getMax_visitors() {
        return max_visitors;
    }

    public String getAddress() {
        return address;
    }

    public String[] getFormats() {
        return formats;
    }

    public List<CinemaHall> getOur_halls() {
        return our_halls;
    }


    public List<String> getAll_time() {
        return all_time;
    }


    public List<Integer> getAll_prices() {
        return all_prices;
    }

    public void add_price(Integer price){
        all_prices.add(price);
    }

    public void add_session(Session session){
        sessions.add(session);
    }
    public void add_film(Film film){
        films.add(film);
    }
    public void add_time(String time){
        all_time.add(time);
    }
    public void add_cinemaHall(CinemaHall cinemaHall){
        our_halls.add(cinemaHall);
    }

    public void printSessionsTime() {

        System.out.println(all_time);
    }

    public void printSessionsFilmName() {
        for (int i = 0; i < films.size(); ++i) {
            for (int j = 0; j < sessions.size(); ++j){
                if (films.get(i).getName().equals(sessions.get(j).getFilm_name())){
                    System.out.println(films.get(i).getName());
                    break;
                }
            }
        }
    }


    public List<Session> getSessions() {
        return sessions;
    }

    public List<Film> getFilms() {
        return films;
    }

    public int getNum_of_halls() {
        return num_of_halls;
    }

    public void setNum_of_halls(int num_of_halls) {
        this.num_of_halls = num_of_halls;
    }


    public Cinema(String name, String address, String[] formats, Entertainment entertainment, int sell_tickets,
                  int earned_money) {
        this.name = name;
        this.address = address;
        this.max_visitors = 0;
        this.formats = formats;
        this.sell_tickets = sell_tickets;
        this.earned_money = earned_money;
        sessions = new ArrayList<>();
        films = new ArrayList<>();
        if (entertainment.getAll_cinema().size() != 0){
            films.addAll(entertainment.getAll_cinema().get(0).getFilms());
        }

        our_halls = new ArrayList<>();
        num_of_halls = 0;
        all_prices = new ArrayList<>();
        all_time = new ArrayList<>();
        entertainment.add_cinema(this);

    }

    public void setMax_visitors(int max_visitors) {
        this.max_visitors = max_visitors;
    }

    /*public int recieve_hall_index(Session session){
        int index = 0;
        for (int i = 0; i < our_halls.size(); ++i){
            if (session.getOur_CinemaHall().equals(our_halls.get(i))){
                index = i;
            }
        }
        return index;
    }*/
    public Session delete_session(int index){
        return sessions.remove(index);
    }
    public void delete_time(String time){
        all_time.remove(time);
    }
    public void delete_cinemaHall(int index){
        our_halls.remove(index);
        num_of_halls--;
    }
    public void delete_price(int index){
        all_prices.remove(index);
    }

    public void confirm_ticket(List<Session> good_sessions, int ch_session_int, int ch_place_int, int money_earn){
        //int hall_index = recieve_hall_index(good_sessions.get(ch_session_int - 1));
        System.out.println("Билет куплен. Спасибо за покупку.");
        int real_index = 0;
        for (int i = 1; i <  getSessions().size(); ++i){
            if (good_sessions.get(ch_session_int - 1).getFilm_name().
                    equals(getSessions().get(i).getFilm_name())
                    && good_sessions.get(ch_session_int - 1).getTime().
                    equals(getSessions().get(i).getTime())){
                real_index = i;
            }
        }
        getSessions().get(real_index).delete_place(ch_place_int / 10 - 1,
                ch_place_int % 10 - 1);
        setSell_tickets(getSell_tickets() + 1);
        setEarned_money(getEarned_money()
                + money_earn);

    }
}
