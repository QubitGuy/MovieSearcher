package is.moviesearcher.Service.Implementation;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import is.moviesearcher.Persistence.Movie;
import is.moviesearcher.Service.MovieService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieImplementation implements MovieService {

    private final List<Movie> movieRepo = new ArrayList<>();

    private final static String host = "https://movie-database-alternative.p.rapidapi.com/";
    private final static String X_RAPIDAPI_KEY = "d9e00b2fc7msh738d1b591013f04p12ca9cjsn02836d7eb9d1";
    private final static String X_RAPIDAPI_HOST = "movie-database-alternative.p.rapidapi.com";

    @Override
    public List<Movie> getMovieByTitle(String query) {

        movieRepo.clear();
        try {

            String charset = "UTF-8";
            String keyword = String.format("s=%s", URLEncoder.encode(query, charset));

            HttpResponse<JsonNode> httpResponse = Unirest.get(host + "?" + keyword)
                    .header("X-RapidAPI-Key", X_RAPIDAPI_KEY)
                    .header("X-RapidAPI-Host", X_RAPIDAPI_HOST)
                    .asJson();

            int responseStatus = httpResponse.getStatus();
            System.out.println("HTTP Response Code: " + responseStatus);

            JSONParser parser = new JSONParser();
            Object objectQuery = parser.parse(httpResponse.getBody().toString());
            JSONObject jsonObjectQuery = (JSONObject) objectQuery;

            String queryResponse = (String) jsonObjectQuery.get("Response");
            System.out.println("JSON Object - Response State: " + queryResponse + "\n");

            JSONArray array = (JSONArray) jsonObjectQuery.get("Search");


            if (array == null) {
                movieRepo.add(new Movie("Mynd finnst ekki í gagnagrunni",
                        "", "", "", ""));
            } else {
                JSONObject search = (JSONObject) array.get(0);
                movieRepo.add(new Movie(
                        (String) search.get("Title"),
                        (String) search.get("Year"),
                        (String) search.get("imdbID"),
                        (String) search.get("Type"),
                        (String) search.get("Poster")
                ));

                if (movieRepo.get(0).getPoster().contains("N/A")) {
                    movieRepo.get(0).setPoster("");
                    System.out.println("New poster: " + movieRepo.get(0).getPoster());
                }
            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return movieRepo;
    }
}
