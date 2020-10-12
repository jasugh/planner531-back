package fi.jukka.planner531.dto.workoutDayPlanGet;

public class SetDto {
    private float kgs;
    private int reps;
    private boolean finished;
    private String notes;
    private String typeOfSet;

    public SetDto() {
    }

    public SetDto(float kgs, int reps, boolean finished, String notes, String typeOfSet) {
        this.kgs = kgs;
        this.reps = reps;
        this.finished = finished;
        this.notes = notes;
        this.typeOfSet = typeOfSet;
    }

    public float getKgs() {
        return kgs;
    }

    public void setKgs(float kgs) {
        this.kgs = kgs;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTypeOfSet() {
        return typeOfSet;
    }

    public void setTypeOfSet(String typeOfSet) {
        this.typeOfSet = typeOfSet;
    }
}
