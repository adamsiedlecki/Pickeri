package pl.adamsiedlecki.Pickeri.web.ui;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.service.SettingsService;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.AlignmentSetter;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.HeaderAdder;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.ThemeSetter;
import pl.adamsiedlecki.Pickeri.web.tab.independentTabs.MenuTab;
import pl.adamsiedlecki.Pickeri.web.tab.workTimeTabs.AddTimeToPickerTab;
import pl.adamsiedlecki.Pickeri.web.tab.workTimeTabs.AllWorkTimesTab;
import pl.adamsiedlecki.Pickeri.web.tab.workTimeTabs.WorkTimeRanking;

@SpringUI(path = "/work-time")
@Title("${worktime.registry}")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
public class WorkTimeRegistryUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private final AddTimeToPickerTab addTimeToPickerTab;
    private final Environment env;
    private final AllWorkTimesTab allWorkTimesTab;
    private final MenuTab menuTab;
    private final WorkTimeRanking workTimeRanking;
    private final SettingsService settingsService;

    public WorkTimeRegistryUI(AddTimeToPickerTab addTimeToPickerTab, Environment env, AllWorkTimesTab allWorkTimesTab,
                              MenuTab menuTab, WorkTimeRanking workTimeRanking, SettingsService settingsService) {
        this.workTimeRanking = workTimeRanking;
        this.env = env;
        this.addTimeToPickerTab = addTimeToPickerTab;
        this.allWorkTimesTab = allWorkTimesTab;
        this.menuTab = menuTab;
        this.settingsService = settingsService;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        HeaderAdder.add(root, settingsService);
        addTabs();
        AlignmentSetter.apply(root, tabs);
        this.setContent(root);
        ThemeSetter.set(this);
    }

    private void addTabs(){
        tabs = new TabSheet();
        tabs.addTab(addTimeToPickerTab).setCaption(env.getProperty("add.work.time"));
        tabs.addTab(allWorkTimesTab).setCaption(env.getProperty("all.work.times"));
        tabs.addTab(workTimeRanking).setCaption(env.getProperty("work.time.ranking"));
        tabs.addTab(menuTab, env.getProperty("menu.tab.caption"));
        root.addComponent(tabs);
    }
}
