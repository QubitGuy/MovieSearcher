package is.moviesearcher.Service.Implementation;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import is.moviesearcher.Persistence.IMDbMovie;
import is.moviesearcher.Persistence.MDAMovie;
import is.moviesearcher.Service.IMDbMovieService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class IMDbMovieImplementation implements IMDbMovieService {


    private final List<IMDbMovie> IMDbMovieRepo = new ArrayList<>();



    private final String host = "https://imdb-api.com/API/SearchAll/";
    private final String MY_IMDb_KEY = "k_7b339az0";

    @Override
    public List<IMDbMovie> getIMDbMovieByID(String IMDbID) {
        IMDbMovieRepo.clear();

        // IMDbID = "Veronica Mars";

        try {
            String charset = "UTF-8";
            String keyword = String.format("s=%s", URLEncoder.encode(IMDbID, charset));

            HttpResponse<JsonNode> httpResponse = Unirest
                    .get(host + "/" + MY_IMDb_KEY + "/" + keyword)
                    .asJson();

            int responseStatus = httpResponse.getStatus();
            System.out.println("HTTP Response Code: " + responseStatus);

            JSONParser parser = new JSONParser();
            JSONObject jsonObjectQuery = (JSONObject) parser.parse(httpResponse.getBody().toString());;
            JSONArray jsonArray = (JSONArray) jsonObjectQuery.get("results");

            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.size(); i++) {

                    JSONObject search = (JSONObject) jsonArray.get(i);

                    IMDbMovieRepo.add(new IMDbMovie(
                            (String) search.get("id"),
                            (String) search.get("resultType"),
                            (String) search.get("image"),
                            (String) search.get("title"),
                            (String) search.get("description")
                    ));

                    if (IMDbMovieRepo.get(i).getImage().isEmpty()) {
                        IMDbMovieRepo.get(i).setImage("/images/no-poster-found.jpg");
                    }
                }
            }

            IMDbMovieRepo.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return IMDbMovieRepo;
    }

    // public List<IMDbMovie> get

}
