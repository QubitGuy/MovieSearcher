package is.moviesearcher.Controllers;

import is.moviesearcher.Persistence.NetflixMovie;
import is.moviesearcher.Service.NetflixMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class MovieController {

    private final NetflixMovieService netflixMovieService;

    @Autowired
    public MovieController(NetflixMovieService netflixMovieService) {
        this.netflixMovieService = netflixMovieService;
    }


    @RequestMapping("/")
    public String Home(){

        return "index";
    }

    @GetMapping(value = "/index")
    public String movieSearcherGET(@RequestParam(name="query", required = false, defaultValue = "")
                                               String query, Model model) {
        try {

            List<NetflixMovie> netflixMovies = netflixMovieService.getNetflixMovieByTitle(query);
            for (int i = 0; i < netflixMovies.size(); i++) {
                netflixMovies.get(i).setTitle(netflixMovies.get(i).getTitle() + " - Netflix");
            }

            model.addAttribute("movies", netflixMovies);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "index";
    }
}
