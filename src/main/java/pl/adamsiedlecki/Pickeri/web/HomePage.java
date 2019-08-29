package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;



@Route("home")
public class HomePage extends VerticalLayout {

    private VerticalLayout root;


    public HomePage() {

        root = new VerticalLayout();

        root.add(new Label("Welcome to Pickeri!"));


        this.add(root);
    }
}
