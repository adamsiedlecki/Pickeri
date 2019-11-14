package pl.adamsiedlecki.Pickeri.web.tab.settingsTabs;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.service.SettingsService;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.ThemeSetter;

import java.util.List;

@SpringComponent
@Scope("prototype")
public class GeneralSettingsTab extends VerticalLayout {

    private VerticalLayout root;
    private Environment env;
    private SettingsService settingsService;

    @Autowired
    public GeneralSettingsTab(Environment env, SettingsService settingsService){
        this.env = env;
        this.settingsService = settingsService;
        root = new VerticalLayout();
        addRow(env.getProperty("header.text"),env.getProperty("save.button"), "header.text");
        addRowWithComboBox(env.getProperty("menu.buttons.style"), env.getProperty("save.button"), "menu.buttons.style",
                List.of("LARGE","BORDERLESS", "QUIET", "PRIMARY"));
        addRowWithComboBox(env.getProperty("theme"), env.getProperty("save.button"), "theme.name",
                List.of("lightTheme","darkTheme"));

        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.addComponent(root);
    }

    private void addRow(String labelValue, String buttonCaption, String key){
        HorizontalLayout row = new HorizontalLayout();
        row.setWidth(50, Unit.PERCENTAGE);
        row.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Button button = new Button(buttonCaption);
        TextField valueField = new TextField();
        valueField.setValue(settingsService.get(key).getState());
        valueField.setWidth(250, Unit.PIXELS);
        Label label = new Label(labelValue);
        row.addComponents(label, valueField, button);
        button.addClickListener(e->{
            settingsService.update(key, valueField.getValue());
        });
        root.addComponent(row);
        root.setComponentAlignment(row, Alignment.MIDDLE_CENTER);
    }

    private void addRowWithComboBox(String labelValue, String buttonCaption, String key, List<String> values){
        HorizontalLayout row = new HorizontalLayout();
        row.setWidth(50, Unit.PERCENTAGE);
        row.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Button button = new Button(buttonCaption);
        ComboBox<String> valueField = new ComboBox<>();
        valueField.setEmptySelectionAllowed(false);
        valueField.setItems(values);
        valueField.setValue(settingsService.get(key).getState());
        valueField.setWidth(250, Unit.PIXELS);
        Label label = new Label(labelValue);
        row.addComponents(label, valueField, button);
        button.addClickListener(e->{
            settingsService.update(key, valueField.getValue());
            if(key.equals("theme.name")){
                ThemeSetter.set(this.getUI());
            }
        });
        root.addComponent(row);
        root.setComponentAlignment(row, Alignment.MIDDLE_CENTER);
    }

}
