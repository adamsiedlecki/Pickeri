package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import org.springframework.context.annotation.Scope;

@SpringUI(path = "/notes")
@Scope("prototype")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${notes.title}")
public class NotesUI extends UI {

    private TabSheet tabs;

    public NotesUI() {

    }

    @Override
    protected void init(VaadinRequest request) {

    }

}
