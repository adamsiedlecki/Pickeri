package pl.adamsiedlecki.Pickeri.tools.userInterfaceTools;


import com.google.common.io.Resources;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

public class ResourceGetter {

    private static final Logger log = LoggerFactory.getLogger(ResourceGetter.class);

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
        logo = new File("src\\main\\resources\\images\\" + fileNameWithExtension);
        if (!logo.exists()) {
            logo = new File("src/main/resources/images/" + fileNameWithExtension);
        }
        if (!logo.exists()) {
            logo = new File("images\\" + fileNameWithExtension);
        }
        if (!logo.exists()) {
            logo = new File("src/main/resources/images/" + fileNameWithExtension);
        }
        if (!logo.exists()) {
            logo = new File(fileNameWithExtension);
        }
        if (!logo.exists()) {
            logo = new File("/images/" + fileNameWithExtension);
        }
        if (!logo.exists()) {
            URL url = Resources.getResource("/images/" + fileNameWithExtension);
            logo = new File(url.getFile());
        }
        if (!logo.exists()) {
            log.error("ERROR: " + fileNameWithExtension + " NOT FOUND");
        }

        return logo;
    }

}
