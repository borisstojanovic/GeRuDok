package gerudok.actions;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class WorkspaceFileFilter extends FileFilter {
    @Override
    public String getDescription() {
        return "GeRuDok Workspace Files (*.gwf)";
    }

    @Override
    public boolean accept(File f) {
        return (f.isDirectory() ||
                f.getName().toLowerCase().endsWith(".gwf"));
    }
}
