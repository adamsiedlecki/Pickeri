package pl.adamsiedlecki.Pickeri.web.tab.independentTabs;

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
import pl.adamsiedlecki.Pickeri.service.SettingsService;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.components.StockInfoPanel;

@SpringComponent
@Scope("prototype")
public class MenuTab extends VerticalLayout {

    private final Environment env;
    private final Logger log = LoggerFactory.getLogger(MenuTab.class);
    private static String buttonStyle;

    @Autowired
    public MenuTab(Environment environment, NoteService noteService, StockInfoPanel stockInfoPanel, SettingsService settingsService) {
        this.env = environment;
        GridLayout rootLayout = new GridLayout(4, 8);
        rootLayout.setSpacing(true);
        rootLayout.setWidth(100, Unit.PERCENTAGE);

        switch (settingsService.get("menu.buttons.style").getState()) {
            case "LARGE":
            case "":
                buttonStyle = ValoTheme.BUTTON_LARGE;
                break;
            case "PRIMARY":
                buttonStyle = ValoTheme.BUTTON_PRIMARY;
                break;
            case "BORDERLESS":
                buttonStyle = ValoTheme.BUTTON_BORDERLESS;
                break;
            case "QUIET":
                buttonStyle = ValoTheme.BUTTON_QUIET;
                break;
        }

        addLink(rootLayout, "add.fruits.ui", "/", 1, 1);
        addLink(rootLayout, "statistics.ui", "/statistics-and-info", 1, 2);
        addLink(rootLayout, "ranking.ui", "/ranking", 1, 3);
        addLink(rootLayout, "all.deliveries.ui", "/all-deliveries", 1, 4);
        addLink(rootLayout, "expenses.ui", "/expenses", 1, 5);
        addLink(rootLayout, "payments.ui", "/picker-payments", 1, 6);
        addLink(rootLayout, "employees.ui", "/other", 1, 7);
        //firstList.setStyleName("firstListMenu");

        addLink(rootLayout, "notes.ui", "/notes", 2, 1);
        addLink(rootLayout, "worktime.registry", "/work-time", 2, 2);

        TextArea lastNote = new TextArea(env.getProperty("last.note"));
        lastNote.setEnabled(false);
        String lastNoteText = noteService.getLastNote().getContent();
        lastNote.setValue(lastNoteText==null?" " : lastNoteText);
        lastNote.setWidth(240, Unit.PIXELS);
        lastNote.setRows(2);
        rootLayout.addComponent(lastNote, 2, 3, 2, 4);

        rootLayout.addComponent(stockInfoPanel, 2, 5, 2, 6);
        stockInfoPanel.setWidth(240, Unit.PIXELS);

        addLink(rootLayout, "documents.title", "/documents-generator", 3, 1);
        addLink(rootLayout, "varieties.and.types.of.fruits", "/varieties-and-types", 3, 2);
        addLink(rootLayout, "devices.controller.title", "/devices-controller", 3, 3);
        addLink(rootLayout, "settings.title", "/settings", 3, 4);

        this.addComponent(rootLayout);
        addLink(this, "logout.button", "/logout", 0, 0);
        HorizontalLayout logoLayout = ResourceGetter.getSiedleckiLogoWithLayout();
        this.addComponent(logoLayout);
        this.setComponentAlignment(logoLayout, Alignment.BOTTOM_CENTER);
        this.setHeight(100, Unit.PERCENTAGE);
        this.setWidth(100, Unit.PERCENTAGE);
        this.setExpandRatio(rootLayout, 6);
        this.setExpandRatio(logoLayout, 1);
        this.setComponentAlignment(logoLayout, Alignment.BOTTOM_CENTER);

    }

    private void addLink(Layout layout, String propertyName, String path, int column, int row) {
        String name = env.getProperty(propertyName);
        Button link = new Button(name);
        link.setWidth(240, Unit.PIXELS);
        link.setStyleName(buttonStyle);

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
        addIconIfPropertyContains(propertyName,"devices",VaadinIcons.AUTOMATION, link);
        addIconIfPropertyContains(propertyName,"settings",VaadinIcons.ELLIPSIS_CIRCLE, link);
        
        link.addClickListener(e->{
            if(this.getUI().getPage()!=null){
                this.getUI().getPage().setLocation(path);
            }else{
                log.error("Page is null.");
            }

        });
        if (layout instanceof GridLayout) {
            GridLayout gridLayout = (GridLayout) layout;
            gridLayout.addComponent(link, column, row);
        } else if (layout instanceof VerticalLayout) {
            VerticalLayout verticalLayout = (VerticalLayout) layout;
            verticalLayout.addComponent(link);
            verticalLayout.setComponentAlignment(link, Alignment.TOP_CENTER);

            if (propertyName.contains("logout")) {
                link.setIcon(VaadinIcons.SIGN_OUT);
                verticalLayout.setComponentAlignment(link, Alignment.BOTTOM_CENTER);
                link.setWidth(20, Unit.PERCENTAGE);
                link.setStyleName(ValoTheme.BUTTON_PRIMARY);
            }
        }


    }

    private void addIconIfPropertyContains(String property, String text, Resource icon, Button link){
        if(property!=null && property.contains(text)){
            link.setIcon(icon);
        }
    }


}
