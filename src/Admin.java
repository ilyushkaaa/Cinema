import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// есть маркетолог. Он умеет создавать аккции и временное повышение скидки. все билеты купленные после маркетолога
//считается что куплены благодаря ему
//посчитать насколько лучше или хуже стало после маркетолога


public class Admin extends Client implements Serializable {
    private transient static final String password = "12345";


    public Admin(String name, String surname, String patronymic, String phone_number, String email, int money,
                 Entertainment entertainment){
        super(name, surname, patronymic, phone_number, email, money, entertainment);
    }
    public Admin(String name, String surname, String patronymic, String phone_number, String email, int money,
                  Entertainment entertainment, int visits, List<Ticket> tickets,int num) {
        super(name, surname, patronymic, phone_number, email, money, entertainment, visits, tickets, num);


    }
    public void authentication(Entertainment entertainment) throws FileNotFoundException{
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
        choose_mode(entertainment);

    }
    public void choose_mode(Entertainment entertainment) throws FileNotFoundException{
        System.out.println("Увидеть инфрормацию о продажах - 1\nАнализ работы маркетолога - 2\nВнести изменения в " +
                "кинотеатры - остальное");
        Scanner scanner = new Scanner(System.in);
        String ch = scanner.nextLine();
        if (ch.equals("1")){
            System.out.println("Информацию о продажах в каком кинотеатре вы хотите увидеть?");
            for (int i = 0; i < entertainment.getAll_cinema().size(); ++i){
                System.out.println(entertainment.getAll_cinema().get(i).getName());
            }
            boolean right_name = false;
            do{
                System.out.println("Введите название кинотеатра");
                String name = scanner.nextLine();
                for (int j = 0; j < entertainment.getAll_cinema().size(); ++j){
                    if (name.equals(entertainment.getAll_cinema().get(j).getName())){
                        right_name = true;
                        get_info(entertainment.getAll_cinema().get(j));
                        break;
                    }
                }
                if (!right_name){
                    System.out.println("Нет кинотеатра с таким названием.");
                }
            }
            while(!right_name);
        }
        else {
            if (ch.equals("2")){
               analyze_marketer(entertainment);
            }
            else{
                make_changes(entertainment);
            }
        }
    }
    public void analyze_marketer(Entertainment entertainment){
        //System.out.println(entertainment.getDiscount().getType_of_discount() + entertainment.getDiscount().getHall_discount().getName());
        if (!entertainment.getDiscount().isDiscount_is_on()){
            System.out.println("Сейчас никаких акций не действует");
        }
        else{
            int sum_before_discount = 0;
            int sum_after_discount = 0;
            List <Ticket> all_tickets_before_discount = new ArrayList<>();
            List <Ticket> all_tickets_after_discount = new ArrayList<>();

            Date date_of_discount = entertainment.getDiscount().getDate_of_discount();
            for (User user : entertainment.getClients()){
                for (Ticket ticket : user.getTickets()){
                    if (ticket.getDate_of_purchase().compareTo(date_of_discount) < 0){
                        sum_before_discount += ticket.getPrice();
                        all_tickets_before_discount.add(ticket);
                    }
                    else{
                        sum_after_discount += ticket.getPrice();
                        all_tickets_after_discount.add(ticket);

                    }

                }
            }
            Collections.sort(all_tickets_before_discount);
            Collections.sort(all_tickets_after_discount);
            double average_revenue_before_discount;
            double average_revenue_after_discount;
            long milliseconds_before_discount;
            long days_before_discount;
            long milliseconds_after_discount;
            long days_after_discount;

            if (all_tickets_before_discount.size() ==0){
                average_revenue_before_discount = 0.0;
            }
            else{

                milliseconds_before_discount = entertainment.getDiscount().getDate_of_discount().getTime() -
                        all_tickets_before_discount.get(0).getDate_of_purchase().getTime();
                days_before_discount = TimeUnit.DAYS.convert(milliseconds_before_discount, TimeUnit.MILLISECONDS) + 1;
                average_revenue_before_discount = (double)sum_before_discount / (double)days_before_discount;

            }
            if (all_tickets_after_discount.size() == 0){
                average_revenue_after_discount = 0;
            }
            else{
                Date date = new Date();
                milliseconds_after_discount = date.getTime() - entertainment.getDiscount().getDate_of_discount().getTime();
                days_after_discount = TimeUnit.DAYS.convert(milliseconds_after_discount, TimeUnit.MILLISECONDS) + 1;
                average_revenue_after_discount = (double)sum_after_discount / (double)days_after_discount;
            }
            System.out.println("Средняя выручка в день до введения акции: " + average_revenue_before_discount + " " +
                    "рублей в день");
            System.out.println("Средняя выручка в день после введения акции: " + average_revenue_after_discount + " " +
                    "рублей в день");
            String path = "marketerAnaliz.txt";
            File file = new File(path);

            if(average_revenue_before_discount > average_revenue_after_discount){
                System.out.println("Введение акции негативно сказалось на выручке.");
                try{
                    PrintWriter fileWriter = new PrintWriter(file);
                    fileWriter.println("Средняя выручка в день до введения акции: " + average_revenue_before_discount + " " +
                            "рублей в день");
                    fileWriter.println("Средняя выручка в день после введения акции: " + average_revenue_after_discount + " " +
                            "рублей в день");
                    fileWriter.println("Введение акции негативно сказалось на выручке.");
                    fileWriter.close();


                }
                catch(IOException e){
                    e.printStackTrace();
                }

            }
            if (average_revenue_before_discount < average_revenue_after_discount){
                System.out.println("Введение акции позитивно сказалось на выручке.");
                try{
                    PrintWriter fileWriter = new PrintWriter(file);
                    fileWriter.println("Средняя выручка в день до введения акции: " + average_revenue_before_discount + " " +
                            "рублей в день");
                    fileWriter.println("Средняя выручка в день после введения акции: " + average_revenue_after_discount + " " +
                            "рублей в день");
                    fileWriter.println("Введение акции позитивно сказалось на выручке.");
                    fileWriter.close();

                }
                catch(IOException e){
                    e.printStackTrace();
                }

            }
            if (average_revenue_before_discount == average_revenue_after_discount){
                System.out.println("Введение акции никак не сказалось на выручке.");
                try{
                    PrintWriter fileWriter = new PrintWriter(file);
                    fileWriter.println("Средняя выручка в день до введения акции: " + average_revenue_before_discount + " " +
                            "рублей в день");
                    fileWriter.println("Средняя выручка в день после введения акции: " + average_revenue_after_discount + " " +
                            "рублей в день");
                    fileWriter.println("Введение акции никак не сказалось на выручке.");
                    fileWriter.close();


                }
                catch(IOException e){
                    e.printStackTrace();
                }

            }
        }

    }
    public void make_changes(Entertainment entertainment){
        System.out.println("1 - создать кинотеатр\n2 - редактировать кинотеатр\nОстальное - удалить кинотеатр");
        Scanner scanner = new Scanner(System.in);
        String ch = scanner.nextLine();
        if (ch.equals("1")){
            create_cinema(entertainment);
        }
        else {
            if (ch.equals("2")){
                edit_cinema(entertainment);
            }
            else {
                delete_cinema(entertainment);
            }
        }

    }

