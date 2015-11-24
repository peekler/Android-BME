package hu.ait.android.sugarormdemo.data;

import com.orm.SugarRecord;

import java.util.Date;

public class DayItem extends SugarRecord<DayItem> {

    private String title;
    private Date dueDate;

    public DayItem(){
    }

    public DayItem(String title, Date dueDate) {
        this.title = title;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
