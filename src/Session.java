import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Session implements Serializable {
    private final CinemaHall our_CinemaHall;
    private final String film_name;
    private final String time;
    private boolean[][] free_places;
    private double discount;

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public CinemaHall getOur_CinemaHall() {
        return our_CinemaHall;
    }

    public String getFilm_name() {
        return film_name;
    }

    public String getTime() {
        return time;
    }

    public boolean[][] getFree_places() {
        return free_places;
    }

    public void setFree_places(boolean[][] free_places) {
        this.free_places = free_places;
    }

    public void delete_place(int row, int place){
        free_places[row][place] = false;
    }

    public Session(Entertainment entertainment,Cinema cinema, CinemaHall our_CinemaHall, String film_name, String time) {
        discount = 1.0;
        this.our_CinemaHall = our_CinemaHall;
        this.film_name = film_name;
        this.time = time;
        free_places = new boolean[our_CinemaHall.getNum_of_rows()][our_CinemaHall.getNum_of_places()];
        for (int i = 0; i < our_CinemaHall.getNum_of_rows(); ++i){
            for (int j = 0; j < our_CinemaHall.getNum_of_places(); ++j){
                free_places[i][j] = true;
            }
        }
        for (int i = 0; i < our_CinemaHall.getNum_of_places(); ++i){
            if (!cinema.getAll_prices().contains(our_CinemaHall.getPrice_for_place()[0][i])){
                cinema.add_price(our_CinemaHall.getPrice_for_place()[0][i]);
            }
        }
        if(!cinema.getAll_time().contains(time)){
            cinema.add_time(time);
        }
        for (int i = 0; i < entertainment.getAll_films().size(); ++i){
            if (entertainment.getAll_films().get(i).getName().equals(film_name)){
                if (!cinema.getFilms().contains(entertainment.getAll_films().get(i))){
                    cinema.add_film(entertainment.getAll_films().get(i));
                }
            }
        }

        cinema.add_session(this);
    }

    public Session(Entertainment entertainment,Cinema cinema, CinemaHall our_CinemaHall, String film_name, String time,
                   boolean[][] free_places) {
        discount = 1.0;
        this.our_CinemaHall = our_CinemaHall;
        this.film_name = film_name;
        this.time = time;
        this.free_places = new boolean[our_CinemaHall.getNum_of_rows()][our_CinemaHall.getNum_of_places()];
        for (int i = 0; i < our_CinemaHall.getNum_of_rows(); ++i){
            for (int j = 0; j < our_CinemaHall.getNum_of_places(); ++j){
                this.free_places[i][j] = free_places[i][j];
            }
        }
        for (int i = 0; i < our_CinemaHall.getNum_of_places(); ++i){
            if (!cinema.getAll_prices().contains(our_CinemaHall.getPrice_for_place()[0][i])){
                cinema.add_price(our_CinemaHall.getPrice_for_place()[0][i]);
            }
        }
        if(!cinema.getAll_time().contains(time)){
            cinema.add_time(time);
        }
        for (int i = 0; i < entertainment.getAll_films().size(); ++i){
            if (entertainment.getAll_films().get(i).getName().equals(film_name)){
                if (!cinema.getFilms().contains(entertainment.getAll_films().get(i))){
                    cinema.add_film(entertainment.getAll_films().get(i));
                }
            }
        }

        cinema.add_session(this);
    }
    public void printInfo(boolean a){
        System.out.print("Тип кинозала ");
        our_CinemaHall.print_type_of_hall();
        if (a){
            our_CinemaHall.print_hall_info();
        }
        System.out.println("Название фильма: " + film_name + "\nВремя сеанса: " + time + "\nСвободные места: ");
        for (int t = 0; t < our_CinemaHall.getNum_of_places(); ++t){
            int price = our_CinemaHall.price_for_place[0][t];
            System.out.print(price + " ");
        }
        System.out.print("\n");
        for (int i = 0; i < our_CinemaHall.getNum_of_rows(); ++i){
            for (int j = 0; j < our_CinemaHall.getNum_of_places(); ++j){

                if (free_places[i][j]){
                    System.out.print(our_CinemaHall.getPlaces()[i][j] + "  ");
                }
                else {
                    System.out.print("x   ");
                }
            }
            System.out.print("\n");
        }
    }
    public void printPrices(int price, boolean a){
        System.out.print("Тип кинозала ");
        our_CinemaHall.print_type_of_hall();
        if (a){
            our_CinemaHall.print_hall_info();
        }
        System.out.println("Название фильма: " + film_name + "\nВремя сеанса: " + time +
                "\nСвободные места по выбранной цене ");
        for (int i = 0; i < our_CinemaHall.getNum_of_rows(); ++i){
            for (int j = 0; j < our_CinemaHall.getNum_of_places(); ++j){
                if (free_places[i][j] && our_CinemaHall.getPrice_for_place()[i][j] == price){
                    System.out.print(our_CinemaHall.getPlaces()[i][j] + "  ");
                }
                else {
                    System.out.print("x   ");
                }
            }
            System.out.print("\n");
        }
    }

    public void printChosenSessionInfo(Entertainment entertainment, Cinema cinema){
        if (discount != 1.0){
            System.out.println("Акция! На все билеты этого сеанса действует скидка " + (1.0 - discount) * 100 +
                    " процентов");
        }
        if (entertainment.getDiscount().isDiscount_is_on()){
            if (entertainment.getDiscount().getType_of_discount().equals("hall_discount")){
                if (this.our_CinemaHall.equals(entertainment.getDiscount().getHall_discount())){
                    System.out.println("Этот сеанс проходит в кинозале, на который распространяется акция! " +
                            "Скидка на все билеты - " + (1 - entertainment.getDiscount().getOld_discount()) * 100 +
                            " процентов!");
                }
            }
        }
        System.out.println("Выбранный сеанс");
        for (int d = 0; d <  cinema.getFilms().size(); ++d){
            if (getFilm_name().equals(
                    cinema.getFilms().get(d).getName())){
                cinema.getFilms().get(d).PrintFilmInfo();
            }
        }
        printInfo(true);

    }
    public void printChosenSessionInfoPriceMode(Cinema cinema, int price){
        System.out.println("Выбранный сеанс");
        for (int d = 0; d <  cinema.getFilms().size(); ++d){
            if (getFilm_name().equals(
                    cinema.getFilms().get(d).getName())){
                cinema.getFilms().get(d).PrintFilmInfo();
            }
        }
        printPrices(price, true);

    }

}
