package pl.adamsiedlecki.Pickeri.web.ui;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.service.SettingsService;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.AlignmentSetter;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.HeaderAdder;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.ThemeSetter;
import pl.adamsiedlecki.Pickeri.web.tab.independentTabs.MenuTab;
import pl.adamsiedlecki.Pickeri.web.tab.noteTabs.AddNoteTab;
import pl.adamsiedlecki.Pickeri.web.tab.noteTabs.AllNotesTab;
import pl.adamsiedlecki.Pickeri.web.tab.noteTabs.DeleteNoteTab;

@SpringUI(path = "/notes")
@Scope("prototype")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${notes.title}")
public class NotesUI extends UI {

    private TabSheet tabs;
    private Environment env;
    private VerticalLayout root;
    private SettingsService settingsService;

    @Autowired
    public NotesUI(Environment environment, AddNoteTab addNoteTab, MenuTab menuTab, AllNotesTab allNotesTab, DeleteNoteTab deleteNoteTab,
                   SettingsService settingsService) {
        this.env = environment;
        this.settingsService = settingsService;
        tabs = new TabSheet();
        tabs.addTab(addNoteTab).setCaption(environment.getProperty("add.note.tab"));
        tabs.addTab(allNotesTab, env.getProperty("all.notes.tab"));
        tabs.addTab(deleteNoteTab, env.getProperty("delete.note.tab"));
        tabs.addTab(menuTab).setCaption(environment.getProperty("menu.tab.caption"));
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        HeaderAdder.add(root, settingsService);
        root.addComponent(tabs);
        AlignmentSetter.apply(root, tabs);
        this.setContent(root);
        ThemeSetter.set(this);
    }

}
