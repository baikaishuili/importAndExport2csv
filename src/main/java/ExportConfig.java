import java.util.List;

/**
 * Created by cdliwenke on 2017/8/5.
 */
public class ExportConfig {
    /**�����ļ���*/
    String fileName = "temp";
    /**title*/
    List<String> tileNameList;
    /**Ҫ�������ֶ�*/
    List<String> FieldName;
    Integer from = 0;
    Integer size;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getTileNameList() {
        return tileNameList;
    }

    public void setTileNameList(List<String> tileNameList) {
        this.tileNameList = tileNameList;
    }

    public List<String> getFieldName() {
        return FieldName;
    }

    public void setFieldName(List<String> fieldName) {
        FieldName = fieldName;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
