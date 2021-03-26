package fi.jukka.planner531.controller;

import fi.jukka.planner531.dto.workoutDayPlanGet.*;
import fi.jukka.planner531.exception.NotFoundException;
import fi.jukka.planner531.model.*;
import fi.jukka.planner531.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routine")
public class WorkoutRoutineController {

    private static final ModelMapper modelMapper = new ModelMapper();

    private final LoginRepository loginRepository;
    private final WorkoutDayPlanRepository workoutDayPlanRepository;
    private final WorkoutDayRepository workoutDayRepository;
    private final WorkoutDayExerciseRepository workoutDayExerciseRepository;
    private final WorkoutDaySetRepository workoutDaySetRepository;

    @Autowired
    public WorkoutRoutineController(
            LoginRepository loginRepository,
            WorkoutDayPlanRepository workoutDayPlanRepository,
            WorkoutDayRepository workoutDayRepository,
            WorkoutDayExerciseRepository workoutDayExerciseRepository,
            WorkoutDaySetRepository workoutDaySetRepository) {
        this.loginRepository = loginRepository;
        this.workoutDayPlanRepository = workoutDayPlanRepository;
        this.workoutDayRepository = workoutDayRepository;
        this.workoutDayExerciseRepository = workoutDayExerciseRepository;
        this.workoutDaySetRepository = workoutDaySetRepository;
    }

    @GetMapping("/{exerciseId}/exercise")
    public ExerciseDayDto getRoutineExercise(@PathVariable Long exerciseId) {
        WorkoutDayExercise workoutDayExercise = workoutDayExerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new NotFoundException("No exercise data found with exercise id " + exerciseId));

        return convertWorkoutDayExerciseToDTO(workoutDayExercise);
    }

    private ExerciseDayDto convertWorkoutDayExerciseToDTO(WorkoutDayExercise workoutDayExercise) {
        return modelMapper.map(workoutDayExercise, ExerciseDayDto.class);
    }

}

