package image;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import javax.swing.filechooser.FileFilter;

public class OpenFileFilter extends FileFilter {

    String description = "";
    String fileExt = "";

    public OpenFileFilter(String extension, String typeDescription) {
        fileExt = extension;
        description = typeDescription;
    }

    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        return (f.getName().toLowerCase().endsWith(fileExt));
    }

    public String getDescription() {
        return description;
    }
}
