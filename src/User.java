import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public interface User {
    void choose_session(Cinema cinema, Entertainment entertainment);
    String getPhone_number();
    int getVisits();
    void add_tickets(Ticket ticket);
    int choose_cinema(Entertainment entertainment);
    String getName();
    String getPatronymic();
    static void serializeCount(ObjectOutputStream oos){

    }
    static int deserializeCount (ObjectInputStream ois){
        return 100;

    }
    void setDiscount(double discount);
    double getDiscount();
    String getType();


    void setVisits(int visits);

    void setName(String name);

    String getSurname();

    void setSurname(String surname);

    void setPhone_number(String phone_number);
    String getEmail();

    void setEmail(String email);

    int getMoney();

    List<Ticket> getTickets();
}
