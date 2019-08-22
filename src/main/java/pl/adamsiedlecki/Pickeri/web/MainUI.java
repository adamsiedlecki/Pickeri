package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import pl.adamsiedlecki.Pickeri.web.tabs.AddInfoTab;

@SpringUI
public class MainUI extends UI {

    //private TabSheet tabs;

    @Override
    protected void init(VaadinRequest request) {

        //addTabs();

        //this.setContent(tabs);

        VerticalLayout v = new VerticalLayout();
        v.addComponent(new Label("!!!!!!!"));
        setContent(v);
    }

//    private void addTabs(){
//        tabs = new TabSheet();
//
//        AddInfoTab addInfoTab = new AddInfoTab();
//        tabs.addComponent(addInfoTab);
//
//
//    }

}
