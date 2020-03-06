package gerudok.model.elements;


import java.io.*;
import java.util.ArrayList;

public class Content implements Serializable {

    String filePath;
    ContentTypeEnum contentTypeEnum;

    public Content(){

    }

    public Content(Content content){
        this.contentTypeEnum = content.getContentTypeEnum();

        if(contentTypeEnum == ContentTypeEnum.TEXT) {
            try {
                File file = File.createTempFile("cont", ".rtf");
                File readFile = new File(content.getFilePath());
                ObjectInputStream is = new ObjectInputStream(new FileInputStream(readFile));
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(is.readObject());
                filePath = file.getPath();
            } catch (IOException | ClassNotFoundException e) {

            }
        }else{
            filePath = content.getFilePath();
        }
    }

    public Content clone(){
        return new Content(this);
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setContentTypeEnum(ContentTypeEnum contentTypeEnum) {
        this.contentTypeEnum = contentTypeEnum;
    }

    public ContentTypeEnum getContentTypeEnum() {
        return contentTypeEnum;
    }
}
