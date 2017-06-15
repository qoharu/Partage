package id.innovable.partage;

/**
 * Created by Qoharu on 2/24/17.
 */

public class Content {
    private String content;
    private String uid;
    private String timestamp;
    private String loc_lat;
    private String loc_long;

    public Content() {}
    public Content(String content) {
        this.content = content;
    }
    public Content(String content, String uid, String timestamp) {
        this.content = content;
        this.uid = uid;
        this.timestamp = timestamp;
    }
    public Content(String content, String uid, String lat, String lon) {
        this.content = content;
        this.uid = uid;
        this.loc_lat = lat;
        this.loc_long = lon;
    }

    public String getContent() {
        return content;
    }
    public String getLoc_lat() {return loc_lat;}
    public String getLoc_long() {return loc_long;}
    public String getUid() {return uid;}
    public String getTimestamp() {return timestamp;}

    public void setTimestamp(String timestamp) {this.timestamp = timestamp;}
}