    public void create_cinema(Entertainment entertainment){
        String cinema_name = name_cinema(entertainment);
        Scanner scanner = new Scanner (System.in);
        System.out.println("Введите адрес кинотеатра");
        String address = scanner.nextLine();
        String[] formats = new String[]{"2D", "3D", "4D"};
        Cinema new_cinema = new Cinema(cinema_name, address, formats, entertainment, 0, 0);
    }
    public String name_cinema(Entertainment entertainment){
        Scanner scanner = new Scanner (System.in);
        String name;
        boolean old_name = false;
        do{
            System.out.print("Название кинотеатра");
            name = scanner.nextLine();

            for (int i = 0; i < entertainment.getAll_cinema().size(); ++i){
                if (name.equals(entertainment.getAll_cinema().get(i).getName())){
                    old_name = true;
                    break;
                }
            }
            if (old_name){
                System.out.println("Ошибка! Кинотеатр с таким названием уже есть");
            }
        }
        while(old_name);
        return name;
    }

    public int space_of_hall(){
        String hall_area;
        Scanner scanner = new Scanner (System.in);
        boolean space_is_digit;
        do {
            System.out.println("Введите площадь нового кинозала");
            hall_area = scanner.nextLine();

            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(hall_area);
            space_is_digit = matcher.matches();
            if (!space_is_digit){
                System.out.println("Неверный формат ввода. Должно быть целое число");
            }
        }
        while(!space_is_digit);
        return Integer.parseInt(hall_area);
    }
    public int num_of_rows_places(boolean a){
        String num_of;
        Scanner scanner = new Scanner (System.in);
        boolean num_is_digit;
        do {
            if (a){
                System.out.println("Введите количество рядов (1-9)");
            }
            else {
                System.out.println("Введите количество мест в ряду(1-9)");
            }
            num_of = scanner.nextLine();
            Pattern pattern = Pattern.compile("[1-9]");
            Matcher matcher = pattern.matcher(num_of);
            num_is_digit = matcher.matches();
            if (!num_is_digit){
                System.out.println("Неверный формат ввода.");
            }
        }
        while(!num_is_digit);
        return Integer.parseInt(num_of);
    }
    public int free_dishes(){
        String dishes;
        Scanner scanner = new Scanner (System.in);
        boolean dishes_is_digit;
        do {
            System.out.println("Введите количество бесплатных блюд");
            dishes = scanner.nextLine();

            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(dishes);
            dishes_is_digit = matcher.matches();
            if (!dishes_is_digit){
                System.out.println("Неверный формат ввода. Должно быть целое число");
            }
        }
        while(!dishes_is_digit);
        return Integer.parseInt(dishes);
    }

