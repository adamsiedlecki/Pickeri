package pl.adamsiedlecki.Pickeri.tools.userInterfaceTools;


import com.google.common.io.Resources;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import java.io.File;
import java.net.URL;

public class ResourceGetter {

    public static Embedded getPickeriLogoAsEmbeddedComponent() {
        Embedded logo = new Embedded("", new FileResource(ResourceGetter.getPickeriLogo()));
        logo.setHeight(80, Sizeable.Unit.PIXELS);
        logo.setWidth(160, Sizeable.Unit.PIXELS);
        logo.setCaption("");
        return logo;
    }

    public static HorizontalLayout getSiedleckiLogoWithLayout() {
        Image logo = new Image(null, new FileResource(ResourceGetter.getSiedleckiBlackLogo()));
        logo.setWidth(370, Sizeable.Unit.PIXELS);
        logo.setStyleName("logo");
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setMargin(false);
        logoLayout.setCaption(null);
        logoLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
        logoLayout.addComponent(logo);
        logoLayout.addStyleName("logo-div");
        logoLayout.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoLayout.setHeight(logo.getHeight(), logo.getHeightUnits());
        return logoLayout;
    }

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
