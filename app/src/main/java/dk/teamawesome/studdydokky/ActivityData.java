package dk.teamawesome.studdydokky;

import android.graphics.Bitmap;

/**
 * Created by Medes on 27/05/2016.
 */
public class ActivityData {
    public String title;
    public String startTime;
    public String endTime;
    public String image;
    public String description;
    public String category;
    public String id;
    public String[] tags;
    //Hvad gør vi med den mere specifikke position på Dokk1?



    public ActivityData(String titl, String sTime, String eTime, String img, String descrip, String cat, String id_, String[] a){
        title = titl;
        startTime = sTime;
        endTime = eTime;
        image = img;
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

    public String getImageUrl() {return image;}

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getId() { return id; }

    public String[] getTags() { return tags; }
}
