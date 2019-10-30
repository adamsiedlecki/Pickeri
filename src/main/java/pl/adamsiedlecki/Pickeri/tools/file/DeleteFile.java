package pl.adamsiedlecki.Pickeri.tools.file;

import java.io.File;

public class DeleteFile {

    public static void ifExists(File file){
        if (file.exists()) {
            file.delete();
        }
    }

}
