package pl.adamsiedlecki.Pickeri.tools.file;

import com.vaadin.ui.Layout;

public class FileDownloader {

    public static void action(Layout layout, String linkName, String downloadPath){
        layout.getUI().getPage().setLocation(downloadPath);
    }

}
