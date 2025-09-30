package dueling.api;

import dueling.model.Card;
import java.net.http.*;
import java.net.URI;
import org.json.JSONObject;

public class YGO_Api {
    private static final String RANDOM_CARD_URL = "https://db.ygoprodeck.com/api/v7/randomcard.php";

    public static Card getRandomMonsterCard() throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(RANDOM_CARD_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        JSONObject json = new JSONObject(body);
        JSONObject cardJson;
        if (json.has("data")) {
            cardJson = json.getJSONArray("data").getJSONObject(0);
        } else {
            cardJson = json;
        }
        if (!cardJson.getString("type").contains("Monster")) {
            return getRandomMonsterCard();
        }
        String name = cardJson.getString("name");
        int atk = cardJson.optInt("atk", 0);
        int def = cardJson.optInt("def", 0);
        String imageUrl = cardJson
                .getJSONArray("card_images")
                .getJSONObject(0)
                .getString("image_url");

        return new Card(name, atk, def, imageUrl);
    }
}
