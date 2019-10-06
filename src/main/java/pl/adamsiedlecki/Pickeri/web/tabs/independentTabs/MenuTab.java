package pl.adamsiedlecki.Pickeri.web.tabs.independentTabs;

import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
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
        HorizontalLayout root = new HorizontalLayout();
        VerticalLayout firstList = new VerticalLayout();
        VerticalLayout secondList = new VerticalLayout();
        addLink(firstList, "add.fruits.ui", "/");
        addLink(firstList, "statistics.and.employees.ui", "/statistics-and-info");
        addLink(firstList, "ranking.ui", "/ranking");
        addLink(firstList, "all.deliveries.ui", "/all-deliveries");
        addLink(firstList, "expenses.ui", "/expenses");
        addLink(firstList, "payments.ui", "/picker-payments");
        addLink(firstList, "other.ui", "/other");
        addLink(firstList, "logout.button", "/logout");
        firstList.setMargin(false);
        firstList.setStyleName("firstListMenu");
        HorizontalLayout logoLayout = ResourceGetter.getSiedleckiLogoWithLayout();
        root.setWidth(100, Unit.PERCENTAGE);
        root.addComponent(firstList);
        root.addComponent(secondList);
        secondList.addComponent(new Label("AAAAAAAAA"));
        root.setComponentAlignment(firstList, Alignment.TOP_LEFT);
        root.setComponentAlignment(secondList, Alignment.TOP_RIGHT);
        root.setMargin(false);
        this.addComponent(root);
        this.addComponent(logoLayout);
        this.setWidth(100, Unit.PERCENTAGE);
        this.setComponentAlignment(logoLayout, Alignment.MIDDLE_CENTER);

    }

    private void addLink(VerticalLayout layout, String propertyName, String path) {
        String name = env.getProperty(propertyName);
        Link link = new Link(name, new ExternalResource(path));
        layout.addComponent(link);
        layout.setComponentAlignment(link, Alignment.MIDDLE_CENTER);
    }

}
