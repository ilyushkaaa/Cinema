import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CinemaHall7D extends CinemaHall{
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

    public CinemaHall7D(Entertainment entertainment, int hall_area, int num_of_rows, int num_of_places, Cinema cinema, int money) {
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
                    this.price_for_place[k][t] = 350;
                }
                else {
                    this.price_for_place[k][t] = 450;
                }

            }
        }
        cinema.getOur_halls().add(this);
        cinema.setNum_of_halls(cinema.getNum_of_halls() + 1);
        earned_money = money;
        Type = "7D";
        int num_of_hall = 0;
        for (int i = 0; i < cinema.getNum_of_halls(); ++i){
            if (cinema.getOur_halls().get(i).getType().equals("7D")){
                ++num_of_hall;
            }
        }
        String num_of_hall_string = Integer.toString(num_of_hall);
        name = Type + counter + counter + counter + num_of_hall_string;
    }
    public void print_type_of_hall(){
        System.out.println("7D hall");

    }
    public void print_hall_info(){
        System.out.println("Получите истинное наслаждение и эффект присутствия в фильме с ультра - новой технологией" +
                " 7D!\n" + "Вы получите уникальные 3D очки, а также множество захватывающих эмоций от таких эффектов, " +
                "как\n " + "двигающееся кресло, соответствие природным условием и много - много другого, создающего" +
                " эффкт\n" + " нахождения в самом фильме! ");
    }

}
