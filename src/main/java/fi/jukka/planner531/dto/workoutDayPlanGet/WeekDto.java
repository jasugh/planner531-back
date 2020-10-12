package fi.jukka.planner531.dto.workoutDayPlanGet;

import java.util.ArrayList;
import java.util.List;

public class WeekDto {
    private int week;
    private List<DayDto> dayDtos = new ArrayList<>();

    public WeekDto() {
    }

    public WeekDto(int week, List<DayDto> dayDtos) {
        this.week = week;
        this.dayDtos = dayDtos;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public List<DayDto> getDayDtos() {
        return dayDtos;
    }

    public void setDayDtos(List<DayDto> dayDtos) {
        this.dayDtos = dayDtos;
    }
}
