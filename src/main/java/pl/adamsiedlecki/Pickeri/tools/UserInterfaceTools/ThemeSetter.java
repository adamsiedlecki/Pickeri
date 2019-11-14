package pl.adamsiedlecki.Pickeri.tools.UserInterfaceTools;

import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.service.SettingsService;

import javax.annotation.PostConstruct;

@Component
public class ThemeSetter {

    private static SettingsService settingsServiceStatic;
    private SettingsService settingsService;

    @Autowired
    public ThemeSetter(SettingsService settingsService){
        this.settingsService = settingsService;
    }

    @PostConstruct
    public void init(){
        settingsServiceStatic = settingsService;
    }

    public static void set(UI ui){
        String themeName = settingsServiceStatic.get("theme.name").getState();
        if(themeName==null||themeName.isEmpty()){
            themeName = "lightTheme";
            System.out.println("THEME IS NULL OR EMPTY");
        }
        ui.setTheme(themeName);
    }

}
