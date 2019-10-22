package pl.adamsiedlecki.Pickeri.tools;

import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class AlignmentSetter {

    public static void apply(VerticalLayout root, Embedded pickeriLogo, TabSheet tabs){
        root.setSizeFull();
        root.setMargin(new MarginInfo(false, true, false, true));
        root.setExpandRatio(pickeriLogo, 1);
        root.setExpandRatio(tabs,7);
        tabs.setHeight(100, Sizeable.Unit.PERCENTAGE);
    }

}
