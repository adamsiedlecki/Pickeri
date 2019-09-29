package pl.adamsiedlecki.Pickeri.web;

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
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;


@SpringUI(path = "/errorPage")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Squada+One|Ubuntu&display=swap"})
@Title("Error")
public class ErrorPageUI extends UI {

    private VerticalLayout root;

    public ErrorPageUI() {
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        root.addComponent(new Label(request.getPathInfo()));
        root.addComponent(new Label("Nie znaleziono elementu."));
        Embedded picture = new Embedded("Wystąpił błąd. Jesteś w polu.", new FileResource(ResourceGetter.getTractorPicture()));
        picture.setWidth(50, Unit.PERCENTAGE);
        picture.setHeight(50, Unit.PERCENTAGE);
        root.addComponent(picture);
        root.addComponent(ResourceGetter.getPickeriLogoAsEmbeddedComponent());
        this.setContent(root);
    }
}
