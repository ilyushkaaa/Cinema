import java.util.ArrayList;
import java.util.List;

public class Entertainment {
    private List <Cinema> all_cinema;
    private List<User> clients;
    private List<Film> all_films;
    private Admin admin;
    private Marketer marketer;
    private Discount discount;

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }


    public Marketer getMarketer() {
        return marketer;
    }

    public void setMarketer(Marketer marketer) {
        this.marketer = marketer;
    }




    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Film> getAll_films() {
        return all_films;
    }

    public List<User> getClients() {
        return clients;
    }
    public void add_clients(User client){
        clients.add(client);
    }
    public void delete_client (User client){
        for (int i = 0; i < clients.size(); ++i){
            if(client.equals(clients.get(i))){
                clients.remove(clients.get(i));
            }
        }
    }
    public void add_film(Film film){
        all_films.add(film);
    }
    public void printSessionsFilmName() {
        for (int i = 0; i < all_films.size(); ++i) {
            System.out.println(all_films.get(i).getName());
        }
    }

    public List<Cinema> getAll_cinema() {
        return all_cinema;
    }
    public void delete_cinema(int index){
        all_cinema.remove(index);
    }
    public void add_cinema(Cinema cinema){
        all_cinema.add(cinema);
    }

    public Entertainment() {
        all_cinema = new ArrayList<>();
        clients = new ArrayList<>();
        all_films = new ArrayList<>();

    }
}
