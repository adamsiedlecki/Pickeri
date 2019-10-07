package pl.adamsiedlecki.Pickeri.web.tabs.independentTabs;

import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.service.NoteService;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;

@SpringComponent
@Scope("prototype")
public class MenuTab extends VerticalLayout {

    private Environment env;

    @Autowired
    public MenuTab(Environment environment, NoteService noteService) {
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
        addLink(secondList, "notes.ui", "/notes");
        Label weatherWidget = new Label("<div id=\"openweathermap-widget-21\"></div>\n" +
                "<script src='//openweathermap.org/themes/openweathermap/assets/vendor/owm/js/d3.min.js'></script><script>window.myWidgetParam ? window.myWidgetParam : window.myWidgetParam = [];  window.myWidgetParam.push({id: 21,cityid: '771384',appid: '2bde50bdcc809a6230f17e9ba8e7f95',units: 'metric',containerid: 'openweathermap-widget-21',  });  (function() {var script = document.createElement('script');script.async = true;script.charset = \"utf-8\";script.src = \"//openweathermap.org/themes/openweathermap/assets/vendor/owm/js/weather-widget-generator.js\";var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(script, s);  })();</script>");
        weatherWidget.setContentMode(ContentMode.HTML);
        secondList.addComponent(weatherWidget);
        TextArea lastNote = new TextArea(env.getProperty("last.note"));
        lastNote.setValue(noteService.getLastNote().getContent());
        lastNote.setWidth(100, Unit.PERCENTAGE);
        secondList.addComponent(lastNote);
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
