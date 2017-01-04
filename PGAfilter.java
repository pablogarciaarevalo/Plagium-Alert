import java.io.*;

class PGAfilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".pga");
    }

    public String getDescription() {
        return "Archivos de AntiCopia,(*.PGA)";
    }
}
