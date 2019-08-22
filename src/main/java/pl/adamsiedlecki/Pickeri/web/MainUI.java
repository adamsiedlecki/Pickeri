package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import pl.adamsiedlecki.Pickeri.web.tabs.AddInfoTab;

import java.io.File;

@SpringUI
public class MainUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();

        root.addComponent(new Embedded("", new FileResource(new File("src\\main\\resources\\images\\pickeri.png"))));
        addTabs();

        this.setContent(root);
    }

    private void addTabs(){
        tabs = new TabSheet();

        AddInfoTab addInfoTab = new AddInfoTab();
        tabs.addTab(addInfoTab,"Dodaj ");
        root.addComponent(tabs);
    }

}
