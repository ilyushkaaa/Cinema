import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException{
        Entertainment entertainment = new Entertainment();
        String path_discount = "discount.bin";
        File file_discount = new File(path_discount);
        if (file_discount.createNewFile()){
            Discount discount = new Discount(entertainment);
            try {
                FileOutputStream fos = new FileOutputStream(path_discount);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(discount);
                oos.close();
                fos.close();
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
        }
        else {
           try{
               FileInputStream fis = new FileInputStream(path_discount);
               ObjectInputStream ois = new ObjectInputStream(fis);
               Discount discount = (Discount) ois.readObject();
               Discount newDiscount = new Discount(entertainment, discount.getDiscount_friend(),
                       discount.getDiscount_VIP(), discount.getDiscount_simple(), discount.getNum_of_tickets_discount(),
                       discount.getType_of_discount(), discount.getOld_discount(), discount.getType_of_client_discount(),
                       discount.getHall_discount(), discount.getDate_of_discount(), discount.getSession_discount(),
                       discount.isDiscount_is_on());
               ois.close();
               fis.close();
           }
           catch(ClassNotFoundException e){
              e.printStackTrace();
           }
        }

        Admin admin = new Admin("Petr", "Petrov", "Petrovich", "1357",
                "petr@mail.ru", 0, entertainment);
        entertainment.setAdmin(admin);
        Marketer marketer = new Marketer("Ivan", "Ivanov", "Petrovich", "1111",
                "ivanr@mail.ru", 0, entertainment);

        String path_client = "client.bin";
        File file_client = new File(path_client);

        if (file_client.createNewFile()){


            Client Leo = new Client("Lionel", "Messi", "Ivanovich", "1234",
                    "messi@mail.ru", 1650, entertainment);

            try{
                FileOutputStream fos = new FileOutputStream(path_client);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeInt(entertainment.getClients().size());
                for (User user : entertainment.getClients()){
                    oos.writeObject(user);
                    if (user.getType().equals("Simple")){
                        Client.serializeCount(oos);
                    }
                    else {
                        if (user.getType().equals("Friend")){
                            ClientFriend.serializeCount(oos);
                        }
                        else {
                            ClientVIP.serializeCount(oos);
                        }
                    }


                }

                oos.close();
                fos.close();


            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }


        }
        else{
            try{
                FileInputStream fis = new FileInputStream(path_client);
                ObjectInputStream ois = new ObjectInputStream(fis);

                int size_clients = ois.readInt();
                for (int i = 0; i < size_clients; ++i){
                    User user = (User) ois.readObject();
                   /* if (i == 0){
                        Admin admin = new Admin(user.getName(), user.getSurname(), user.getPatronymic(),
                                user.getPhone_number(), user.getEmail(), user.getMoney(), entertainment,
                                user.getVisits(), user.getTickets());
                        entertainment.setAdmin(admin);
                    }*/
                    //else {
                        if (user.getVisits() < 2){
                            int count = Client.deserializeCount(ois);
                            Client client = new Client(user.getName(), user.getSurname(), user.getPatronymic(),
                                    user.getPhone_number(), user.getEmail(), user.getMoney(), entertainment,
                                    user.getVisits(), user.getTickets(), count);

                        }
                        else{
                            if(user.getVisits() < 4){
                                int count = ClientFriend.deserializeCount(ois);
                                ClientFriend clientfr = new ClientFriend(user.getName(), user.getSurname(), user.getPatronymic(),
                                        user.getPhone_number(), user.getEmail(), user.getMoney(), entertainment,
                                        user.getVisits(), user.getTickets(), count);
                            }
                            else {
                                int count = ClientVIP.deserializeCount(ois);
                                ClientVIP clientvip = new ClientVIP(user.getName(), user.getSurname(), user.getPatronymic(),
                                        user.getPhone_number(), user.getEmail(), user.getMoney(), entertainment,
                                        user.getVisits(), user.getTickets(), count);
                            }
                        }

                   // }

                }
                ois.close();
                fis.close();

            }
            catch(IOException e){
                e.printStackTrace();

            }
            catch (ClassNotFoundException e){
                e.printStackTrace();
            }

        }

        String path_film = "films.bin";
        File file_film = new File(path_film);
        String path_cinema = "cinema.bin";
        File file_cinema = new File(path_cinema);
        String path_cinemaHall = "CinemaHall.bin";
        File file_cinemaHall = new File(path_cinemaHall);
        String path_session = "Session.bin";
        File file_session = new File(path_session);
        if (file_film.createNewFile()){
            Film GreenMile = new Film(entertainment, "Green_mile", 1999, "drama", 189, "2D");
            Film Brat2 = new Film(entertainment, "Brat_2", 2000, "thriller", 127, "2D");
            Film ChebUrashka = new Film(entertainment, "Cheburashka", 2023, "comedy", 113, "2D");

            Film Police = new Film(entertainment, "Politseiskiy s Rublyovki", 2018, "comedy",
                    120, "2D");

            Film Forest_Gump = new Film(entertainment, "ForestGump", 1994, "drama", 142, "3D");
            Film Trener_ = new Film(entertainment, "Trener", 2018, "drama/sport", 138, "3D");

            Film Elki = new Film(entertainment, "Elki5", 2015, "comedy", 110, "7D");
            Film AvataR = new Film(entertainment, "Avatar", 2009, "Science fiction", 161, "7D");
            try {
                FileOutputStream fos = new FileOutputStream(path_film);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeInt(entertainment.getAll_films().size());
                for (Film film: entertainment.getAll_films()){
                    oos.writeObject(film);
                }
                oos.close();
                fos.close();

            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }

            if (file_cinema.createNewFile()){
                String[] luxor_formats = {"3D", "2D", "7D"};
                Cinema luxor = new Cinema("Luxor", "Studencheskaya", luxor_formats, entertainment,
                        0, 0);
                try {
                    FileOutputStream fos = new FileOutputStream(path_cinema);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeInt(entertainment.getAll_cinema().size());
                    for (Cinema cinema: entertainment.getAll_cinema()){
                        oos.writeObject(cinema);
                    }
                    oos.close();
                    fos.close();

                }
                catch(FileNotFoundException e){
                    e.printStackTrace();
                }
                if (file_cinemaHall.createNewFile()){
                    CinemaHall2D luxor_1 = new CinemaHall2D(entertainment,200, 9, 8, luxor, 0);
                    CinemaHall3D luxor_2 = new CinemaHall3D(entertainment,160, 6, 7, luxor, 0);
                    CinemaHall7D luxor_3 = new CinemaHall7D(entertainment,100, 3, 8, luxor, 0);
                    CinemaHallComfort luxor_4 = new CinemaHallComfort(entertainment,120, 3, 6, luxor, 0);
                    CinemaHallVIP luxor_5 = new CinemaHallVIP(entertainment,180, 9, luxor, 3, 0);
                    try{
                        FileOutputStream fos = new FileOutputStream(path_cinemaHall);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeInt(luxor.getNum_of_halls());
                        for (CinemaHall cinemaHall: luxor.getOur_halls()){
                            oos.writeObject(cinemaHall);
                            switch (cinemaHall.getType()) {
                                case "2D":
                                    CinemaHall2D.serialize_money(oos);
                                    break;
                                case "3D":
                                    CinemaHall3D.serialize_money(oos);
                                    break;
                                case "7D":
                                    CinemaHall7D.serialize_money(oos);
                                    break;
                                case "Comfort":
                                    CinemaHallComfort.serialize_money(oos);
                                    break;
                                case "VIP":
                                    CinemaHallVIP.serialize_money(oos);
                                    break;
                            }
                        }
                        oos.close();
                        fos.close();

                    }
                    catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                    if(file_session.createNewFile()){
                        Session Forest_Gump_1 = new Session(entertainment, luxor, luxor_2, Forest_Gump.getName(),
                                "13:00");
                        Session Forest_Gump_2 = new Session(entertainment, luxor, luxor_2, Forest_Gump.getName(),
                                "18:00");
                        Session Trener_1 = new Session(entertainment, luxor, luxor_2, Trener_.getName(),
                                "15:00");
                        Session Trener_2 = new Session(entertainment, luxor, luxor_2, Trener_.getName(),
                                "18:00");
                        Session Elki_1 = new Session(entertainment,luxor, luxor_3,  Elki.getName(),
                                "21:00");
                        Session Elki_2 = new Session(entertainment,luxor, luxor_3,  Elki.getName(),
                                "17:00");
                        Session AvataR_1 = new Session(entertainment, luxor, luxor_3, AvataR.getName(),
                                "19:00");
                        Session AvataR_2 = new Session(entertainment, luxor, luxor_3, AvataR.getName(),
                                "16:00");

                        Session GreenMile4 = new Session(entertainment, luxor, luxor_4, GreenMile.getName(),
                                "17:00");
                        Session Police_1 = new Session(entertainment,luxor, luxor_4, Police.getName(),
                                "22:00");
                        Session Police_2 = new Session(entertainment, luxor, luxor_4, Police.getName(),
                                "19:00");
                        Session Brat2_5 = new Session(entertainment,luxor, luxor_5, Brat2.getName(),
                                "18:00");
                        Session Cheburashka_4 = new Session(entertainment,luxor, luxor_5, ChebUrashka.getName(),
                                "21:00");

                        Session GreenMile1 = new Session(entertainment,luxor, luxor_1, GreenMile.getName(),
                                "13:00");
                        Session GreenMile2 = new Session(entertainment,luxor, luxor_1, GreenMile.getName(),
                                "14:00");
                        Session GreenMile3 = new Session(entertainment,luxor, luxor_1, GreenMile.getName(),
                                "21:00");
                        Session Brat2_1 = new Session(entertainment,luxor, luxor_1, Brat2.getName(),
                                "15:00");
                        Session Brat2_2 = new Session(entertainment,luxor, luxor_1, Brat2.getName(),
                                "16:00");
                        Session Brat2_3 = new Session(entertainment,luxor, luxor_1, Brat2.getName(),
                                "19:00");
                        Session Brat2_4 = new Session(entertainment,luxor, luxor_1, Brat2.getName(),
                                "23:00");
                        Session Cheburashka_1 = new Session(entertainment,luxor, luxor_1, ChebUrashka.getName(),
                                "17:00");
                        Session Cheburashka_2 = new Session(entertainment,luxor, luxor_1, ChebUrashka.getName(),
                                "18:00");
                        Session Cheburashka_3 = new Session(entertainment,luxor, luxor_1, ChebUrashka.getName(),
                                "22:00");
                        try{
                            FileOutputStream fos = new FileOutputStream(path_session);
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            oos.writeInt(luxor.getSessions().size());
                            for (Session session: luxor.getSessions()){
                                oos.writeObject(session);
                            }
                            oos.close();
                            fos.close();

                        }
                        catch(FileNotFoundException e){
                            e.printStackTrace();
                        }

                    }
                }


            }


        }
        else{
            try{
                FileInputStream fis = new FileInputStream(path_film);
                ObjectInputStream ois = new ObjectInputStream(fis);
                int size_film = ois.readInt();
                for (int i = 0; i < size_film; ++i){
                    entertainment.add_film((Film)ois.readObject());
                }
                ois.close();
                fis.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            catch (ClassNotFoundException e){
                e.printStackTrace();
            }
            try{
                FileInputStream fis = new FileInputStream(path_cinema);
                ObjectInputStream ois = new ObjectInputStream(fis);
                int size_cinema = ois.readInt();
                for (int i = 0; i < size_cinema; ++i){
                    Cinema cinema = (Cinema) ois.readObject();
                    Cinema new_cinema = new Cinema(cinema.getName(), cinema.getAddress(), cinema.getFormats(),
                            entertainment, cinema.getSell_tickets(), cinema.getEarned_money());
                }
                ois.close();
                fis.close();
            }
            catch(IOException e){
                e.printStackTrace();

            }
            catch(ClassNotFoundException e){
                e.printStackTrace();
            }
            try{
                FileInputStream fis = new FileInputStream(path_cinemaHall);
                ObjectInputStream ois = new ObjectInputStream(fis);

                for (int j = 0; j < entertainment.getAll_cinema().size(); ++j){
                    int size_cinemaHall = ois.readInt();
                    for (int i = 0; i < size_cinemaHall; ++i){

                        CinemaHall cinemaHall = (CinemaHall) ois.readObject();
                        int money;
                        switch (cinemaHall.getType()){
                            case "2D": money = CinemaHall2D.deserialize_money(ois);
                                CinemaHall2D cinemaHall2D =  new CinemaHall2D(entertainment,cinemaHall.getHall_area(),
                                    cinemaHall.getNum_of_rows(), cinemaHall.getNum_of_places(),
                                    entertainment.getAll_cinema().get(j), money);
                            break;
                            case "3D":  money = CinemaHall3D.deserialize_money(ois);
                                CinemaHall3D cinemaHall3D =  new CinemaHall3D(entertainment,cinemaHall.getHall_area(),
                                    cinemaHall.getNum_of_rows(), cinemaHall.getNum_of_places(),
                                    entertainment.getAll_cinema().get(j), money); break;
                            case "7D":  money = CinemaHall7D.deserialize_money(ois);
                                CinemaHall7D cinemaHall7D =  new CinemaHall7D(entertainment,cinemaHall.getHall_area(),
                                    cinemaHall.getNum_of_rows(), cinemaHall.getNum_of_places(),
                                    entertainment.getAll_cinema().get(j), money); break;
                            case "Comfort":  money = CinemaHallComfort.deserialize_money(ois);
                                CinemaHallComfort cinemaHallComfort =  new
                                    CinemaHallComfort(entertainment,cinemaHall.getHall_area(),
                                    cinemaHall.getNum_of_rows(), cinemaHall.getNum_of_places(),
                                    entertainment.getAll_cinema().get(j), money); break;
                            case "VIP":  money = CinemaHallVIP.deserialize_money(ois);
                                CinemaHallVIP cinemaHallVIP =  new CinemaHallVIP(entertainment,cinemaHall.getHall_area(),
                                    cinemaHall.getNum_of_places(), entertainment.getAll_cinema().get(j),
                                    cinemaHall.getNum_of_free_dishes(), money); break;
                        }
                    }
                }

                ois.close();
                fis.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            catch (ClassNotFoundException e){
                e.printStackTrace();
            }
            try{
                FileInputStream fis = new FileInputStream(path_session);
                ObjectInputStream ois = new ObjectInputStream(fis);

                for (int j = 0; j < entertainment.getAll_cinema().size(); ++j){
                    int size_session = ois.readInt();
                    for (int m = 0; m < size_session; ++m){
                        Session session = (Session) ois.readObject();
                        Session new_session = new Session(entertainment, entertainment.getAll_cinema().get(j),
                                session.getOur_CinemaHall(), session.getFilm_name(), session.getTime(),
                                session.getFree_places());

                    }
                }
                ois.close();
                fis.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            catch (ClassNotFoundException e){
                e.printStackTrace();
            }


        }

        Scanner scanner = new Scanner(System.in);
        String go;
        do {
            String who;
            do{
                System.out.println("Вы: 1 - клиент, 2 - администратор, 3 - маркетолог");
                who = scanner.nextLine();
            }
            while(!who.equals("1") && !who.equals("2") && !who.equals("3"));
            if(who.equals("1")){

                System.out.println("Введите ваш номер телефона");
                boolean is_client = false;
                int index_client = 0;
                String number = scanner.nextLine();
                for (int i = 0; i < entertainment.getClients().size(); ++i){
                    if (number.equals(entertainment.getClients().get(i).getPhone_number())){
                        is_client = true;
                        index_client = i;
                        break;
                    }
                }

                if(is_client){
                   System.out.println("Выберите кинотеатр");
                   int ind_cinema = entertainment.getClients().get(index_client).choose_cinema(entertainment);
                   if (ind_cinema != 1000){
                       entertainment.getClients().get(index_client).choose_session
                               (entertainment.getAll_cinema().get(ind_cinema - 1), entertainment);
                   }
                   else{
                       System.out.println("Нет доступных кинотеатров");
                   }


                }
                else {
                    System.out.println("Клиента с таким номером телефона нет");
                    System.out.println("Создать новый аккаунт? 1 - да  Остальное - нет");
                    String make_new = scanner.nextLine();
                    if (make_new.equals("1")){
                        Client new_client = new Client(entertainment);
                        int ind_cinema = new_client.choose_cinema(entertainment);
                        if (ind_cinema != 1000){
                            new_client.choose_session(entertainment.getAll_cinema().get(ind_cinema - 1), entertainment);
                        }
                        else{
                            System.out.println("Нет доступных кинотеатров");
                        }

                    }
                }
            }
            else {
                if (who.equals("2")){
                    entertainment.getAdmin().authentication(entertainment);
                }
                else {
                    entertainment.getMarketer().authentication(entertainment);
                }
            }
            System.out.println("Продолжим работу?  1 - да   Всё остальное - нет");
            go = scanner.nextLine();
        }
        while(go.equals("1"));
        try{
            FileOutputStream fos = new FileOutputStream(path_client);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeInt(entertainment.getClients().size());
            //oos.writeObject(entertainment.getAdmin());
            for (User user : entertainment.getClients()){
                oos.writeObject(user);
                if (user.getType().equals("Simple")){
                    Client.serializeCount(oos);
                }
                else {
                    if (user.getType().equals("Friend")){
                        ClientFriend.serializeCount(oos);
                    }
                    else {
                        ClientVIP.serializeCount(oos);
                    }
                }
            }
            oos.close();
            fos.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        try {
            FileOutputStream fos = new FileOutputStream(path_cinema);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeInt(entertainment.getAll_cinema().size());
            for (Cinema cinema: entertainment.getAll_cinema()){
                oos.writeObject(cinema);
            }
            oos.close();
            fos.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }try{
            FileOutputStream fos = new FileOutputStream(path_cinemaHall);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (int i = 0; i < entertainment.getAll_cinema().size(); ++i){
                oos.writeInt(entertainment.getAll_cinema().get(i).getNum_of_halls());
                for (CinemaHall cinemaHall: entertainment.getAll_cinema().get(i).getOur_halls()){
                    oos.writeObject(cinemaHall);
                    switch (cinemaHall.getType()) {
                        case "2D":
                            CinemaHall2D.serialize_money(oos);
                            break;
                        case "3D":
                            CinemaHall3D.serialize_money(oos);
                            break;
                        case "7D":
                            CinemaHall7D.serialize_money(oos);
                            break;
                        case "Comfort":
                            CinemaHallComfort.serialize_money(oos);
                            break;
                        case "VIP":
                            CinemaHallVIP.serialize_money(oos);
                            break;
                    }
                }
            }
            oos.close();
            fos.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        try{
            FileOutputStream fos = new FileOutputStream(path_session);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (int i = 0; i < entertainment.getAll_cinema().size(); ++i){
                oos.writeInt(entertainment.getAll_cinema().get(i).getSessions().size());
                for (Session session: entertainment.getAll_cinema().get(i).getSessions()){
                    oos.writeObject(session);
                }

            }
            oos.close();
            fos.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        try{
            FileOutputStream fos = new FileOutputStream(path_discount);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(entertainment.getDiscount());
            oos.close();
            fos.close();

        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        System.out.println("До новых встреч!");
    }
}