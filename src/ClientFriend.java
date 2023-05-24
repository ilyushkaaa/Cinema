import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientFriend implements User, Serializable {
    private static int count = 0;
    private final String type = "Friend";

    public String getType() {
        return type;
    }

    private double discount;

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    private int visits;
    private String name;
    private String surname;
    private String patronymic;
    private String phone_number;
    private String email;
    private int money;
    private List<Ticket> tickets;
    public static void serializeCount(ObjectOutputStream oos) throws IOException {
        oos.writeInt(count);
    }
    public static int deserializeCount (ObjectInputStream ois) throws IOException{
        count = ois.readInt();
        return count;
    }

    public String getName() {
        return name;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMoney() {
        return money;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
    public void add_tickets(Ticket ticket){
        tickets.add(ticket);
    }

    public String getPatronymic() {
        return patronymic;
    }

    public static int getCount() {
        return count;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public ClientFriend(Client client, Entertainment entertainment){
        visits = client.getVisits();
        name = client.getName();
        surname = client.getSurname();
        patronymic = client.getPatronymic();
        phone_number = client.getPhone_number();
        email = client.getEmail();
        money = client.getMoney();
        tickets = new ArrayList<>();
        tickets.addAll(client.getTickets());
        count += 1;
        discount = entertainment.getDiscount().getDiscount_friend();
        entertainment.add_clients(this);

    }
    public ClientFriend(String name, String surname, String patronymic, String phone_number, String email, int money,
                  Entertainment entertainment, int visits, List<Ticket> tickets, int num) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phone_number = phone_number;
        this.email = email;
        this.money = money;
        this.tickets = new ArrayList<>();
        this.tickets.addAll(tickets);
        this.visits = visits;
        count = num;
        discount = entertainment.getDiscount().getDiscount_friend();
        entertainment.add_clients(this);



    }


    public void add_money(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Желаете пополнить баланс? 1 - да Остальное - нет" );
        String balance = scanner.nextLine();
        if (balance.equals("1")){
            String plus_money;
            boolean right_money;
            do {
                System.out.println("Сколько денег вы желаете пложить на ваш баланс?");
                plus_money = scanner.nextLine();
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(plus_money);
                right_money = matcher.matches();
            }
            while(!right_money);
            int plus_money_int = Integer.parseInt(plus_money);
            money += plus_money_int;
            System.out.println("Денег на балансе\n" + money);
        }
    }

    String choose_way (){
        String ch;
        Scanner scanner = new Scanner(System.in);
        boolean right_num;
        do {
            System.out.println("По какому критерию вы будете выбирать билеты?\n По времени - 1\n По стоимости билета" +
                    " - 2\n По названию фильма - 3");
            System.out.println("Введите 1/2/3");
            ch = scanner.nextLine();
            if (!ch.equals("1") && !ch.equals("2") && !ch.equals("3")) {
                right_num = false;
            }
            else {
                right_num = true;
            }
        }
        while(!right_num);
        return ch;
    }
    String choose_time(Cinema cinema){
        Scanner scanner = new Scanner(System.in);
        String our_time;
        boolean right_time = false;
        do {
            System.out.println("Введите нужное вам время сеанса");
            our_time = scanner.nextLine();
            for (int i = 0; i <  cinema.getSessions().size(); ++i){
                if (our_time.equals( cinema.getSessions().get(i).getTime())){
                    right_time = true;
                    break;
                }
            }
        }
        while (!right_time);
        return our_time;
    }

    int choose_price(Cinema cinema){
        Scanner scanner = new Scanner(System.in);
        String our_price;
        int our_price_int;
        boolean right_price = false;
        do {

            boolean is_digit;
            do {
                System.out.println("Введите нужную стоимость билета");
                our_price = scanner.nextLine();
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(our_price);
                is_digit = matcher.matches();
                if(!is_digit){
                    System.out.println("Введено некорректное значение цены " +
                            "на билет (стоимость всех билеов - целое число");
                }
            }
            while(!is_digit);
            our_price_int = Integer.parseInt(our_price);
            for (int i = 0; i <  cinema.getSessions().size(); ++i){
                for (int j = 0; j < cinema.getSessions().get(i).getOur_CinemaHall().getNum_of_places(); ++j){
                    if (our_price_int == cinema.getSessions().get(i).getOur_CinemaHall().getPrice_for_place()[0][j]){
                        right_price = true;
                        break;
                    }
                }
            }
        }
        while (!right_price);
        return our_price_int;
    }

    String choose_film (Cinema cinema){
        Scanner scanner = new Scanner(System.in);
        String our_name;
        boolean right_name = false;
        do {
            System.out.println("Введите название интересующего вас фильма");
            our_name = scanner.nextLine();
            for (int i = 0; i <  cinema.getSessions().size(); ++i){
                if (our_name.equals( cinema.getSessions().get(i).getFilm_name())){
                    right_name = true;
                    break;
                }
            }
        }
        while (!right_name);
        return our_name;
    }

    int choose_num_of_session(List <Session> good_sessions){
        String ch_session;
        int ch_session_int;
        Scanner scanner = new Scanner(System.in);

        boolean right_session = false;
        do{

            boolean session_is_digit;
            do {
                System.out.println("Выберите номер сеанса из списка");
                ch_session = scanner.nextLine();

                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(ch_session);
                session_is_digit = matcher.matches();
            }
            while(!session_is_digit);
            ch_session_int = Integer.parseInt(ch_session);
            for (int h = 0; h < good_sessions.size(); ++h){
                if (ch_session_int - 1 == h){
                    right_session = true;
                }
            }
        }
        while(!right_session);
        return ch_session_int;

    }
    int choose_place (List <Session> good_sessions, Cinema cinema, int ch_session_int, int our_price, String ch){
        Scanner scanner = new Scanner(System.in);
        String ch_place;
        int ch_place_int;
        boolean right_place = false;
        do {
            boolean place_is_digit;
            do {
                System.out.println("Выберите номер места");
                ch_place = scanner.nextLine();
                Pattern pattern = Pattern.compile("\\d\\d");
                Matcher matcher = pattern.matcher(ch_place);
                place_is_digit = matcher.matches();
            }
            while(!place_is_digit);
            //int hall_index = cinema.recieve_hall_index(good_sessions.get(ch_session_int - 1));
            ch_place_int = Integer.parseInt(ch_place);
            for (int i = 0; i <  good_sessions.get(ch_session_int - 1).getOur_CinemaHall().getNum_of_rows(); ++i){
                for (int j = 0; j < good_sessions.get(ch_session_int - 1).getOur_CinemaHall().getNum_of_places(); ++j){
                    boolean cur_price;
                    if (ch.equals("2")){
                        if (good_sessions.get(ch_session_int - 1).getOur_CinemaHall().getPrice_for_place()[i][j]
                                == our_price){
                            cur_price = true;
                        }
                        else {
                            cur_price = false;
                        }
                    }
                    else {
                        cur_price = true;
                    }
                    if (ch_place_int ==  good_sessions.get(ch_session_int - 1).getOur_CinemaHall().getPlaces()[i][j] &&
                            good_sessions.get(ch_session_int - 1).getFree_places()[i][j] && cur_price){
                        if (money >=  good_sessions.get(ch_session_int - 1).getOur_CinemaHall().getPrice_for_place()[i][j]){
                            right_place = true;
                            break;
                        }
                        else {
                            System.out.println("Недостаточно средств");
                            add_money();
                        }

                    }
                }
                if (right_place){
                    break;
                }
            }
            if (!right_place){
                System.out.println("Место недоступно. Выберите другое место.");

            }
        }
        while(!right_place);
        return ch_place_int;
    }

    public void show_bought_tickets(){
        System.out.println("Купленные билеты в рамках этой сессии:");
        for (int i = 0; i < tickets.size(); ++i){
            int num = i + 1;
            System.out.print(num + ". " + tickets.get(i).getCinema().getName() + " " );
            tickets.get(i).getCinemaHall().print_type_of_hall();
            System.out.println(" " + tickets.get(i).getSession().getTime() + " " + tickets.get(i).getFilm().getName() +
                    " " + tickets.get(i).getRow() + " ряд " + tickets.get(i).getPlace() + " место");
        }

    }

    public Ticket buy_ticket(Cinema cinema, Session session, CinemaHall cinemaHall, Film film, int row,
                             int place, ClientFriend client, int price){
        Date date = new Date();
        Ticket new_ticket = new Ticket(cinema, session, cinemaHall, film, row, place, client, date, price);
        return new_ticket;

    }


    public void choose_session(Cinema cinema, Entertainment entertainment){
        if (cinema.getSessions().size() == 0){
            System.out.println("В кинотеатре пока нет сеансов, но скоро они появятся!");
            return;
        }
        System.out.println("Добро пожаловать в кинотеатр " + cinema.getName() + ", " +
                surname + " " + name + " " + patronymic + "!");
        entertainment.getDiscount().print_discount_info(this);
        System.out.println("Ваш статус - друг сети");
        System.out.println("Ваша персональная скидка на все билеты: " + (1 - discount) * 100 +" % ");
        System.out.println("Денег на балансе\n" + money);
        add_money();
        String ch = choose_way();
        if (ch.equals("1")){
            System.out.println("Доступные времена сеансов");
            cinema.printSessionsTime();
            String our_time = choose_time(cinema);
            int counter = 1;
            List <Session> good_sessions = new ArrayList<>();
            for (int i = 0; i <  cinema.getSessions().size(); ++i){
                if (our_time.equals( cinema.getSessions().get(i).getTime())){
                    System.out.println(counter + ".");
                    cinema.getSessions().get(i).printInfo(false);
                    System.out.print("\n");
                    ++counter;
                    good_sessions.add( cinema.getSessions().get(i));
                }
            }
            int ch_session_int = choose_num_of_session(good_sessions);
            good_sessions.get(ch_session_int - 1).printChosenSessionInfo(entertainment, cinema);
            int ch_place_int = choose_place(good_sessions, cinema, ch_session_int, 200, ch);
            double hall_discount = 1;
            if (entertainment.getDiscount().isDiscount_is_on()){
                if (entertainment.getDiscount().getType_of_discount().equals("hall_discount")){
                    if (entertainment.getDiscount().getHall_discount().getName().equals(good_sessions.get(ch_session_int - 1).
                            getOur_CinemaHall().getName())){
                        hall_discount = entertainment.getDiscount().getOld_discount();
                    }
                }
            }
            int price_for_ticket = good_sessions.get(ch_session_int - 1).getOur_CinemaHall().getPrice_for_place()[ch_place_int / 10 - 1][ch_place_int % 10 - 1];
            double your_price = price_for_ticket * discount * good_sessions.get(ch_session_int - 1).getDiscount() *
                    hall_discount;
            money -= (int)your_price;

            cinema.confirm_ticket(good_sessions, ch_session_int, ch_place_int, (int)your_price);
            good_sessions.get(ch_session_int - 1).getOur_CinemaHall().
                    add_money(good_sessions.get(ch_session_int - 1).getOur_CinemaHall(), (int)your_price);
            int film_index = 0;
            for (int r = 0; r < cinema.getFilms().size(); ++r){
                if (good_sessions.get(ch_session_int - 1).getFilm_name().equals(cinema.getFilms().get(r).getName())){
                    film_index = r;
                }
            }


            Ticket our_ticket = buy_ticket(cinema, good_sessions.get(ch_session_int - 1),
                    good_sessions.get(ch_session_int - 1).getOur_CinemaHall(), cinema.getFilms().get(film_index),
                    ch_place_int / 10, ch_place_int % 10, this, (int)your_price);


        }
        if (ch.equals("2")){
            System.out.println("Возможные цены на билеты");
            System.out.println(cinema.getAll_prices());
            int our_price_int = choose_price(cinema);
            int counter = 1;
            List <Session> good_sessions = new ArrayList<>();
            for (int i = 0; i <  cinema.getSessions().size(); ++i){
                boolean has_this_price = false;
                for (int h = 0; h < cinema.getSessions().get(i).getOur_CinemaHall().getNum_of_places(); ++h){
                    if (our_price_int == cinema.getSessions().get(i).getOur_CinemaHall().getPrice_for_place()[0][h]){
                        has_this_price = true;
                        break;
                    }
                }
                if (has_this_price){
                    System.out.println("Сеанс номер " + counter + ".");
                    cinema.getSessions().get(i).printPrices(our_price_int, false);
                    System.out.print("\n");
                    ++counter;
                    good_sessions.add( cinema.getSessions().get(i));
                }
            }

            int ch_session_int = choose_num_of_session(good_sessions);

            good_sessions.get(ch_session_int - 1).printChosenSessionInfoPriceMode(cinema, our_price_int);
            int ch_place_int = choose_place(good_sessions, cinema, ch_session_int, our_price_int, ch);
            double hall_discount = 1;
            if (entertainment.getDiscount().isDiscount_is_on()){
                if (entertainment.getDiscount().getType_of_discount().equals("hall_discount")){
                    if (entertainment.getDiscount().getHall_discount().getName().equals(good_sessions.get(ch_session_int - 1).
                            getOur_CinemaHall().getName())){
                        hall_discount = entertainment.getDiscount().getOld_discount();
                    }
                }
            }
            int price_for_ticket = good_sessions.get(ch_session_int - 1).getOur_CinemaHall().getPrice_for_place()[ch_place_int / 10 - 1][ch_place_int % 10 - 1];
            double your_price = price_for_ticket * discount * good_sessions.get(ch_session_int - 1).getDiscount() *
                    hall_discount;          ;
            money -= (int)your_price;

            cinema.confirm_ticket(good_sessions, ch_session_int, ch_place_int, (int)your_price);
            good_sessions.get(ch_session_int - 1).getOur_CinemaHall().
                    add_money(good_sessions.get(ch_session_int - 1).getOur_CinemaHall(), (int)your_price);
            int film_index = 0;
            for (int r = 0; r < cinema.getFilms().size(); ++r){
                if (good_sessions.get(ch_session_int - 1).getFilm_name().equals(cinema.getFilms().get(r).getName())){
                    film_index = r;
                }
            }
            buy_ticket(cinema, good_sessions.get(ch_session_int - 1), good_sessions.get(ch_session_int - 1).getOur_CinemaHall(), cinema.getFilms().get(film_index),
                    ch_place_int / 10, ch_place_int % 10, this, (int)your_price);

        }
        if (ch.equals("3")){
            System.out.println("Доступные фильмы");
            cinema.printSessionsFilmName();
            String our_name = choose_film(cinema);
            int counter = 1;
            List <Session> good_sessions = new ArrayList<>();
            for (int i = 0; i <  cinema.getSessions().size(); ++i){
                if (our_name.equals( cinema.getSessions().get(i).getFilm_name())){
                    System.out.println(counter + ".");
                    cinema.getSessions().get(i).printInfo(false);
                    System.out.print("\n");
                    ++counter;
                    good_sessions.add( cinema.getSessions().get(i));
                }
            }
            int ch_session_int = choose_num_of_session(good_sessions);
            good_sessions.get(ch_session_int - 1).printChosenSessionInfo( entertainment, cinema);
            int ch_place_int = choose_place(good_sessions, cinema, ch_session_int, 200, ch);
            double hall_discount = 1;
            if (entertainment.getDiscount().isDiscount_is_on()){
                if (entertainment.getDiscount().getType_of_discount().equals("hall_discount")){
                    if (entertainment.getDiscount().getHall_discount().getName().equals(good_sessions.get(ch_session_int - 1).
                            getOur_CinemaHall().getName())){
                        hall_discount = entertainment.getDiscount().getOld_discount();
                    }
                }
            }
            int price_for_ticket = good_sessions.get(ch_session_int - 1).getOur_CinemaHall().getPrice_for_place()[ch_place_int / 10 - 1][ch_place_int % 10 - 1];
            double your_price = price_for_ticket * discount * good_sessions.get(ch_session_int - 1).getDiscount() *
                    hall_discount;
            money -= (int)your_price;

            cinema.confirm_ticket(good_sessions, ch_session_int, ch_place_int, (int)your_price);
            good_sessions.get(ch_session_int - 1).getOur_CinemaHall().
                    add_money(good_sessions.get(ch_session_int - 1).getOur_CinemaHall(), (int)your_price);
            int film_index = 0;
            for (int r = 0; r < cinema.getFilms().size(); ++r){
                if (good_sessions.get(ch_session_int - 1).getFilm_name().equals(cinema.getFilms().get(r).getName())){
                    film_index = r;
                }
            }
            buy_ticket(cinema, good_sessions.get(ch_session_int - 1), good_sessions.get(ch_session_int - 1).getOur_CinemaHall(), cinema.getFilms().get(film_index),
                    ch_place_int / 10, ch_place_int % 10, this, (int)your_price);

        }
        show_bought_tickets();
        System.out.println("Денег на балансе\n" + money);
        visits +=1;
        if (entertainment.getDiscount().isDiscount_is_on()){
            if (entertainment.getDiscount().getType_of_discount().equals("client_discount")){
                if (entertainment.getDiscount().getType_of_client_discount().equals("Friend")){
                    entertainment.getDiscount().setNum_of_tickets_discount(entertainment.
                            getDiscount().getNum_of_tickets_discount() - 1);
                    if (entertainment.getDiscount().getNum_of_tickets_discount() == 0){
                        entertainment.getMarketer().cancel_discount(entertainment);
                    }
                }
            }

        }
        if (visits == 4){
            System.out.println("Поздравляем! Вы получили статус VIP клиента! Теперь все билеты доступны\n " +
                    "вам со скидкой 20 %, а также при покупке каждого билета перед сеансом вы будете иметь\n" +
                    " возможность получить бесплатный напиток");
            ClientVIP new_client_friend = new ClientVIP(this, entertainment);
            entertainment.delete_client(this);
            count -= 1;
        }

    }
    public int choose_cinema(Entertainment entertainment){
        Scanner scanner = new Scanner(System.in);
        int counter = 1;
        for (int i = 0; i < entertainment.getAll_cinema().size(); ++i){
            System.out.println(counter++ + ". " + entertainment.getAll_cinema().get(i).getName());
        }
        String ch_cinema;
        int ch_cinema_int;
        boolean right_cinema = false;
        do{

            boolean cinema_is_digit;
            do {
                System.out.println("Выберите номер кинотеатра из списка");
                ch_cinema = scanner.nextLine();

                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(ch_cinema);
                cinema_is_digit = matcher.matches();
            }
            while(!cinema_is_digit);
            ch_cinema_int = Integer.parseInt(ch_cinema );
            for (int h = 0; h < entertainment.getAll_cinema().size(); ++h){
                if (ch_cinema_int - 1 == h){
                    right_cinema = true;
                }
            }
        }
        while(!right_cinema);
        return ch_cinema_int;


    }

}
