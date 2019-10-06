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
        VerticalLayout pageList = new VerticalLayout();
        VerticalLayout rightList = new VerticalLayout();
        pageList.addComponent(new Link(env.getProperty("add.fruits.ui"), new ExternalResource("/")));
        pageList.addComponent(new Link(env.getProperty("statistics.and.employees.ui"), new ExternalResource("/statistics-and-info")));
        pageList.addComponent(new Link(env.getProperty("ranking.ui"), new ExternalResource("/ranking")));
        pageList.addComponent(new Link(env.getProperty("all.deliveries.ui"), new ExternalResource("/all-deliveries")));
        pageList.addComponent(new Link(env.getProperty("expenses.ui"), new ExternalResource("/expenses")));
        pageList.addComponent(new Link(env.getProperty("payments.ui"), new ExternalResource("/picker-payments")));
        pageList.addComponent(new Link(env.getProperty("other.ui"), new ExternalResource("/other")));
        pageList.addComponent(new Link(env.getProperty("logout.button"), new ExternalResource("/logout")));
        pageList.setMargin(false);
        HorizontalLayout logoLayout = ResourceGetter.getSiedleckiLogoWithLayout();
        root.setWidth(100, Unit.PERCENTAGE);
        root.addComponent(pageList);
        root.addComponent(rightList);
        rightList.addComponent(new Label("AAAAAAAAA"));
        root.setComponentAlignment(pageList, Alignment.TOP_LEFT);
        root.setComponentAlignment(rightList, Alignment.TOP_RIGHT);
        root.setMargin(false);
        this.addComponent(root);
        this.addComponent(logoLayout);
        this.setWidth(100, Unit.PERCENTAGE);
        this.setComponentAlignment(logoLayout, Alignment.MIDDLE_CENTER);

    }

}
