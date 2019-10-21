package pl.adamsiedlecki.Pickeri.tools.ApiReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.IOException;

public class InfoGetter {

    public static String getBitcoinPrice(Environment env){
        String url = env.getProperty("bitcoin.api");
        try {
            JSONObject json = JsonReader.readJsonFromUrl(url);
            JSONArray bids = (JSONArray) json.get("bids");
            JSONArray offer = (JSONArray) bids.get(0);
            String price = offer.get(0).toString();
            return price;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }


}
