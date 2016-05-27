package dk.teamawesome.studdydokky;

/**
 * Created by Medes on 27/05/2016.
 */
public class ActivityData {
    public String title;
    public String startTime;
    public String endTime;
    public String imageUrl;
    public String description;
    public String category;
    public int id;
    public String[] tags;
    //Hvad gør vi med den mere specifikke position på Dokk1?



    public ActivityData(String titl, String sTime, String eTime, String iUrl, String descrip, String cat, int id_, String[] a){
        title = titl;
        startTime = sTime;
        endTime = eTime;
        imageUrl = iUrl;
        description = descrip;
        category = cat;
        id = id_;
        tags = a;
    }

    public String getTitle() {
        return title;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getId() { return id; }

    public String[] getTags() { return tags; }
}
