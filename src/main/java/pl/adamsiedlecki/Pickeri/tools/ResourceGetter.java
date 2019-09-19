package pl.adamsiedlecki.Pickeri.tools;


import com.google.common.io.Resources;

import java.io.File;
import java.net.URL;

public class ResourceGetter {

    public static File getPickeriLogo() {
        return getFileByName("pickeri.png");
    }

    public static File getSiedleckiBlackLogo() {
        return getFileByName("logo2.png");
    }

    public static File getSiedleckiWhiteLogo() {
        return getFileByName("logo9.png");
    }

    public static File getTractorPicture() {
        return getFileByName("trzydziestka.jpg");
    }

    private static File getFileByName(String fileNameWithExtension) {
        File logo = null;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            logo = new File("src\\main\\resources\\images\\" + fileNameWithExtension);
        } else if (osName.contains("nux")) {
            logo = new File("trzydziestka.jpg");
            if (!logo.exists()) {
                logo = new File("src/main/resources/images/" + fileNameWithExtension);
            }
            if (!logo.exists()) {
                URL url2 = Resources.getResource("images/" + fileNameWithExtension);
                logo = new File(url2.getFile());
            }
            if (!logo.exists()) {
                logo = new File("src/main/resources/images/" + fileNameWithExtension);
            }
            if (!logo.exists()) {
                logo = new File(fileNameWithExtension);
            }
            if (!logo.exists()) {
                URL url = Resources.getResource("/images/" + fileNameWithExtension);
                logo = new File(url.getFile());
            }
            if (!logo.exists()) {
                System.out.println("ERROR: " + fileNameWithExtension + " NOT FOUND");
            }
        }
        return logo;
    }


}