    public void create_cinemaHall(Entertainment entertainment, Cinema cinema){
        System.out.println("Выберите тип кинозала");
        System.out.println("1 - 2D\n2 - 3D\n3 - 7D\n4 - Comfort\nОстальное - VIP");
        Scanner scanner = new Scanner(System.in);
        String ch = scanner.nextLine();
        int num_of_rows_1 = num_of_rows_places(true);
        int num_of_places_1 = num_of_rows_places(false);
        int hall_area_1 = space_of_hall();
        switch (ch){
            case "1":
                CinemaHall2D new_hall1 = new CinemaHall2D(entertainment, hall_area_1, num_of_rows_1, num_of_places_1, cinema,
                        CinemaHall2D.getEarned_money()); break;
            case "2":
                CinemaHall3D new_hall2 = new CinemaHall3D(entertainment, hall_area_1, num_of_rows_1, num_of_places_1, cinema,
                        CinemaHall3D.getEarned_money()); break;
            case "3":
                CinemaHall7D new_hall3 = new CinemaHall7D(entertainment, hall_area_1, num_of_rows_1, num_of_places_1, cinema,
                        CinemaHall7D.getEarned_money()); break;
            case "4":
                CinemaHallComfort new_hall4 = new CinemaHallComfort
                        (entertainment, hall_area_1, num_of_rows_1, num_of_places_1, cinema,
                                CinemaHallComfort.getEarned_money()); break;
            default: CinemaHallVIP new_hall5 = new CinemaHallVIP(entertainment, hall_area_1, num_of_places_1, cinema, free_dishes(),
                    CinemaHallVIP.getEarned_money());
            break;
        }
    }

    public void delete_cinema(Entertainment entertainment){

        if (entertainment.getAll_cinema().isEmpty()){
            System.out.println("Не добавлено ни одного кинотеатра. Удалять нечего.");
            return;
        }
        System.out.println("Какой кинотеатр вы хотите удалить?");
        int index = choose_cinema(entertainment);
        for (int i = 0; i < entertainment.getAll_cinema().get(index - 1).getOur_halls().size(); ++i){
            for (int j = 0; j < entertainment.getAll_cinema().get(index - 1).getSessions().size(); ++j){
                if (entertainment.getAll_cinema().get(index - 1).getSessions().get(j).getOur_CinemaHall().
                        getType().equals(entertainment.getAll_cinema().get(index - 1).getOur_halls().
                        get(i).getType())){
                    entertainment.getAll_cinema().get(index - 1).delete_session(j);
                    --j;
                }
            }

           entertainment.getAll_cinema().get(index - 1).delete_cinemaHall(i);
           --i;
        }
        entertainment.delete_cinema(index - 1);



    }
    public int choose_cinemaHall(Cinema cinema){
        String ch_hall;
        int ch_hall_int;
        Scanner scanner = new Scanner(System.in);

        int counter = 1;

        for (int i = 0; i <  cinema.getOur_halls().size(); ++i){

            System.out.println(counter + ".");
            System.out.println(cinema.getOur_halls().get(i).getName());
            System.out.print("\n");
            ++counter;
        }

        boolean right_hall = false;
        do{

            boolean hall_is_digit;
            do {
                System.out.println("Выберите номер кинозала из списка");
                ch_hall = scanner.nextLine();

                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(ch_hall);
                hall_is_digit = matcher.matches();
            }
            while(!hall_is_digit);
            ch_hall_int = Integer.parseInt(ch_hall );
            for (int h = 0; h < cinema.getOur_halls().size(); ++h){
                if (ch_hall_int - 1 == h){
                    right_hall = true;
                }
            }
        }
        while(!right_hall);
        return ch_hall_int;


    }

