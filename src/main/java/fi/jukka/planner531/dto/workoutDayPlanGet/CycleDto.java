package fi.jukka.planner531.dto.workoutDayPlanGet;

import java.util.ArrayList;
import java.util.List;

public class CycleDto {
    private int cycle;
    private List<WeekDto> weekDtos = new ArrayList<>();

    public CycleDto() {
    }

    public CycleDto(int cycle, List<WeekDto> weekDtos) {
        this.cycle = cycle;
        this.weekDtos = weekDtos;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public List<WeekDto> getWeekDtos() {
        return weekDtos;
    }

    public void setWeekDtos(List<WeekDto> weekDtos) {
        this.weekDtos = weekDtos;
    }
}
