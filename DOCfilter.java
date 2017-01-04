import java.io.*;

class DOCfilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".doc");
    }
    
    public String getDescription() {
        return "Archivos de Word,(*.DOC)";
    }
}