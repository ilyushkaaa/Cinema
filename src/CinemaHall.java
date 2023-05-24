import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class CinemaHall implements Serializable {
    protected String name;


    protected int num_of_free_dishes;
    protected String Type;

    protected int hall_area;
    protected int num_of_rows;
    protected int num_of_places;
    protected int[][] places;
    protected int[][] price_for_place;
    public int getHall_area() {
        return hall_area;
    }

    public int getNum_of_rows() {
        return num_of_rows;
    }

    public int getNum_of_places() {
        return num_of_places;
    }

    public int[][] getPlaces() {
        return places;
    }

    public void setPrice_for_place(int[][] price_for_place) {
        this.price_for_place = price_for_place;
    }

    public int[][] getPrice_for_place(){
        return price_for_place;
    }
    public void print_type_of_hall(){}
    public void print_hall_info(){}
    //private static int earned_money;
    public int getNum_of_free_dishes() {
        return num_of_free_dishes;
    }

    public String getType() {
        return Type;
    }

   /* public static int getEarned_money() {
        return 0;
    }
    public static void add_earned_money(int money){

    }*/

    public String getName() {
        return name;
    }
    public  void add_money(CinemaHall cinemaHall, int money){
        if(cinemaHall.getType().equals("2D")){
            CinemaHall2D.add_earned_money(money);
        }
        if(cinemaHall.getType().equals("3D")){
            CinemaHall3D.add_earned_money(money);
        }
        if(cinemaHall.getType().equals("7D")){
            CinemaHall7D.add_earned_money(money);
        }
        if(cinemaHall.getType().equals("Comfort")){
            CinemaHallComfort.add_earned_money(money);
        }
        if(cinemaHall.getType().equals("VIP")){
            CinemaHallVIP.add_earned_money(money);
        }
    }


}
