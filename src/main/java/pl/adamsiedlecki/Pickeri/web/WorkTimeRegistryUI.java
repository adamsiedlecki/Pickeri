package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.workTimeTabs.AddTimeToPickerTab;

@SpringUI
@Title("${worktime.registry}")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
public class WorkTimeRegistryUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private AddTimeToPickerTab addTimeToPickerTab;

    public WorkTimeRegistryUI(AddTimeToPickerTab addTimeToPickerTab){
        this.addTimeToPickerTab = addTimeToPickerTab;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        tabs = new TabSheet();
        tabs.addTab(addTimeToPickerTab);
        root.addComponent(ResourceGetter.getPickeriLogoAsEmbeddedComponent());
        root.addComponent(tabs);

        this.setContent(root);
    }
}
