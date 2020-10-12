package fi.jukka.planner531.enums;

public enum Exercises531 {
    e1("Overhead Press"),
    e2("Dead Lift"),
    e3("Bench Press"),
    e4("Squat"),
    ;

    private final String exerciseName;

    Exercises531(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseName() {
        return exerciseName;
    }
}
