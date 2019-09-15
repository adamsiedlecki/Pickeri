package pl.adamsiedlecki.Pickeri.tools;


import java.io.File;
import java.net.URL;
import com.google.common.io.Resources;

public class ResourceGetter {

    public static File getPickeriLogo(){
            File logo = null;
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("win") >= 0) {
                logo = new File("src\\main\\resources\\images\\pickeri.png");
            } else if (osName.indexOf("nux") >= 0) {
                //URL url = Resources.getResource("pickeri.png");
                //logo = new File(url.getFile());
                logo = new File("pickeri.png");
                if(!logo.exists()){
                    logo = new File("src/main/resources/images/pickeri.png");
                }
                if(!logo.exists()){
                    URL url2 = Resources.getResource("images/pickeri.png");
                    logo = new File(url2.getFile());
                }
                if(!logo.exists()){
                    logo = new File("src/main/resources/images/pickeri.png");
                }
                if(!logo.exists()){
                    logo = new File("pickeri.png");
                }
                if(!logo.exists()){
                    URL url = Resources.getResource("/images/pickeri.png");
                    logo = new File(url.getFile());
                }
                if(!logo.exists()){
                    while(true){
                        System.out.println("ERROR: PICKERI LOGO NOT FOUND");
                    }
                }

            }
            return logo;
    }

    public static File getSiedleckiBlackLogo(){
            File logo = null;
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("win") >= 0) {
                logo = new File("src\\main\\resources\\images\\logo2.png");
            } else if (osName.indexOf("nux") >= 0) {
                //URL url = Resources.getResource("pickeri.png");
                //logo = new File(url.getFile());
                logo = new File("logo2.png");
                if(!logo.exists()){
                    logo = new File("src/main/resources/images/logo2.png");
                }
                if(!logo.exists()){
                    URL url2 = Resources.getResource("images/logo2.png");
                    logo = new File(url2.getFile());
                }
                if(!logo.exists()){
                    logo = new File("src/main/resources/images/logo2.png");
                }
                if(!logo.exists()){
                    logo = new File("logo2.png");
                }
                if(!logo.exists()){
                    URL url = Resources.getResource("/images/logo2.png");
                    logo = new File(url.getFile());
                }
                if(!logo.exists()){
                    while(true){
                        System.out.println("ERROR: SIEDLECKI LOGO NOT FOUND");
                    }
                }

            }
            return logo;
    }

    public static File getSiedleckiWhiteLogo(){
        File logo = null;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("win") >= 0) {
            logo = new File("src\\main\\resources\\images\\logo9.png");
        } else if (osName.indexOf("nux") >= 0) {
            logo = new File("logo9.png");
            if(!logo.exists()){
                logo = new File("src/main/resources/images/logo9.png");
            }
            if(!logo.exists()){
                URL url2 = Resources.getResource("images/logo9.png");
                logo = new File(url2.getFile());
            }
            if(!logo.exists()){
                logo = new File("src/main/resources/images/logo9.png");
            }
            if(!logo.exists()){
                logo = new File("logo9.png");
            }
            if(!logo.exists()){
                URL url = Resources.getResource("/images/logo9.png");
                logo = new File(url.getFile());
            }
            if(!logo.exists()){
                while(true){
                    System.out.println("ERROR: SIEDLECKI LOGO NOT FOUND");
                }
            }

        }
        return logo;
    }

}
