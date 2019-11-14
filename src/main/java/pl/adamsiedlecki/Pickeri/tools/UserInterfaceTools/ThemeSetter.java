package pl.adamsiedlecki.Pickeri.tools.UserInterfaceTools;

import com.vaadin.ui.UI;

public class ThemeSetter {

    public static void set(UI ui){
        String themeName = "mytheme";
        ui.setTheme(themeName);
    }

}
