package pl.adamsiedlecki.Pickeri.tools.userInterfaceTools;

import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

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
