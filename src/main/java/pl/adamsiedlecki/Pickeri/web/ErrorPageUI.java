package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;

import java.util.Map;
import java.util.Set;


@SpringUI(path = "/errorPage")
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
        root.addComponent(new Embedded("", new FileResource(ResourceGetter.getPickeriLogo())));
        this.setContent(root);
    }
}
