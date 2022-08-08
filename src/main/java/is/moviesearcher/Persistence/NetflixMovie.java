package is.moviesearcher.Persistence;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class NetflixMovie {
    private String title;
    private String title_type;
    private Long netflix_id;
    private String synopsis;
    private String imdb_id;
    private String poster;
    private String title_date;

    public NetflixMovie(String title, String title_type, Long netflix_id, String synopsis, String imdb_id, String poster, String title_date) {
        this.title = title;
        this.title_type = title_type;
        this.netflix_id = netflix_id;
        this.synopsis = synopsis;
        this.imdb_id = imdb_id;
        this.poster = poster;
        this.title_date = title_date;
    }

    public NetflixMovie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_type() {
        return title_type;
    }

    public void setTitle_type(String title_type) {
        this.title_type = title_type;
    }

    public Long getNetflix_id() {
        return netflix_id;
    }

    public void setNetflix_id(Long netflix_id) {
        this.netflix_id = netflix_id;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle_date() {
        return title_date;
    }

    public void setTitle_date(String title_date) {
        this.title_date = title_date;
    }

    @Override
    public String toString() {
        return "NetflixMovie{" +
                "title='" + title + '\'' +
                ", title_type='" + title_type + '\'' +
                ", netflix_id='" + netflix_id + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", imdb_id='" + imdb_id + '\'' +
                ", poster='" + poster + '\'' +
                ", title_date='" + title_date + '\'' +
                '}';
    }
}
