package is.moviesearcher.Service;


import is.moviesearcher.Persistence.NetflixMovie;

import java.util.List;

public interface NetflixMovieService {
    List<NetflixMovie> getNetflixMovieByTitle(String title);
}
