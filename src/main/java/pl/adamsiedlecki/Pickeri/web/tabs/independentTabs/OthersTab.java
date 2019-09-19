package pl.adamsiedlecki.Pickeri.web.tabs.independentTabs;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import org.springframework.context.annotation.Scope;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;

@SpringComponent
@Scope("prototype")
public class OthersTab extends VerticalLayout {

    public OthersTab() {
        this.addComponent(new Link("Dodawanie owoców", new ExternalResource("/")));
        this.addComponent(new Link("Statystyki i pracownicy", new ExternalResource("/statistics-and-info")));
        this.addComponent(new Link("Ranking", new ExternalResource("/ranking")));
        this.addComponent(new Link("Wszystkie dostawy", new ExternalResource("/all-deliveries")));
        this.addComponent(new Link("Wydatki", new ExternalResource("/expenses")));
        this.addComponent(new Link("Inne", new ExternalResource("/other")));
        this.addComponent(new Link("Wyloguj", new ExternalResource("/logout")));
        Embedded logo = new Embedded("", new FileResource(ResourceGetter.getSiedleckiBlackLogo()));
        logo.setWidth(60, Unit.PERCENTAGE);
        this.addComponent(logo);

    }

}
