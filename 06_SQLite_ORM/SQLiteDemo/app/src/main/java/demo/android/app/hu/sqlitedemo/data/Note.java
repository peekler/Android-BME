package demo.android.app.hu.sqlitedemo.data;

/**
 * Created by Peter on 2014.10.13..
 */
public class Note {
    private long id;
    private String title;
    private String desc;

    // Parent class is Object!
    @Override
    public String toString() {
        return title+"\n"+desc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
