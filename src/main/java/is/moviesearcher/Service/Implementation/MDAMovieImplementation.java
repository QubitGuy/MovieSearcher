package is.moviesearcher.Service.Implementation;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import is.moviesearcher.Persistence.MDAMovie;
import is.moviesearcher.Service.MDAMovieService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class MDAMovieImplementation implements MDAMovieService {

    private final List<MDAMovie> MDAMovieRepo = new ArrayList<>();

    private final static String host = "https://movie-database-alternative.p.rapidapi.com/";
    private final static String X_RAPIDAPI_KEY = "d9e00b2fc7msh738d1b591013f04p12ca9cjsn02836d7eb9d1";
    private final static String X_RAPIDAPI_HOST = "movie-database-alternative.p.rapidapi.com";

    @Override
    public List<MDAMovie> getMDAMovieByTitle(String query) {

        MDAMovieRepo.clear();
        try {

            String charset = "UTF-8";
            String keyword = String.format("s=%s", URLEncoder.encode(query, charset));

            HttpResponse<JsonNode> httpResponse = Unirest.get(host + "?" + keyword)
                    .header("X-RapidAPI-Key", X_RAPIDAPI_KEY)
                    .header("X-RapidAPI-Host", X_RAPIDAPI_HOST)
                    .asJson();

            int responseStatus = httpResponse.getStatus();
            System.out.println("HTTP Response Code: " + responseStatus);

            // Serializing JSON data
            JSONParser parser = new JSONParser();
            // Object objectQuery = parser.parse(httpResponse.getBody().toString());

            // Þessi kóði gerir það sama og kóðinn fyrir neðan og JSONArray kóðalínan líka,
            // nema það er læsilegri, snyrtilegri og betri aðgengi til að nota kóðann í ýmiskonar.
            /*
            * JSONArray jsonObjectQuery =
            * (JSONArray) ((JSONObject) parser.parse(httpResponse.getBody().toString())).get("Search");
            */
            JSONObject jsonObjectQuery = (JSONObject) parser.parse(httpResponse.getBody().toString());



            String queryResponse = (String) jsonObjectQuery.get("Response");
            System.out.println("JSON Object - Response State: " + queryResponse + "\n");

            JSONArray jsonArray = (JSONArray) jsonObjectQuery.get("Search");

            if (jsonArray == null) {
                MDAMovieRepo.add(new MDAMovie("Mynd finnst ekki í gagnagrunni",
                        "", "", "", ""));
            } else {

                for (int i = 0; i < jsonArray.size(); i++ ) {

                    JSONObject search = (JSONObject) jsonArray.get(i);

                    MDAMovieRepo.add(new MDAMovie(
                            (String) search.get("Title"),
                            (String) search.get("Year"),
                            (String) search.get("imdbID"),
                            (String) search.get("Type"),
                            (String) search.get("Poster")
                    ));

                    if (MDAMovieRepo.get(i).getPoster().contains("N/A")) {
                        MDAMovieRepo.get(i).setPoster("/images/no-poster-found.jpg");
                    }
                }
            }

            MDAMovieRepo.forEach(System.out::println);

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return MDAMovieRepo;
    }
}
