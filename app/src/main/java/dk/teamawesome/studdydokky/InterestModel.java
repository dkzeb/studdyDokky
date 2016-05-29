package dk.teamawesome.studdydokky;

public class InterestModel implements Comparable{

    private String name;
    private boolean checked;

    public InterestModel(String name){
        this.name = name;
        this.checked = false;
    }

    public String getName(){
        return name;
    }

    public boolean getChecked(){
        return checked;
    }

    public void setChecked(boolean checked){
        this.checked = checked;
    }

    @Override
    public int compareTo(Object m) {
        return this.name.compareTo(((InterestModel)m).getName());
    }
}