    public void delete_cinema_Hall(Entertainment entertainment,Cinema cinema){
        if (cinema.getOur_halls().isEmpty()){
            System.out.println("В кинотеатре пока нет кинозалов. Удалять нечего.");
            return;
        }
        System.out.println("Выберите, какой кинозал вы хотите удалить");
        int ch_hall = choose_cinemaHall(cinema);
        if (entertainment.getDiscount().isDiscount_is_on()){
            if (entertainment.getDiscount().getType_of_discount().equals("hall_discount")){
                if(entertainment.getDiscount().getHall_discount().getName().equals(cinema.getOur_halls().
                        get(ch_hall - 1).getName())){
                    entertainment.getMarketer().cancel_discount(entertainment);
                }
            }
        }
        for (int i = 0; i < cinema.getSessions().size(); ++i){
            if (cinema.getSessions().get(i).getOur_CinemaHall().getName().equals(cinema.getOur_halls().
                    get(ch_hall - 1).getName())){
                Session session_to_delete = cinema.delete_session(i);
                --i;
                boolean has_time = false;
                for (int n = 0; n < cinema.getSessions().size(); ++n){
                    if (session_to_delete.getTime().equals(cinema.getSessions().get(n).getTime())){
                        has_time = true;
                        break;
                    }
                }
                if(!has_time){
                    cinema.delete_time(session_to_delete.getTime());
                }
                boolean has_price = false;
                for (int j = 0; j < cinema.getAll_prices().size(); ++j){
                    for (int f = 0; f < cinema.getSessions().size(); ++f){
                        for (int r = 0; r < cinema.getSessions().get(f).getOur_CinemaHall().getNum_of_places(); ++r){
                            if (cinema.getAll_prices().get(j).equals(cinema.getSessions().get(f).getOur_CinemaHall().
                                    getPrice_for_place()[0][r])){
                                has_price = true;
                            }
                        }
                    }
                    if (!has_price){
                        cinema.delete_price(j);
                    }
                    has_price = false;
                }

            }
        }
        cinema.delete_cinemaHall(ch_hall - 1);



    }
    public void edit_cinema(Entertainment entertainment){
        System.out.println("Какой кинотеатр вы");
        System.out.println("1 - создать кинозал\n2 - удалить кинозал\n3 - создать сеанс" +
                "\nостальное - удалить сеанс");
        Scanner scanner = new Scanner(System.in);
        String ch = scanner.nextLine();

        for (int i = 0; i < entertainment.getAll_cinema().size(); ++i){
            System.out.println(entertainment.getAll_cinema().get(i).getName());
        }
        boolean right_name = false;
        do{
            System.out.println("Введите название кинотеатра");
            String name = scanner.nextLine();
            for (int j = 0; j < entertainment.getAll_cinema().size(); ++j){
                if (name.equals(entertainment.getAll_cinema().get(j).getName())){
                    right_name = true;
                    switch (ch){
                        case "1": create_cinemaHall(entertainment, entertainment.getAll_cinema().get(j)); break;
                        case "2": delete_cinema_Hall(entertainment, entertainment.getAll_cinema().get(j)); break;
                        case "3": create_session(entertainment,entertainment.getAll_cinema().get(j)); break;
                        default: delete_session(entertainment, entertainment.getAll_cinema().get(j));
                    }
                    break;
                }
            }
            if (!right_name){
                System.out.println("Нет кинотеатра с таким названием.");
            }
        }
        while(!right_name);
    }
    public void create_session(Entertainment entertainment, Cinema cinema){
        if (cinema.getOur_halls().isEmpty()){
            System.out.println("В этом кинотеатре пока нет кинозалов\nДобавьте сначала хотя бы 1 кинозал");
            return;
        }

        int ch_hall = choose_cinemaHall(cinema);
        Scanner scanner = new Scanner(System.in);

        String our_name;
        boolean right_name = false;
        System.out.println("Доступные фильмы");
        entertainment.printSessionsFilmName();
        do {
            System.out.println("Введите название интересующего вас фильма");
            our_name = scanner.nextLine();
            for (int i = 0; i <  entertainment.getAll_films().size(); ++i){
                if (our_name.equals(entertainment.getAll_films().get(i).getName())){
                    right_name = true;
                    break;
                }
            }
        }
        while (!right_name);
        String time;

        boolean right_time;
        do{
            System.out.print("Время сеанса ");
            time = scanner.nextLine();
            Pattern pattern = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
            Matcher matcher = pattern.matcher(time);
            right_time = matcher.matches();
            if (!right_time){
                System.out.println("Неверный формат времени. Введите его снова");
            }
        }
        while(!right_time);


        Session new_session = new Session(entertainment,cinema, cinema.getOur_halls().get(ch_hall - 1),
                our_name, time);

    }
    public void delete_session(Entertainment entertainment, Cinema cinema){
        if (cinema.getSessions().isEmpty()){
            System.out.println("В кинотеатре пока нет сеансов. Удалять нечего.");
            return;
        }
        System.out.println("Удалите 1 из сеансов");
        String ch_session;
        int ch_session_int;
        Scanner scanner = new Scanner(System.in);
        int counter = 1;

        boolean right_session = false;
        for (int i = 0; i <  cinema.getSessions().size(); ++i){

            System.out.println(counter + ".");
            cinema.getSessions().get(i).printInfo(false);
            System.out.print("\n");
            ++counter;
        }
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
            for (int h = 0; h < cinema.getSessions().size(); ++h){
                if (ch_session_int - 1 == h){
                    right_session = true;
                }
            }
        }
        while(!right_session);
        if (entertainment.getDiscount().isDiscount_is_on()){
            if(entertainment.getDiscount().getType_of_discount().equals("session_discount")){
                if (entertainment.getDiscount().getSession_discount().getTime().equals(cinema.getSessions().
                        get(ch_session_int - 1).getTime()) && entertainment.getDiscount().getSession_discount().
                        getFilm_name().equals(cinema.getSessions().
                        get(ch_session_int - 1).getFilm_name())){
                    entertainment.getMarketer().cancel_discount(entertainment);
                    System.out.println("dddddddddddddddddddddddddddd");
                }
            }
        }
        Session session_to_delete = cinema.delete_session(ch_session_int - 1);
        boolean has_time = false;
        for (int i = 0; i < cinema.getSessions().size(); ++i){
            if (session_to_delete.getTime().equals(cinema.getSessions().get(i).getTime())){
                has_time = true;
                break;
            }
        }
        if(!has_time){
            cinema.delete_time(session_to_delete.getTime());
        }
        //написать удадение стоимости если нет её больше


