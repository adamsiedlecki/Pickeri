package pl.adamsiedlecki.Pickeri.tools;

import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamsiedlecki.Pickeri.service.SettingsService;

public class AlignmentSetter {

    public static void apply(VerticalLayout root, TabSheet tabs){
        root.setSizeFull();
        root.setMargin(new MarginInfo(false, true, false, true));
        root.setExpandRatio(tabs,7);
        tabs.setHeight(100, Sizeable.Unit.PERCENTAGE);
    }

    public static void apply(VerticalLayout root, Embedded embedded, TabSheet tabs){
        root.setSizeFull();
        root.setMargin(new MarginInfo(false, true, false, true));
        root.setExpandRatio(tabs,7);
        tabs.setHeight(100, Sizeable.Unit.PERCENTAGE);
    }

}
