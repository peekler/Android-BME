package examples.amorg.aut.bme.hu.listfragmentdemo;

/**
 * Created by Peter on 2014.09.30..
 */
public class Note {

    private String title;
    private String date;

    public Note(String aTitle, String aDate) {
        title = aTitle;
        date = aDate;
    }

    public String getDate() {
        return date;
    }
    public String getTitle() {
        return title;
    }

}
