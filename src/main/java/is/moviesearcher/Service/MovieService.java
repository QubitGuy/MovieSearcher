package is.moviesearcher.Service;

import is.moviesearcher.Persistence.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getMovieByTitle(String jsonString);
}
