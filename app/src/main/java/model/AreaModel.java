package model;

import java.util.List;

/**
 * Created by Albert on 2015/11/17.
 * Mail : lbh@jusfoun.com
 * TODO :区域model
 */
public class AreaModel {

    public final static String NORMAL = "normal";
    public final static String HOT = "hot";

    private String id,pid,name;
    private List<AreaModel> children;
    private boolean choosed;
    private String sectionletter;
    private String type;
    private String pinyin;
    private int index;//搜索地区时，记录关键词在命中地区拼音中的索引


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaModel> getChildren() {
        return children;
    }

    public void setChildren(List<AreaModel> children) {
        this.children = children;
    }

    public String getSectionletter() {
        return sectionletter;
    }

    public void setSectionletter(String sectionletter) {
        this.sectionletter = sectionletter;
    }

    public boolean isChoosed() {
        return choosed;
    }

    public void setChoosed(boolean choosed) {
        this.choosed = choosed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
