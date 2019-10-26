package pl.adamsiedlecki.Pickeri.tools.apiInteraction;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DeviceApiInteraction {

    private static final Logger log = LoggerFactory.getLogger(DeviceApiInteraction.class);

    public static void setDeviceState(String address, int pin, boolean enabled, String key){
        String fullApiAddress;
        if(address.endsWith("/")){
            fullApiAddress = address+"api/v1/setDevStateByPin";
        }else{
            fullApiAddress = address+"/api/v1/setDevStateByPin";
        }

        ApiDevice apiDevice = new ApiDevice(pin,"",key);
        if(enabled){
            apiDevice.setState("on");
        }else{
            apiDevice.setState("off");
        }

        String result = getWithPOST(fullApiAddress, apiDevice);
        log.info("RESULT OF API INTERACTION: "+result);

    }

    public static boolean getDeviceState(String address, int pin){

        String fullApiAddress;
        if(address.endsWith("/")){
            fullApiAddress = address+"api/v1/getDevStateByPin?devicePin="+pin;
        }else{
            fullApiAddress = address+"/api/v1/getDevStateByPin?devicePin="+pin;
        }

        try {
            String result = readStringFromURL(fullApiAddress);
            log.info("RESULT OF API INTERACTION: "+result);
            if(result.equals("ENABLED")){
                return true;
            }else if(result.equals("DISABLED")){
                return false;
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.info("Device is neither DISABLED or ENABLED.");
        return false;
    }

    public static String getWithPOST(String url, ApiDevice apiDevice){
        CloseableHttpResponse response;
        String json = apiDevice.toString();
        try {
            StringEntity entity = new StringEntity(json);
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);

            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "text/html");
            httpPost.setHeader("Content-type", "application/json");

            response = client.execute(httpPost);
            client.close();
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            return e.getMessage();
        } catch (ClientProtocolException e) {
            log.error(e.getMessage());
            return e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return response.toString();

    }

    public static String readStringFromURL(String requestURL) throws IOException
    {
        try (Scanner scanner = new Scanner(new URL(requestURL).openStream(),
                StandardCharsets.UTF_8.toString()))
        {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }



}
