package is.moviesearcher.Service.Implementation;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import is.moviesearcher.Persistence.NetflixMovie;
import is.moviesearcher.Service.NetflixMovieService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NetflixMovieImplementation implements NetflixMovieService {

    private List<NetflixMovie> netflixMovieRepo = new ArrayList<>();

    private final List<NetflixMovie> netflixMovieRepoTemp = new ArrayList<>();

    private final List<NetflixMovie> netflixMovieRepoFinal = new ArrayList<>();

    private final static String HOST = "https://unogs-unogs-v1.p.rapidapi.com/search/titles?order_by=title&title=";
    private final static String X_RAPIDAPI_KEY = "d9e00b2fc7msh738d1b591013f04p12ca9cjsn02836d7eb9d1";
    private final String X_RAPIDAPI_HOST = "unogs-unogs-v1.p.rapidapi.com";

    @Override
    public List<NetflixMovie> getNetflixMovieByTitle(String query) {

        try {
            netflixMovieRepo.clear();

            String charset = "UTF-8";
            String keyword = String.format("%s", URLEncoder.encode(query, charset));
            HttpResponse<JsonNode> httpResponse = Unirest.get(HOST + keyword)
                    .header("X-RapidAPI-Key", X_RAPIDAPI_KEY)
                    .header("X-RapidAPI-Host", X_RAPIDAPI_HOST)
                    .asJson();

            int responseStatus = httpResponse.getStatus();
            System.out.println("\nHTTP Response Code: " + responseStatus);

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

            JSONArray jsonArray = (JSONArray) jsonObjectQuery.get("results");

            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.size(); i++) {

                    JSONObject search = (JSONObject) jsonArray.get(i);

                    String title = (String) search.get("title");
                    title = title.toLowerCase();

                    String sQ = query.toLowerCase();

                    List<String> netflixCountry;
                    boolean exists = title.contains(sQ);

                    int matchCounter = 0;
                    if (exists) {
                        long id = (Long) search.get("netflix_id");
                        netflixCountry = netflixMovieCountryByID(id);

                        netflixMovieRepo.add(new NetflixMovie(
                                (String) search.get("title"),
                                (Long) search.get("netflix_id"),
                                (String) search.get("synopsis"),
                                (String) search.get("poster"),
                                netflixCountry
                        ));

                        if (netflixMovieRepo.get(matchCounter).getPoster().isEmpty()) {
                            netflixMovieRepo.get(matchCounter).setPoster("/images/no-poster-found.jpg");
                        }
                        matchCounter++;
                        System.out.println("Title of film that got a positive match: " + search.get("title"));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return netflixMovieRepo;
    }

    @Override
    public List<String> netflixMovieCountryByID(long id) {

        List<String> netflixCountryName = new ArrayList<>();
        try {
            HttpResponse<JsonNode> httpResponse = Unirest.get("https://unogs-unogs-v1.p.rapidapi.com/title/countries?netflix_id=" + id)
                    .header("X-RapidAPI-Key", X_RAPIDAPI_KEY)
                    .header("X-RapidAPI-Host", X_RAPIDAPI_HOST)
                    .asJson();

            int responseStatus = httpResponse.getStatus();
            // System.out.println("\nHTTP Response Code | Netflix Country Side: " + responseStatus);

            JSONParser parser = new JSONParser();
            JSONObject jsonObjectQuery = (JSONObject) parser.parse(httpResponse.getBody().toString());

            JSONArray jsonArray = (JSONArray) jsonObjectQuery.get("results");

            for (int i = 0; i < jsonArray.size(); i++) {

                JSONObject search = (JSONObject) jsonArray.get(i);
                netflixCountryName.add((String) search.get("country"));
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return netflixCountryName;
    }


}
