package pl.adamsiedlecki.Pickeri.tools.apiInteraction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DeviceApiInteraction {

    private static final Logger log = LoggerFactory.getLogger(DeviceApiInteraction.class);

    public static String setDeviceState(String address, int pin, boolean enabled){

        String fullApiAddress = address+"/api/v1/setDevState?"+pin;

        if(enabled){
            fullApiAddress = fullApiAddress.concat("?on");
        }else{
            fullApiAddress = fullApiAddress.concat("?off");
        }

        try {
            String result = readStringFromURL(fullApiAddress);
            log.info("RESULT OF API INTERACTION: "+result);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return "";
    }

    public static boolean getDeviceState(String address, int pin){

        String fullApiAddress = address+"/api/v1/getDevStateByPin?"+pin;

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
