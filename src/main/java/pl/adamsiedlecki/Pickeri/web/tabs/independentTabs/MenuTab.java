package pl.adamsiedlecki.Pickeri.web.tabs.independentTabs;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;

@SpringComponent
@Scope("prototype")
public class MenuTab extends VerticalLayout {

    private Environment env;

    @Autowired
    public MenuTab(Environment environment) {
        this.env = environment;
        this.addComponent(new Link(env.getProperty("add.fruits.ui"), new ExternalResource("/")));
        this.addComponent(new Link(env.getProperty("statistics.and.employees.ui"), new ExternalResource("/statistics-and-info")));
        this.addComponent(new Link(env.getProperty("ranking.ui"), new ExternalResource("/ranking")));
        this.addComponent(new Link(env.getProperty("all.deliveries.ui"), new ExternalResource("/all-deliveries")));
        this.addComponent(new Link(env.getProperty("expenses.ui"), new ExternalResource("/expenses")));
        this.addComponent(new Link(env.getProperty("payments.ui"), new ExternalResource("/picker-payments")));
        this.addComponent(new Link(env.getProperty("other.ui"), new ExternalResource("/other")));
        this.addComponent(new Link(env.getProperty("logout.button"), new ExternalResource("/logout")));
        Embedded logo = new Embedded("", new FileResource(ResourceGetter.getSiedleckiBlackLogo()));
        logo.setWidth(60, Unit.PERCENTAGE);
        this.addComponent(logo);

    }

}
