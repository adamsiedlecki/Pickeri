package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.tools.AlignmentSetter;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.MenuTab;
import pl.adamsiedlecki.Pickeri.web.tabs.workTimeTabs.AddTimeToPickerTab;
import pl.adamsiedlecki.Pickeri.web.tabs.workTimeTabs.AllWorkTimesTab;
import pl.adamsiedlecki.Pickeri.web.tabs.workTimeTabs.WorkTimeRanking;

@SpringUI(path="/work-time")
@Title("${worktime.registry}")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
public class WorkTimeRegistryUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private AddTimeToPickerTab addTimeToPickerTab;
    private Environment env;
    private AllWorkTimesTab allWorkTimesTab;
    private MenuTab menuTab;
    private WorkTimeRanking workTimeRanking;

    public WorkTimeRegistryUI(AddTimeToPickerTab addTimeToPickerTab, Environment env, AllWorkTimesTab allWorkTimesTab,
                              MenuTab menuTab, WorkTimeRanking workTimeRanking){
        this.workTimeRanking = workTimeRanking;
        this.env = env;
        this.addTimeToPickerTab = addTimeToPickerTab;
        this.allWorkTimesTab = allWorkTimesTab;
        this.menuTab = menuTab;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        Embedded pickeriLogo = ResourceGetter.getPickeriLogoAsEmbeddedComponent();
        root.addComponent(pickeriLogo);
        tabs = new TabSheet();
        tabs.addTab(addTimeToPickerTab).setCaption(env.getProperty("add.work.time"));
        tabs.addTab(allWorkTimesTab).setCaption(env.getProperty("all.work.times"));
        tabs.addTab(workTimeRanking).setCaption(env.getProperty("work.time.ranking"));
        tabs.addTab(menuTab, env.getProperty("menu.tab.caption"));
        root.addComponent(tabs);

        AlignmentSetter.apply(root, pickeriLogo, tabs);

        this.setContent(root);
    }
}
