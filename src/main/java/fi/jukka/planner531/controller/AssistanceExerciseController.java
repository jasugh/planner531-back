package fi.jukka.planner531.controller;

import fi.jukka.planner531.dto.ExerciseDto;
import fi.jukka.planner531.exception.BadRequestException;
import fi.jukka.planner531.exception.NotFoundException;
import fi.jukka.planner531.model.AssistanceExercise;
import fi.jukka.planner531.model.Category;
import fi.jukka.planner531.repository.AssistanceExerciseRepository;
import fi.jukka.planner531.repository.CategoryRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exercise")
public class AssistanceExerciseController {

    private final AssistanceExerciseRepository assistanceExerciseRepository;
    private final CategoryRepository categoryRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    private Throwable cause() {
        return new Exception("name");
    }

    @Autowired
    public AssistanceExerciseController(AssistanceExerciseRepository assistanceExerciseRepository,
                                        CategoryRepository categoryRepository) {
        this.assistanceExerciseRepository = assistanceExerciseRepository;
        this.categoryRepository = categoryRepository;
    }



    @GetMapping("")
    public List<ExerciseDto> getAllExercises() {
        List<AssistanceExercise> assistanceExercises = assistanceExerciseRepository.findAll();
        return assistanceExercises.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ExerciseDto getExerciseById(@PathVariable Long id) {
        return convertToDTO(assistanceExerciseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AssistanceExercise not found with id " + id)));
    }

    @PostMapping("")
    public ExerciseDto saveExercise(@RequestBody ExerciseDto exerciseDto) {
        if (exerciseDto.getName().isEmpty()) {
            throw new BadRequestException("AssistanceExercise name can not be blank", cause());
        }
        if (assistanceExerciseRepository.findFirstByName(exerciseDto.getName()) != null) {
            throw new BadRequestException("AssistanceExercise with that name already exist", cause());
        }
        Category category = categoryRepository.findById(exerciseDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with id " + exerciseDto.getCategoryId()));

        AssistanceExercise newAssistanceExercise = new AssistanceExercise();
        newAssistanceExercise.setName(exerciseDto.getName());
        newAssistanceExercise.setRestTime(exerciseDto.getRestTime());
        newAssistanceExercise.setWeightIncrement(exerciseDto.getWeightIncrement());
        newAssistanceExercise.setOneRmKg(exerciseDto.getOneRmKg());
        newAssistanceExercise.setOneRmReps(exerciseDto.getOneRmReps());
        newAssistanceExercise.setOneRm(exerciseDto.getOneRm());
        newAssistanceExercise.setNotes(exerciseDto.getNotes());
        newAssistanceExercise.setCategory(category);

        return convertToDTO(assistanceExerciseRepository.save(newAssistanceExercise));
    }

    @PutMapping(value = "/{id}")
    public ExerciseDto replaceExercise(@RequestBody AssistanceExercise assistanceExercise, @PathVariable Long id) {
        if (assistanceExercise.getName().isEmpty()) {
            throw new BadRequestException("AssistanceExercise name can not be blank", cause());
        }
        AssistanceExercise exc = assistanceExerciseRepository.findFirstByName(assistanceExercise.getName());
        if (exc != null && !exc.getId().equals(id)) {
            throw new BadRequestException("AssistanceExercise with that name already exist", cause());
        }

        return assistanceExerciseRepository.findById(id)
                .map(changedExercise -> {
                    changedExercise.setName(assistanceExercise.getName());
                    changedExercise.setRestTime(assistanceExercise.getRestTime());
                    changedExercise.setWeightIncrement(assistanceExercise.getWeightIncrement());
                    changedExercise.setOneRmKg(assistanceExercise.getOneRmKg());
                    changedExercise.setOneRmReps(assistanceExercise.getOneRmReps());
                    changedExercise.setOneRm(assistanceExercise.getOneRm());
                    changedExercise.setNotes(assistanceExercise.getNotes());
                    if (assistanceExercise.getCategory() != null) {
                        Category category = categoryRepository.findById(assistanceExercise.getCategory().getId())
                                .orElseThrow(() -> new NotFoundException("Category not found with id " + assistanceExercise.getCategory().getId()));
                        changedExercise.setCategory(category);
                    }
                    return convertToDTO(assistanceExerciseRepository.save(changedExercise));
                })
                .orElseThrow(() -> new NotFoundException("AssistanceExercise was not updated with id " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExercise(@PathVariable Long id) {
        getExerciseById(id);
        assistanceExerciseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ExerciseDto convertToDTO(AssistanceExercise assistanceExercise) {
        return modelMapper.map(assistanceExercise, ExerciseDto.class);
    }
}
