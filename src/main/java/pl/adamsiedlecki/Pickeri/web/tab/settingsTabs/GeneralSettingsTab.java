package pl.adamsiedlecki.Pickeri.web.tab.settingsTabs;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

@SpringComponent
@Scope("prototype")
public class GeneralSettingsTab extends VerticalLayout {

    private VerticalLayout root;
    private Environment env;

    @Autowired
    public GeneralSettingsTab(Environment env){
        this.env = env;
        root = new VerticalLayout();
        addRow(env.getProperty("menu.header.text"),env.getProperty("save.button"), e->{} );

        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.addComponent(root);
    }

    private void addRow(String labelValue, String buttonCaption, Button.ClickListener clickListener){
        HorizontalLayout row = new HorizontalLayout();
        row.setWidth(50, Unit.PERCENTAGE);
        row.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Button button = new Button(buttonCaption);
        TextField valueField = new TextField();
        Label label = new Label(labelValue);
        row.addComponents(label, valueField, button);
        button.addClickListener(clickListener);
        root.addComponent(row);
        root.setComponentAlignment(row, Alignment.MIDDLE_CENTER);
    }

}
