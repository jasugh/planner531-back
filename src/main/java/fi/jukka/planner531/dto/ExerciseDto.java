package fi.jukka.planner531.dto;

public class ExerciseDto {
    private Long id;
    private String name;
    private int restTime;
    private float weightIncrement;
    private String notes;
    private Long categoryId;
    private String categoryName;

    public ExerciseDto() {
    }

    public ExerciseDto(
            Long id,
            String name,
            int restTime,
            float weightIncrement,
            String notes,
            Long categoryId,
            String categoryName) {
        this.id = id;
        this.name = name;
        this.restTime = restTime;
        this.weightIncrement = weightIncrement;
        this.notes = notes;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public float getWeightIncrement() {
        return weightIncrement;
    }

    public void setWeightIncrement(float weightIncrement) {
        this.weightIncrement = weightIncrement;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {  return categoryName; }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
