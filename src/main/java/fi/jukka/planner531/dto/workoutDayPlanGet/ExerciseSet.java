package fi.jukka.planner531.dto.workoutDayPlanGet;

public class ExerciseSet {
    private Long id;
    private float kgs;
    private int reps;
    private boolean finished;
    private String notes;

    public ExerciseSet() {
    }

    public ExerciseSet(Long id, float kgs, int reps, boolean finished, String notes) {
        this.id = id;
        this.kgs = kgs;
        this.reps = reps;
        this.finished = finished;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
