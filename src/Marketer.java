import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Marketer extends Client implements Serializable {
    private transient static final String password = "98765";


    public Marketer(String name, String surname, String patronymic, String phone_number, String email, int money,
                    Entertainment entertainment) {
        super(name, surname, patronymic, phone_number, email, money, entertainment);
        entertainment.setMarketer(this);

    }

/*    public Marketer(String name, String surname, String patronymic, String phone_number, String email, int money,
                 Entertainment entertainment, int visits, List<Ticket> tickets, int num) {
        super(name, surname, patronymic, phone_number, email, money, entertainment, visits, tickets, num);



    }*/
    public void authentication(Entertainment entertainment){
        Scanner scanner = new Scanner(System.in);
        boolean right_password = false;
        do{
            System.out.println("Введите пароль");
            String try_password = scanner.nextLine();
            if (try_password.equals(password)){
                right_password = true;
            }
            else {
                System.out.println("Пароль введён неверно");
            }
        }
        while(!right_password);
        make_discount(entertainment);

    }

    public void make_discount(Entertainment entertainment){
        System.out.println("Выьерите, в каком кинотеатре вы хотите ввести акцию");
        int ind_cinema = choose_cinema(entertainment) - 1;
        choose_type_of_discount(ind_cinema, entertainment);


    }

    public void choose_type_of_discount(int ind_cinema, Entertainment entertainment){
        if (entertainment.getDiscount().isDiscount_is_on()){
            cancel_discount(entertainment);
        }
        System.out.println("1 - Сделать скидку на все сеансы в конкретном кинозале\n2 - Сделат скидку на " +
                "конкретный сеанс\nВременное увеличение скидки для клиентов");
        Scanner scanner = new Scanner(System.in);
        String ch = scanner.nextLine();

        if (ch.equals("1")){
            discount_for_cinemaHall(entertainment, entertainment.getAll_cinema().get(ind_cinema));
        }
        else{
            if (ch.equals("2")){
                discount_for_session(entertainment, entertainment.getAll_cinema().get(ind_cinema));

            }
            else {
                discount_for_clients(entertainment);
            }

        }
    }
    public void discount_for_cinemaHall(Entertainment entertainment, Cinema cinema){
        int ind_cinemaHall = choose_cinemaHall(cinema) - 1;
        System.out.println("Введите, сколько процентов будет скидка");
        double discount = set_discount_amount();
        int rows = cinema.getOur_halls().get(ind_cinemaHall).getNum_of_rows();
        int places = cinema.getOur_halls().get(ind_cinemaHall).getNum_of_places();

        int[][] new_price_for_places = new int[rows][places];
        for (int i = 0; i < rows; ++i){
            for (int j = 0; j < places; ++j){
                new_price_for_places[i][j] = (int)(cinema.getOur_halls().get(ind_cinemaHall).
                        getPrice_for_place()[i][j] * discount);
            }
        }
        cinema.getOur_halls().get(ind_cinemaHall).setPrice_for_place(new_price_for_places);
        entertainment.getDiscount().setOld_discount(discount);
        entertainment.getDiscount().setDiscount_is_on(true);
        entertainment.getDiscount().setType_of_discount("hall_discount");
        entertainment.getDiscount().setHall_discount(cinema.getOur_halls().get(ind_cinemaHall));
        Date date = new Date();
        entertainment.getDiscount().setDate_of_discount(date);

    }
    public void cancel_discount_cinemaHall(Entertainment entertainment){
        int rows =  entertainment.getDiscount().getHall_discount().getNum_of_rows();
        int places = entertainment.getDiscount().getHall_discount().getNum_of_places();
        int[][] old_price_for_places = new int[rows][places];
        for (int i = 0; i < rows; ++i){
            for (int j = 0; j < places; ++j){
                old_price_for_places[i][j] = (int)(entertainment.getDiscount().getHall_discount().getPrice_for_place()[i][j]/
                        (1 - entertainment.getDiscount().getOld_discount()));
            }
        }
        entertainment.getDiscount().getHall_discount().setPrice_for_place(old_price_for_places);
        entertainment.getDiscount().setDiscount_is_on(false);

    }
    public void discount_for_session(Entertainment entertainment, Cinema cinema){
        int ind_session = choose_session(cinema) - 1;
        System.out.println("Введите, сколько процентов будет скидка");
        double discount = set_discount_amount();
        entertainment.getDiscount().setOld_discount(discount);
        entertainment.getDiscount().setDiscount_is_on(true);
        entertainment.getDiscount().setType_of_discount("session_discount");
        entertainment.getDiscount().setSession_discount(cinema.getSessions().get(ind_session));
        cinema.getSessions().get(ind_session).setDiscount(discount);
        Date date = new Date();
        entertainment.getDiscount().setDate_of_discount(date);
    }
    public void cancel_discount_session(Entertainment entertainment){
        entertainment.getDiscount().getSession_discount().setDiscount(1.0);
        entertainment.getDiscount().setDiscount_is_on(false);

    }
     public double set_discount_amount(){
        Scanner scanner = new Scanner (System.in);
         boolean right_discount;
         String discount_string;
         do{
             System.out.print("Скидка в процентах ");
             discount_string = scanner.nextLine();
             Pattern pattern = Pattern.compile("([1-9])|([1-9][0-9])");
             Matcher matcher = pattern.matcher(discount_string);
             right_discount = matcher.matches();
             if (!right_discount){
                 System.out.println("Неверный формат");
             }
         }
         while(!right_discount);
         return (1 - Double.parseDouble(discount_string)/100);
     }

    public void discount_for_clients(Entertainment entertainment){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите, для кого будет скидка\n1 - для обычных клиентов\n2 - для друзей сети\n3 - " +
                "для вип - клиентов");
        String ch_type_of_client = scanner.nextLine();



        System.out.println("Введите, чему будет равна новая скидка для этого типа клииентов");

        double discount = set_discount_amount();
        if (ch_type_of_client.equals("1")){
            entertainment.getDiscount().setOld_discount(1.0);
            entertainment.getDiscount().setDiscount_simple(discount);
            entertainment.getDiscount().setType_of_client_discount("Simple");
        }
        else {
            if(ch_type_of_client.equals("2")){
                entertainment.getDiscount().setOld_discount(0.9);
                entertainment.getDiscount().setDiscount_friend(discount);
                entertainment.getDiscount().setType_of_client_discount("Friend");
            }
            else{
                entertainment.getDiscount().setOld_discount(0.8);
                entertainment.getDiscount().setDiscount_VIP(discount);
                entertainment.getDiscount().setType_of_client_discount("VIP");


            }
        }
       for (User user : entertainment.getClients()){
           if (user.getType().equals(entertainment.getDiscount().getType_of_client_discount())){
               user.setDiscount(discount);
           }
       }
       System.out.println("На сколько покупок будет распространяться эта скидка?");
        boolean right_num;
        String num_string;
        do{
            System.out.print("Количество покупок: ");
            num_string = scanner.nextLine();
            Pattern pattern = Pattern.compile("[1-9]");
            Matcher matcher = pattern.matcher(num_string);
            right_num = matcher.matches();
            if (!right_num){
                System.out.println("Неверный формат");
            }
        }
        while(!right_num);
        entertainment.getDiscount().setNum_of_tickets_discount(Integer.parseInt(num_string));
        entertainment.getDiscount().setDiscount_is_on(true);
        entertainment.getDiscount().setType_of_discount("client_discount");
        Date date = new Date();
        entertainment.getDiscount().setDate_of_discount(date);


    }
    public void cancel_discount (Entertainment entertainment){
        if (entertainment.getDiscount().getType_of_discount().equals("client_discount")){
            cancel_discount_client(entertainment);
        }

        if (entertainment.getDiscount().getType_of_discount().equals("hall_discount")){
            cancel_discount_cinemaHall(entertainment);
        }
        if (entertainment.getDiscount().getType_of_discount().equals("session_discount")){
            cancel_discount_session(entertainment);
        }
       // Discount discount = new Discount(entertainment);




    }
    public void cancel_discount_client(Entertainment entertainment){
        entertainment.getDiscount().setDiscount_is_on(false);
        if (entertainment.getDiscount().getType_of_client_discount().equals("1")){
            entertainment.getDiscount().setDiscount_simple(entertainment.getDiscount().getOld_discount());
        }
        else {
            if(entertainment.getDiscount().getType_of_client_discount().equals("2")){
                entertainment.getDiscount().setDiscount_friend(entertainment.getDiscount().getOld_discount());


            }
            else{
                entertainment.getDiscount().setDiscount_VIP(entertainment.getDiscount().getOld_discount());


            }
        }
        for (User user : entertainment.getClients()){
            if (user.getType().equals(entertainment.getDiscount().getType_of_client_discount())){
                user.setDiscount(entertainment.getDiscount().getOld_discount());
            }
        }
    }

    public int choose_cinemaHall(Cinema cinema){
        Scanner scanner = new Scanner(System.in);
        int counter = 1;
        if (cinema.getOur_halls().isEmpty()){
            System.out.println("Нет ни одного кинозала");
            return 1000;
        }
        for (int i = 0; i <cinema.getOur_halls().size(); ++i){
            System.out.println(counter++ + ". " + cinema.getOur_halls().get(i).getName());
        }
        String ch_cinemaHall;
        int ch_cinemaHall_int;
        boolean right_cinemaHall = false;
        do{

            boolean cinemaHall_is_digit;
            do {
                System.out.println("Выберите номер кинозала из списка");
                ch_cinemaHall = scanner.nextLine();

                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(ch_cinemaHall);
                cinemaHall_is_digit = matcher.matches();
            }
            while(!cinemaHall_is_digit);
            ch_cinemaHall_int = Integer.parseInt(ch_cinemaHall );
            for (int h = 0; h < cinema.getOur_halls().size(); ++h){
                if (ch_cinemaHall_int - 1 == h){
                    right_cinemaHall = true;
                }
            }
        }
        while(!right_cinemaHall);
        return ch_cinemaHall_int;
    }

    public int choose_session(Cinema cinema){
        Scanner scanner = new Scanner(System.in);
        int counter = 1;
        if (cinema.getSessions().isEmpty()){
            System.out.println("Нет ни одного сеанса");
            return 1000;
        }
        for (int i = 0; i < cinema.getSessions().size(); ++i){
            System.out.println(counter++ + ". " + cinema.getSessions().get(i).getTime() + cinema.getSessions().
                    get(i).getFilm_name() + cinema.getSessions().get(i).getOur_CinemaHall().getName());
        }
        String session;
        int ch_session_int;
        boolean right_session = false;
        do{

            boolean session_is_digit;
            do {
                System.out.println("Выберите номер сеанса из списка");
                session = scanner.nextLine();

                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(session);
                session_is_digit = matcher.matches();
            }
            while(!session_is_digit);
            ch_session_int = Integer.parseInt(session );
            for (int h = 0; h < cinema.getSessions().size(); ++h){
                if (ch_session_int - 1 == h){
                    right_session = true;
                }
            }
        }
        while(!right_session);
        return ch_session_int;
    }

}
