package fi.jukka.planner531.controller;

import fi.jukka.planner531.dto.ExerciseDto;
import fi.jukka.planner531.exception.BadRequestException;
import fi.jukka.planner531.exception.NotFoundException;
import fi.jukka.planner531.model.Category;
import fi.jukka.planner531.model.Exercise;
import fi.jukka.planner531.repository.CategoryRepository;
import fi.jukka.planner531.repository.ExerciseRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;
    private final CategoryRepository categoryRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    private Throwable cause() {
        return new Exception("name");
    }

    @Autowired
    public ExerciseController(ExerciseRepository exerciseRepository,
                              CategoryRepository categoryRepository) {
        this.exerciseRepository = exerciseRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("")
    public List<ExerciseDto> getAllExercises() {
        List<Exercise> exercises = exerciseRepository.findAll();
        return exercises.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ExerciseDto getExerciseById(@PathVariable Long id) {
        return convertToDTO(exerciseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exercise not found with id " + id)));
    }

    @PostMapping("")
    public ExerciseDto saveExercise(@RequestBody ExerciseDto exerciseDto) {
        if (exerciseDto.getName().isEmpty()) {
            throw new BadRequestException("Exercise name can not be blank", cause());
        }
        if (exerciseRepository.findFirstByName(exerciseDto.getName()) != null) {
            throw new BadRequestException("Exercise with that name already exist", cause());
        }
        Category category = categoryRepository.findById(exerciseDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with id " + exerciseDto.getCategoryId()));

        Exercise newExercise = new Exercise();
        newExercise.setName(exerciseDto.getName());
        newExercise.setRestTime(exerciseDto.getRestTime());
        newExercise.setWeightIncrement(exerciseDto.getWeightIncrement());
        newExercise.setOneRmKg(exerciseDto.getOneRmKg());
        newExercise.setOneRmReps(exerciseDto.getOneRmReps());
        newExercise.setOneRm(exerciseDto.getOneRm());
        newExercise.setNotes(exerciseDto.getNotes());
        newExercise.setCategory(category);

        return convertToDTO(exerciseRepository.save(newExercise));
    }

    @PutMapping(value = "/{id}")
    public ExerciseDto replaceExercise(@RequestBody Exercise exercise, @PathVariable Long id) {
        if (exercise.getName().isEmpty()) {
            throw new BadRequestException("Exercise name can not be blank", cause());
        }
        Exercise exc = exerciseRepository.findFirstByName(exercise.getName());
        if (exc != null && !exc.getId().equals(id)) {
            throw new BadRequestException("Exercise with that name already exist", cause());
        }

        return exerciseRepository.findById(id)
                .map(changedExercise -> {
                    changedExercise.setName(exercise.getName());
                    changedExercise.setRestTime(exercise.getRestTime());
                    changedExercise.setWeightIncrement(exercise.getWeightIncrement());
                    changedExercise.setOneRmKg(exercise.getOneRmKg());
                    changedExercise.setOneRmReps(exercise.getOneRmReps());
                    changedExercise.setOneRm(exercise.getOneRm());
                    changedExercise.setNotes(exercise.getNotes());
                    if (exercise.getCategory() != null) {
                        Category category = categoryRepository.findById(exercise.getCategory().getId())
                                .orElseThrow(() -> new NotFoundException("Category not found with id " + exercise.getCategory().getId()));
                        changedExercise.setCategory(category);
                    }
                    return convertToDTO(exerciseRepository.save(changedExercise));
                })
                .orElseThrow(() -> new NotFoundException("Exercise was not updated with id " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExercise(@PathVariable Long id) {
        getExerciseById(id);
        exerciseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ExerciseDto convertToDTO(Exercise exercise) {
        return modelMapper.map(exercise, ExerciseDto.class);
    }
}
