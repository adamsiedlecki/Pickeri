package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


@SpringUI(path="/home")
public class HomePage extends UI {

    private VerticalLayout root;

    @Override
    protected void init(VaadinRequest request) {

        root = new VerticalLayout();

        root.addComponent(new Label("Welcome to Pickeri!"));
        root.addComponent(new Link("LOGIN PAGE",new ExternalResource("/login")));

        this.setContent(root);
    }
}
