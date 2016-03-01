package model;

/**
 * Created by Albert on 2016/2/25.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:地区片段model
 */
public class SectionModel {
    private int index;//索引值
    private int size;//片段包含的item数量
    private String letter;//该片段的headletter
    private int lastindex;//最后一个元素的索引

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getLastindex() {
        return lastindex;
    }

    public void setLastindex(int lastindex) {
        this.lastindex = lastindex;
    }
}
