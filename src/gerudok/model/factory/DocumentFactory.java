package gerudok.model.factory;

import gerudok.app.MyJFrame;
import gerudok.model.Document;
import gerudok.model.Page;
import gerudok.model.Project;

import javax.swing.*;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class DocumentFactory extends NodeFactory {

    @Override
    public MutableTreeNode createNode() {
        Document doc = new Document("Document");
        return doc;
    }
}
