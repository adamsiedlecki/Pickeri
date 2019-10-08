package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.MenuTab;
import pl.adamsiedlecki.Pickeri.web.tabs.noteTabs.AddNoteTab;
import pl.adamsiedlecki.Pickeri.web.tabs.noteTabs.AllNotesTab;
import pl.adamsiedlecki.Pickeri.web.tabs.noteTabs.DeleteNoteTab;

@SpringUI(path = "/notes")
@Scope("prototype")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${notes.title}")
public class NotesUI extends UI {

    private TabSheet tabs;
    private Environment env;
    private VerticalLayout root;

    @Autowired
    public NotesUI(Environment environment, AddNoteTab addNoteTab, MenuTab menuTab, AllNotesTab allNotesTab, DeleteNoteTab deleteNoteTab) {
        this.env = environment;
        tabs = new TabSheet();
        tabs.addTab(addNoteTab).setCaption(environment.getProperty("add.note.tab"));
        tabs.addTab(allNotesTab, env.getProperty("all.notes.tab"));
        tabs.addTab(deleteNoteTab, env.getProperty("delete.note.tab"));
        tabs.addTab(menuTab).setCaption(environment.getProperty("menu.tab.caption"));
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        root.addComponent(ResourceGetter.getPickeriLogoAsEmbeddedComponent());
        root.setMargin(new MarginInfo(false, true, true, true));
        root.addComponent(tabs);
        this.setContent(root);
    }

}
