package gerudok.model.factory;

import gerudok.model.Document;
import gerudok.model.Project;
import gerudok.model.workspace.Workspace;

public abstract class FactoryProducer {
    public static NodeFactory returnFactory(Object obj){
        if(obj instanceof Workspace){
            return new ProjectFactory();
        }
        else if(obj instanceof Project){
            return new DocumentFactory();
        }
        else if(obj instanceof Document){
            return new PageFactory();
        }
        return null;
    }
}
