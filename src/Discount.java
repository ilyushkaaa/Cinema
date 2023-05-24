import java.io.Serializable;
import java.util.Date;

public class Discount implements Serializable {
    private double discount_friend;
    private double discount_VIP;

    private double discount_simple;
    private int num_of_tickets_discount;
    private String type_of_discount;

    private double old_discount;
    private String type_of_client_discount;
    private CinemaHall hall_discount;
    private Date date_of_discount;
    private Session session_discount;



    public Session getSession_discount() {
        return session_discount;
    }
    public void print_discount_info(User user){
        if (discount_is_on){
            if (type_of_discount.equals("client_discount")){
                if (user.getType().equals(getType_of_client_discount())){
                    System.out.println("Для вас на все сеансы действует скидка на все билеты в размере " +
                            (1-user.getDiscount()) * 100 + " процентов");
                }
            }
            else {
                if (type_of_discount.equals("hall_discount")){
                    System.out.println("На все билеты на сеансы, которые проходят в кинозале " + hall_discount.getName()
                    + " действует скидка в размере " + (1 - old_discount) * 100 + " процентов");
                }
                else {
                    System.out.println("На все билеты на сеанс " + session_discount.getFilm_name() + " " +
                            session_discount.getTime()
                            + " действует скидка в размере " + (1 - old_discount) * 100 + " процентов");
                }
            }
        }
    }

    public void setSession_discount(Session session_discount) {
        this.session_discount = session_discount;
    }

    public Date getDate_of_discount() {
        return date_of_discount;
    }

    public void setDate_of_discount(Date date_of_discount) {
        this.date_of_discount = date_of_discount;
    }

    public CinemaHall getHall_discount() {
        return hall_discount;
    }

    public void setHall_discount(CinemaHall hall_discount) {
        this.hall_discount = hall_discount;
    }

    public void setType_of_client_discount(String type_of_client_discount) {
        this.type_of_client_discount = type_of_client_discount;
    }

    private boolean discount_is_on;

    public boolean isDiscount_is_on() {
        return discount_is_on;
    }

    public void setDiscount_is_on(boolean discount_is_on) {
        this.discount_is_on = discount_is_on;
    }

    public double getDiscount_friend() {
        return discount_friend;
    }
    public String getType_of_discount() {
        return type_of_discount;
    }

    public void setType_of_discount(String type_of_discount) {
        this.type_of_discount = type_of_discount;
    }
    public int getNum_of_tickets_discount() {
        return num_of_tickets_discount;
    }

    public void setNum_of_tickets_discount(int num_of_tickets_discount) {
        this.num_of_tickets_discount = num_of_tickets_discount;
    }
    public double getDiscount_simple() {
        return discount_simple;
    }

    public void setDiscount_simple(double discount_simple) {
        this.discount_simple = discount_simple;
    }


    public void setDiscount_friend(double discount_friend) {
        this.discount_friend = discount_friend;
    }

    public double getDiscount_VIP() {
        return discount_VIP;
    }

    public void setDiscount_VIP(double discount_VIP) {
        this.discount_VIP = discount_VIP;
    }




    public String getType_of_client_discount() {
        return type_of_client_discount;
    }

    public double getOld_discount() {
        return old_discount;
    }

    public void setOld_discount(double old_discount) {
        this.old_discount = old_discount;
    }

    public Discount(Entertainment entertainment){
        discount_friend = 0.9;
        discount_VIP = 0.8;
        discount_simple = 1.0;
        discount_is_on = false;
        entertainment.setDiscount(this);


    }
    public Discount(Entertainment entertainment, double discount_friend, double discount_VIP,
                    double discount_simple, int num_of_tickets_discount, String type_of_discount, double old_discount,
                    String type_of_client_discount, CinemaHall hall_discount, Date date_of_discount,
                    Session session_discount, boolean discount_is_on){
        this.discount_friend = discount_friend;
        this.discount_VIP = discount_VIP;
        this.discount_simple = discount_simple;
        this.num_of_tickets_discount = num_of_tickets_discount;
        this.type_of_discount = type_of_discount;
        this.old_discount = old_discount;
        this.type_of_client_discount = type_of_client_discount;
        this.hall_discount = hall_discount;
        this.date_of_discount = date_of_discount;
        this.session_discount = session_discount;
        this.discount_is_on = discount_is_on;
        entertainment.setDiscount(this);

    }
}
