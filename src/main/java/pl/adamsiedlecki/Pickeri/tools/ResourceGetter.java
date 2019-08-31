package pl.adamsiedlecki.Pickeri.tools;

import java.io.File;

public class ResourceGetter {

    public static File getPickeriLogo(){
            File logo = null;
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("win") >= 0) {
                logo = new File("src\\main\\resources\\images\\pickeri.png");
            } else if (osName.indexOf("nux") >= 0) {
                logo = new File("src/main/resources/images/pickeri.png");

            }
            return logo;
    }

}
