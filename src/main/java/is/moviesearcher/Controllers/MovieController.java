package is.moviesearcher.Controllers;

import is.moviesearcher.Persistence.Movie;
import is.moviesearcher.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping("/")
    public String Home(){

        return "index";
    }

    @GetMapping(value = "/index")
    public String movieSearcherGET(@RequestParam(name="query", required = false, defaultValue = "")
                                               String query, Model model) {
        try {

            List<Movie> movie = movieService.getMovieByTitle(query);

            System.out.println("");
            model.addAttribute("movies", movie);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "index";
    }
}
