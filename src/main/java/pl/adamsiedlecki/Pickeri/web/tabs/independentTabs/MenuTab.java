package pl.adamsiedlecki.Pickeri.web.tabs.independentTabs;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.service.NoteService;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.components.StockInfoPanel;

@SpringComponent
@Scope("prototype")
public class MenuTab extends VerticalLayout {

    private Environment env;
    private Logger log = LoggerFactory.getLogger(MenuTab.class);

    @Autowired
    public MenuTab(Environment environment, NoteService noteService, StockInfoPanel stockInfoPanel) {
        this.env = environment;
        HorizontalLayout root = new HorizontalLayout();
        VerticalLayout firstList = new VerticalLayout();
        VerticalLayout secondList = new VerticalLayout();
        VerticalLayout thirdList = new VerticalLayout();

        addLink(firstList, "add.fruits.ui", "/");
        addLink(firstList, "statistics.and.employees.ui", "/statistics-and-info");
        addLink(firstList, "ranking.ui", "/ranking");
        addLink(firstList, "all.deliveries.ui", "/all-deliveries");
        addLink(firstList, "expenses.ui", "/expenses");
        addLink(firstList, "payments.ui", "/picker-payments");
        addLink(firstList, "employees.ui", "/other");
        //firstList.setStyleName("firstListMenu");

        addLink(secondList, "notes.ui", "/notes");
        addLink(secondList, "worktime.registry", "/work-time");
        TextArea lastNote = new TextArea(env.getProperty("last.note"));
        lastNote.setEnabled(false);
        lastNote.setValue(noteService.getLastNote().getContent());
        lastNote.setWidth(100, Unit.PERCENTAGE);
        lastNote.setRows(2);
        secondList.addComponent(lastNote);
        secondList.addComponent(stockInfoPanel);

        addLink(thirdList, "documents.title", "/documents-generator");
        addLink(thirdList,"varieties.and.types.of.fruits","/varieties-and-types");

        root.setWidth(100, Unit.PERCENTAGE);
        root.addComponents(firstList, secondList, thirdList);
        firstList.setHeight(350, Unit.PIXELS);
        secondList.setHeight(300, Unit.PIXELS);
        thirdList.setHeight(350, Unit.PIXELS);
        root.setComponentAlignment(firstList, Alignment.TOP_LEFT);
        root.setComponentAlignment(secondList, Alignment.TOP_CENTER);
        root.setComponentAlignment(thirdList, Alignment.TOP_RIGHT);
        root.setMargin(false);
        root.setHeight(100, Unit.PERCENTAGE);
        this.addComponent(root);
        addLink(this, "logout.button", "/logout");
        HorizontalLayout logoLayout = ResourceGetter.getSiedleckiLogoWithLayout();
        this.addComponent(logoLayout);
        this.setComponentAlignment(logoLayout, Alignment.BOTTOM_CENTER);
        this.setHeight(100, Unit.PERCENTAGE);
        this.setWidth(100, Unit.PERCENTAGE);
        this.setExpandRatio(root,6);
        this.setExpandRatio(logoLayout, 1);
        this.setComponentAlignment(logoLayout, Alignment.BOTTOM_CENTER);

    }

    private void addLink(VerticalLayout layout, String propertyName, String path) {
        String name = env.getProperty(propertyName);
        Button link = new Button(name);
        link.setWidth(240, Unit.PIXELS);
        link.addStyleNames(ValoTheme.BUTTON_LARGE);

        addIconIfPropertyContains(propertyName,"ranking",VaadinIcons.TABLE, link);
        addIconIfPropertyContains(propertyName,"add.fruits",VaadinIcons.PLUS_CIRCLE, link);
        addIconIfPropertyContains(propertyName,"all.deliveries",VaadinIcons.TABLE, link);
        addIconIfPropertyContains(propertyName,"expenses",VaadinIcons.BOOK_DOLLAR, link);
        addIconIfPropertyContains(propertyName,"payments",VaadinIcons.BOOK_DOLLAR, link);
        addIconIfPropertyContains(propertyName,"other",VaadinIcons.ARCHIVES, link);
        addIconIfPropertyContains(propertyName,"notes",VaadinIcons.NOTEBOOK, link);
        addIconIfPropertyContains(propertyName,"time",VaadinIcons.TIMER, link);
        addIconIfPropertyContains(propertyName,"document",VaadinIcons.FILE_TABLE, link);
        addIconIfPropertyContains(propertyName,"varieties",VaadinIcons.CLUSTER, link);
        addIconIfPropertyContains(propertyName,"employees",VaadinIcons.GROUP, link);
        addIconIfPropertyContains(propertyName,"stat",VaadinIcons.TRENDING_UP, link);

        link.addClickListener(e->{
            if(this.getUI().getPage()!=null){
                this.getUI().getPage().setLocation(path);
            }else{
                log.error("Page is null.");
            }

        });
        layout.addComponent(link);
        layout.setComponentAlignment(link, Alignment.TOP_CENTER);

        if(propertyName!=null && propertyName.contains("logout")){
            link.setIcon(VaadinIcons.ARROW_CIRCLE_DOWN);
            layout.setComponentAlignment(link, Alignment.BOTTOM_CENTER);
            link.setWidth(20, Unit.PERCENTAGE);
            link.setStyleName(ValoTheme.BUTTON_PRIMARY);
        }
    }

    private void addIconIfPropertyContains(String property, String text, Resource icon, Button link){
        if(property!=null && property.contains(text)){
            link.setIcon(icon);
        }
    }


}
