package is.moviesearcher.Controllers;

import is.moviesearcher.Persistence.IMDbMovie;
import is.moviesearcher.Persistence.MDAMovie;
import is.moviesearcher.Service.IMDbMovieService;
import is.moviesearcher.Service.MDAMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class MovieController {

    private final MDAMovieService MDAMovieService;


    @Autowired
    public MovieController(MDAMovieService MDAMovieService) {
        this.MDAMovieService = MDAMovieService;
    }

    //private final IMDbMovieService IMDbMovieService;

    /*
    @Autowired
    public MovieController(is.moviesearcher.Service.IMDbMovieService imDbMovieService) {
        this.IMDbMovieService = imDbMovieService;
    }

     */

    @RequestMapping("/")
    public String Home(){

        return "index";
    }

    @GetMapping(value = "/index")
    public String movieSearcherGET(@RequestParam(name="query", required = false, defaultValue = "")
                                               String query, Model model) {
        try {

            List<MDAMovie> MDAMovie = MDAMovieService.getMDAMovieByTitle(query);

            // List<IMDbMovie> IMDbMovie = IMDbMovieService.getIMDbMovieByID(query);

            model.addAttribute("movies", MDAMovie);
            //model.addAttribute("movies", IMDbMovie);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "index";
    }
}
