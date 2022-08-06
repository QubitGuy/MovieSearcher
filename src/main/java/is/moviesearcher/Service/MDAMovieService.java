package is.moviesearcher.Service;

import is.moviesearcher.Persistence.MDAMovie;

import java.util.List;

public interface MDAMovieService {
    List<MDAMovie> getMDAMovieByTitle(String jsonString);
}
