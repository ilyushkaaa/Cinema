import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CinemaHallVIP extends CinemaHall {

    private static int earned_money;

    public static int getEarned_money() {
        return earned_money;
    }
    public static void add_earned_money(int money){
        earned_money += money;
    }
    public static void serialize_money(ObjectOutputStream oos) throws IOException {
        oos.writeInt(earned_money);
    }
    public static int deserialize_money(ObjectInputStream ois) throws IOException {
        earned_money = ois.readInt();
        return earned_money;
    }




    public CinemaHallVIP(Entertainment entertainment, int hall_area, int num_of_places, Cinema cinema, int num_of_free_dishes, int money) {
        this.places = new int[1][num_of_places];
        int counter;
        for (counter = 0; counter < entertainment.getAll_cinema().size(); ++counter){
            if (cinema.equals(entertainment.getAll_cinema().get(counter))){
                break;
            }
        }
        cinema.setMax_visitors(cinema.getMax_visitors() + num_of_places);
        this.hall_area = hall_area;
        this.num_of_places = num_of_places;
        this.num_of_rows = 1;
        this.num_of_free_dishes = num_of_free_dishes;
        for (int i = 0; i < num_of_rows; ++i){
            for (int j = 0; j < num_of_places; ++j){
                this.places[i][j] = (i+1) * 10 + j +1;
            }
        }
        this.price_for_place = new int[num_of_rows][num_of_places];
        for (int k = 0; k < num_of_rows; ++k){
            for (int t = 0; t < num_of_places; ++t){
                this.price_for_place[k][t] = 1500;

            }
        }
        cinema.getOur_halls().add(this);
        cinema.setNum_of_halls(cinema.getNum_of_halls() + 1);
        earned_money = money;
        Type = "VIP";
        int num_of_hall = 0;
        for (int i = 0; i < cinema.getNum_of_halls(); ++i){
            if (cinema.getOur_halls().get(i).getType().equals("VIP")){
                ++num_of_hall;
            }
        }
        String num_of_hall_string = Integer.toString(num_of_hall);
        name = Type + counter + counter + counter + num_of_hall_string;
    }
    public void print_type_of_hall(){
        System.out.println("VIP");

    }
    public void print_hall_info(){
        System.out.println("Название зала говорит само за себя. В данном зале каждое место оборудовано удобными\n " +
                "креслами с режимом регулировки положения а так же вы получите несколько вкуснейших блюд, только\n " +
                "приготовленных нашими поварами. Кроме того, если вдруг вы захотите заказать ещё, вы можете заказать\n " +
                "дополнительно по доступным ценам");
        System.out.println("Количество бесплатных блюд на выбор: " + num_of_free_dishes);
    }
}
