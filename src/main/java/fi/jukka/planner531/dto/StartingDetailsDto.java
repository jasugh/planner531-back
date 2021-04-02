package fi.jukka.planner531.dto;

import javax.persistence.ElementCollection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class
StartingDetailsDto {
    private Long id;
    private LocalDate startingDate;
    private float pressKg;
    private int pressReps;
    private float press1rm;
    private float deadLiftKg;
    private int deadLiftReps;
    private float deadLift1rm;
    private float benchPressKg;
    private int benchPressReps;
    private float benchPress1rm;
    private float squatKg;
    private int squatReps;
    private float squat1rm;
    private float weightRounding;
    private float pressIncrement;
    private float deadLiftIncrement;
    private float benchIncrement;
    private float squatIncrement;
    private float trainingMax;
    private float numberOfCycles;
    @ElementCollection
    private List<Integer> w1percentages = new ArrayList<Integer>();
    @ElementCollection
    private List<Integer> w2percentages = new ArrayList<Integer>();
    @ElementCollection
    private List<Integer> w3percentages = new ArrayList<Integer>();
    @ElementCollection
    private List<Integer> w4percentages = new ArrayList<Integer>();
    private Long loginId;

    public StartingDetailsDto() {
    }

    public StartingDetailsDto(LocalDate startingDate,
                              float pressKg,
                              int pressReps,
                              float press1rm,
                              float deadLiftKg,
                              int deadLiftReps,
                              float deadLift1rm,
                              float benchPressKg,
                              int benchPressReps,
                              float benchPress1rm,
                              float squatKg,
                              int squatReps,
                              float squat1rm,
                              float weightRounding,
                              float pressIncrement,
                              float deadLiftIncrement,
                              float benchIncrement,
                              float squatIncrement,
                              float trainingMax,
                              float numberOfCycles,
                              List<Integer> w1percentages,
                              List<Integer> w2percentages,
                              List<Integer> w3percentages,
                              List<Integer> w4percentages,
                              Long loginId) {
        this.startingDate = startingDate;
        this.pressKg = pressKg;
        this.pressReps = pressReps;
        this.press1rm = press1rm;
        this.deadLiftKg = deadLiftKg;
        this.deadLiftReps = deadLiftReps;
        this.deadLift1rm = deadLift1rm;
        this.benchPressKg = benchPressKg;
        this.benchPressReps = benchPressReps;
        this.benchPress1rm = benchPress1rm;
        this.squatKg = squatKg;
        this.squatReps = squatReps;
        this.squat1rm = squat1rm;
        this.weightRounding = weightRounding;
        this.pressIncrement = pressIncrement;
        this.deadLiftIncrement = deadLiftIncrement;
        this.benchIncrement = benchIncrement;
        this.squatIncrement = squatIncrement;
        this.trainingMax = trainingMax;
        this.numberOfCycles = numberOfCycles;
        this.w1percentages = w1percentages;
        this.w2percentages = w2percentages;
        this.w3percentages = w3percentages;
        this.w4percentages = w4percentages;
        this.loginId = loginId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public float getPressKg() {
        return pressKg;
    }

    public void setPressKg(float pressKg) {
        this.pressKg = pressKg;
    }

    public int getPressReps() {
        return pressReps;
    }

    public void setPressReps(int pressReps) {
        this.pressReps = pressReps;
    }

    public float getPress1rm() {
        return press1rm;
    }

    public void setPress1rm(float press1rm) {
        this.press1rm = press1rm;
    }

    public float getDeadLiftKg() {
        return deadLiftKg;
    }

    public void setDeadLiftKg(float deadLiftKg) {
        this.deadLiftKg = deadLiftKg;
    }

    public int getDeadLiftReps() {
        return deadLiftReps;
    }

    public void setDeadLiftReps(int deadLiftReps) {
        this.deadLiftReps = deadLiftReps;
    }

    public float getDeadLift1rm() {
        return deadLift1rm;
    }

    public void setDeadLift1rm(float deadLift1rm) {
        this.deadLift1rm = deadLift1rm;
    }

    public float getBenchPressKg() {
        return benchPressKg;
    }

    public void setBenchPressKg(float benchPressKg) {
        this.benchPressKg = benchPressKg;
    }

    public int getBenchPressReps() {
        return benchPressReps;
    }

    public void setBenchPressReps(int benchPressReps) {
        this.benchPressReps = benchPressReps;
    }

    public float getBenchPress1rm() {
        return benchPress1rm;
    }

    public void setBenchPress1rm(float benchPress1rm) {
        this.benchPress1rm = benchPress1rm;
    }

    public float getSquatKg() {
        return squatKg;
    }

    public void setSquatKg(float squatKg) {
        this.squatKg = squatKg;
    }

    public int getSquatReps() {
        return squatReps;
    }

    public void setSquatReps(int squatReps) {
        this.squatReps = squatReps;
    }

    public float getSquat1rm() {
        return squat1rm;
    }

    public void setSquat1rm(float squat1rm) {
        this.squat1rm = squat1rm;
    }

    public float getWeightRounding() {
        return weightRounding;
    }

    public void setWeightRounding(float weightRounding) {
        this.weightRounding = weightRounding;
    }

    public float getPressIncrement() {
        return pressIncrement;
    }

    public void setPressIncrement(float pressIncrement) {
        this.pressIncrement = pressIncrement;
    }

    public float getDeadLiftIncrement() {
        return deadLiftIncrement;
    }

    public void setDeadLiftIncrement(float deadLiftIncrement) {
        this.deadLiftIncrement = deadLiftIncrement;
    }

    public float getBenchIncrement() {
        return benchIncrement;
    }

    public void setBenchIncrement(float benchIncrement) {
        this.benchIncrement = benchIncrement;
    }

    public float getSquatIncrement() {
        return squatIncrement;
    }

    public void setSquatIncrement(float squatIncrement) {
        this.squatIncrement = squatIncrement;
    }

    public float getTrainingMax() {
        return trainingMax;
    }

    public void setTrainingMax(float trainingMax) {
        this.trainingMax = trainingMax;
    }

    public float getNumberOfCycles() {
        return numberOfCycles;
    }

    public void setNumberOfCycles(float numberOfCycles) {
        this.numberOfCycles = numberOfCycles;
    }

    public List<Integer> getW1percentages() {
        return w1percentages;
    }

    public void setW1percentages(List<Integer> w1percentages) {
        this.w1percentages = w1percentages;
    }

    public List<Integer> getW2percentages() {
        return w2percentages;
    }

    public void setW2percentages(List<Integer> w2percentages) {
        this.w2percentages = w2percentages;
    }

    public List<Integer> getW3percentages() {
        return w3percentages;
    }

    public void setW3percentages(List<Integer> w3percentages) {
        this.w3percentages = w3percentages;
    }

    public List<Integer> getW4percentages() {
        return w4percentages;
    }

    public void setW4percentages(List<Integer> w4percentages) {
        this.w4percentages = w4percentages;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }
}
