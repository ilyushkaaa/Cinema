import java.io.Serializable;
import java.util.List;

public class Film implements Serializable {
    private final String name;
    private final int year;
    private final String genre;
    private final int duration;
    private final String format;

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public String getFormat() {
        return format;
    }
    public void PrintFilmInfo(){
        System.out.println("Название " + getName());
        System.out.println("Год " + getYear());
        System.out.println("Жанр " + getGenre());
        System.out.println("Продолжительность " + getDuration());
        System.out.println("Формат " + getFormat());
    }

    public Film(Entertainment entertainment, String name, int year, String genre, int duration, String format) {
        this.format = format;
        this.name = name;
        this.genre = genre;
        this.duration = duration;
        this.year = year;
        entertainment.add_film(this);

    }
}
