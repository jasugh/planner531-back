package fi.jukka.planner531.controller;

import fi.jukka.planner531.dto.MainExerciseDto;
import fi.jukka.planner531.dto.WorkoutDto;
import fi.jukka.planner531.dto.workoutDayPlanGet.*;
import fi.jukka.planner531.exception.NotFoundException;
import fi.jukka.planner531.model.*;
import fi.jukka.planner531.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ExerciseDay getRoutineExercise(@PathVariable Long exerciseId) {
        WorkoutDayExercise workoutDayExercise = workoutDayExerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new NotFoundException("No exercise data found with exercise id " + exerciseId));

        return convertWorkoutDayExerciseToDTO(workoutDayExercise);
    }

    private ExerciseDay convertWorkoutDayExerciseToDTO(WorkoutDayExercise workoutDayExercise) {
        return modelMapper.map(workoutDayExercise, ExerciseDay.class);
    }

}
