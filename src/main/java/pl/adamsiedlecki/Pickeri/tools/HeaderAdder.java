package pl.adamsiedlecki.Pickeri.tools;

import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import pl.adamsiedlecki.Pickeri.service.SettingsService;

public class HeaderAdder {

    public static void add(VerticalLayout root, SettingsService settingsService){
        Embedded pickeriLogo = ResourceGetter.getPickeriLogoAsEmbeddedComponent();
        Label nameLabel = new Label(settingsService.get("menu.header.text").getState());
        HorizontalLayout horizontalLayout = new HorizontalLayout(pickeriLogo, nameLabel);
        horizontalLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
        horizontalLayout.setComponentAlignment(nameLabel, Alignment.MIDDLE_CENTER);
        nameLabel.setStyleName(ValoTheme.LABEL_H1);
        horizontalLayout.setExpandRatio(pickeriLogo, 1);
        root.addComponent(horizontalLayout);
        root.setMargin(new MarginInfo(false, true, true, true));
    }

}
