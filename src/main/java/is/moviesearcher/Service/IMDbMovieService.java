package is.moviesearcher.Service;

import is.moviesearcher.Persistence.IMDbMovie;

import java.util.List;

public interface IMDbMovieService {
    List<IMDbMovie> getIMDbMovieByID(String IMDbID);
}
