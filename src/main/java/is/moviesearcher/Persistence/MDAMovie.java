package is.moviesearcher.Persistence;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties
public class MDAMovie {


    private String Title;
    private String Year;
    private String imdbID;
    private String Type;
    private String Poster;

    private List<MDAMovie> MDAMovie = new ArrayList<MDAMovie>();

    public MDAMovie() {

    }

    public MDAMovie(String title, String year, String imdbID, String type, String poster) {
        this.Title = title;
        this.Year = year;
        this.imdbID = imdbID;
        this.Type = type;
        this.Poster = poster;
    }



    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    @Override
    public String toString() {
        return "Film{" +
                "Title='" + Title + '\'' +
                ", Poster='" + Poster + '\'' +
                '}';
    }
}
