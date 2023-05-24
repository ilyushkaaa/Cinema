import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CinemaHall3D extends CinemaHall{
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


    public CinemaHall3D(Entertainment entertainment, int hall_area, int num_of_rows, int num_of_places, Cinema cinema, int money) {
        this.places = new int[num_of_rows][num_of_places];
        int counter;
        for (counter = 0; counter < entertainment.getAll_cinema().size(); ++counter){
            if (cinema.equals(entertainment.getAll_cinema().get(counter))){
                break;
            }
        }
        cinema.setMax_visitors(cinema.getMax_visitors() + num_of_places * num_of_rows);
        this.hall_area = hall_area;
        this.num_of_places = num_of_places;
        this.num_of_rows = num_of_rows;
        num_of_free_dishes = 0;
        for (int i = 0; i < num_of_rows; ++i){
            for (int j = 0; j < num_of_places; ++j){
                this.places[i][j] = (i+1) * 10 + j +1;
            }
        }
        this.price_for_place = new int[num_of_rows][num_of_places];
        int cheap_ticket = num_of_places / 3;
        for (int k = 0; k < num_of_rows; ++k){
            for (int t = 0; t < num_of_places; ++t){
                if(t < cheap_ticket || t >= num_of_places - cheap_ticket){
                    this.price_for_place[k][t] = 200;
                }
                else {
                    this.price_for_place[k][t] = 300;
                }

            }
        }
        cinema.getOur_halls().add(this);
        cinema.setNum_of_halls(cinema.getNum_of_halls() + 1);
        earned_money = money;
        Type = "3D";
        int num_of_hall = 0;
        for (int i = 0; i < cinema.getNum_of_halls(); ++i){
            if (cinema.getOur_halls().get(i).getType().equals("3D")){
                ++num_of_hall;
            }
        }
        String num_of_hall_string = Integer.toString(num_of_hall);
        name = Type + counter + counter + counter + num_of_hall_string;
    }
    public void print_type_of_hall(){
        System.out.println("3D hall");
    }
    public void print_hall_info(){
        System.out.println("В зале такие же кресла, как в эконом - зале, но в комплекте с билетом идут 3D очки\n" +
                "Насладитесь просмотром в формате 3D");
    }
}
