package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("prototype")
public class OthersTab extends VerticalLayout {

    public OthersTab(){
        this.addComponent(new Link("Dodawanie owoców",new ExternalResource("/")));
        this.addComponent(new Link("Statystyki",new ExternalResource("/statistics-and-info")));
        this.addComponent(new Link("Inne",new ExternalResource("/other")));
    }

}
