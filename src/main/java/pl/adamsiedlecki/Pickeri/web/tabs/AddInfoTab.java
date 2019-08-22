package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class AddInfoTab extends VerticalLayout {

    public AddInfoTab(){
        addContent();
    }

    private void addContent(){
        this.addComponent(new Label("LABELLLLL"));
    }

}
