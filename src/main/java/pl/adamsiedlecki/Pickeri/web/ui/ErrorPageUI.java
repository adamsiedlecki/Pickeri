package pl.adamsiedlecki.Pickeri.web.ui;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Title;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.ThemeSetter;


@SpringUI(path = "/errorPage")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${error.title}")
public class ErrorPageUI extends UI {

    private VerticalLayout root;
    private final Environment environment;

    @Autowired
    public ErrorPageUI(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        root.addComponent(new Label(request.getPathInfo()));
        root.addComponent(new Label(environment.getProperty("element.not.found")));
        Label messageLabel = new Label(environment.getProperty("error.page.label"));
        messageLabel.setStyleName("h2");
        root.addComponent(messageLabel);
        Image picture = new Image("", new FileResource(ResourceGetter.getTractorPicture()));
        picture.setWidth(50, Unit.PERCENTAGE);
        picture.setHeight(50, Unit.PERCENTAGE);
        root.addComponent(picture);
        root.addComponent(ResourceGetter.getPickeriLogoAsEmbeddedComponent());
        this.setContent(root);
        ThemeSetter.set(this);
        root.forEach(component -> root.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
    }
}
