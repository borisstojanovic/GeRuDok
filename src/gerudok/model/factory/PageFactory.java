package gerudok.model.factory;

import gerudok.model.Page;

import javax.swing.tree.MutableTreeNode;

public class PageFactory extends NodeFactory {

    @Override
    public MutableTreeNode createNode() {
        Page page = new Page("Page");
        return page;
    }
}
