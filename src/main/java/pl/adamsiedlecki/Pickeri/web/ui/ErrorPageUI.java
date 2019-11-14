package pl.adamsiedlecki.Pickeri.web.ui;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.tools.UserInterfaceTools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.tools.UserInterfaceTools.ThemeSetter;


@SpringUI(path = "/errorPage")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${error.title}")
public class ErrorPageUI extends UI {

    private VerticalLayout root;
    private Environment environment;

    @Autowired
    public ErrorPageUI(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        root.addComponent(new Label(request.getPathInfo()));
        root.addComponent(new Label(environment.getProperty("element.not.found")));
        Embedded picture = new Embedded(environment.getProperty("error.page.label"), new FileResource(ResourceGetter.getTractorPicture()));
        picture.setWidth(50, Unit.PERCENTAGE);
        picture.setHeight(50, Unit.PERCENTAGE);
        root.addComponent(picture);
        root.addComponent(ResourceGetter.getPickeriLogoAsEmbeddedComponent());
        this.setContent(root);
        ThemeSetter.set(this);
    }
}