        boolean has_price = false;
        for (int j = 0; j < cinema.getAll_prices().size(); ++j){
            for (int f = 0; f < cinema.getSessions().size(); ++f){
                for (int r = 0; r < cinema.getSessions().get(f).getOur_CinemaHall().getNum_of_places(); ++r){
                   if (cinema.getAll_prices().get(j).equals(cinema.getSessions().get(f).getOur_CinemaHall().
                           getPrice_for_place()[0][r])){
                       has_price = true;
                   }
                }
            }
            if (!has_price){
                cinema.delete_price(cinema.getAll_prices().get(j));
            }
            has_price = false;
        }

    }

    public void get_info(Cinema luxor) throws FileNotFoundException {
        System.out.println("Приветствуем вас, администратор " + getSurname() + " " + getName() + " " + getPatronymic());
        System.out.println("Куплено билетов " + luxor.getSell_tickets());
        System.out.println("Общая выручка кинотеатра " + luxor.getEarned_money() + " рублей");
        System.out.println("Клиентов без статуса: " + Client.getCount());
        System.out.println("Друзей сети: " + ClientFriend.getCount());
        System.out.println("VIP клиентов: " + ClientVIP.getCount());
        System.out.println("Выручка по залам");
        System.out.println("2D - " + CinemaHall2D.getEarned_money());
        System.out.println("3D - " +  CinemaHall3D.getEarned_money());
        System.out.println("7D - " + CinemaHall7D.getEarned_money());
        System.out.println("Comfort - " +  CinemaHallComfort.getEarned_money());
        System.out.println("VIP - " +  CinemaHallVIP.getEarned_money());
        File file_analytic = new File("analytics.txt");
        try{
            PrintWriter pw = new PrintWriter(file_analytic);
            pw.println("Отчёт о кинотеатре " + luxor.getName());
            pw.println("Куплено билетов " + luxor.getSell_tickets());
            pw.println("Общая выручка кинотеатра " + luxor.getEarned_money() + " рублей");
            pw.println("Клиентов без статуса: " + Client.getCount());
            pw.println("Друзей сети: " + ClientFriend.getCount());
            pw.println("VIP клиентов: " + ClientVIP.getCount());
            pw.println("Выручка по залам");
            pw.println("2D - " + CinemaHall2D.getEarned_money());
            pw.println("3D - " +  CinemaHall3D.getEarned_money());
            pw.println("7D - " + CinemaHall7D.getEarned_money());
            pw.println("Comfort - " +  CinemaHallComfort.getEarned_money());
            pw.println("VIP - " +  CinemaHallVIP.getEarned_money());
            pw.close();

        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }



    }
}
