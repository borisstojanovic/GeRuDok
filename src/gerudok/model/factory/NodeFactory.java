package gerudok.model.factory;

import javax.swing.tree.MutableTreeNode;

public abstract class NodeFactory {

    public MutableTreeNode deliverNode(){
        MutableTreeNode node;
        node = createNode();
        return node;
    }

    public abstract MutableTreeNode createNode();
}
