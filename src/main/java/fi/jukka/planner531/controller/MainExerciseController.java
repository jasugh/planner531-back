package fi.jukka.planner531.controller;

import fi.jukka.planner531.dto.MainExerciseDto;
import fi.jukka.planner531.exception.BadRequestException;
import fi.jukka.planner531.exception.NotFoundException;
import fi.jukka.planner531.model.*;
import fi.jukka.planner531.repository.AssistanceExerciseRepository;
import fi.jukka.planner531.repository.LoginRepository;
import fi.jukka.planner531.repository.MainExerciseRepository;
import fi.jukka.planner531.repository.MainExerciseHeaderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/main")
public class MainExerciseController {

    private final MainExerciseRepository mainExerciseRepository;
    private final MainExerciseHeaderRepository mainExerciseHeaderRepository;
    private final AssistanceExerciseRepository assistanceExerciseRepository;
    private final LoginRepository loginRepository;

    private static final ModelMapper modelMapper = new ModelMapper();

    private Throwable cause() {
        return new Exception("name");
    }

    @Autowired
    public MainExerciseController(MainExerciseRepository mainExerciseRepository,
                                  MainExerciseHeaderRepository mainExerciseHeaderRepository,
                                  AssistanceExerciseRepository assistanceExerciseRepository,
                                  LoginRepository loginRepository) {
        this.mainExerciseRepository = mainExerciseRepository;
        this.mainExerciseHeaderRepository = mainExerciseHeaderRepository;
        this.assistanceExerciseRepository = assistanceExerciseRepository;
        this.loginRepository = loginRepository;
    }

    @GetMapping("{loginId}")
    public List<MainExercise> getMainExercises(@PathVariable Long loginId) {
        Login login = loginRepository.findById(loginId)
                .orElseThrow(() -> new NotFoundException("Login not found with id " + loginId));

        if (login.getMainExerciseHeader().getId() == null) {
            throw new NotFoundException("Main exercises not found from login (loginId) " + loginId);
        }

        MainExerciseHeader mainExerciseHeader = mainExerciseHeaderRepository.getOne(login.getMainExerciseHeader().getId());
        return mainExerciseHeader.getMainExercises();
    }

    @PostMapping("")
    public MainExerciseDto saveMainExercise(@RequestBody MainExerciseDto mainExerciseDto) {
        if (mainExerciseDto.getName().isEmpty()) {
            throw new BadRequestException("Main exercise name can not be blank", cause());
        }

        // TODO: add appropriate checks

//        if (mainExerciseRepository.findFirstByName(mainExerciseDto.getName()) != null) {
//            throw new BadRequestException("Main exercise with that name already exist", cause());
//        }

        MainExerciseHeader mainExerciseHeader = mainExerciseHeaderRepository.findById(mainExerciseDto.getMainExerciseHeaderId())
                .orElseThrow(() -> new NotFoundException("Main AssistanceExercise Header not found with id " + mainExerciseDto.getMainExerciseHeaderId()));

        MainExercise newMainExercise = new MainExercise();
        newMainExercise.setName(mainExerciseDto.getName());
        newMainExercise.setRestTime(mainExerciseDto.getRestTime());
        newMainExercise.setWeightIncrement(mainExerciseDto.getWeightIncrement());
        newMainExercise.setOneRmKg(mainExerciseDto.getOneRmKg());
        newMainExercise.setOneRmReps(mainExerciseDto.getOneRmReps());
        newMainExercise.setOneRm(mainExerciseDto.getOneRm());
        newMainExercise.setNotes(mainExerciseDto.getNotes());
        newMainExercise.setExerciseNumber(mainExerciseDto.getExerciseNumber());
        newMainExercise.setMainExerciseHeader(mainExerciseHeader);
        mainExerciseRepository.save(newMainExercise);

        return convertToDTO(newMainExercise);
    }

    @PutMapping("")
    public MainExerciseDto changeMainExercise(@RequestBody MainExerciseDto mainExerciseDto) {

        // TODO: add appropriate checks

//        MainExerciseHeader mainExerciseHeader = mainExerciseDto.getMainExerciseHeader();
//        List<MainExercise> mainExercises = mainExerciseRepository.findAllByMainExerciseHeaderId(mainExerciseHeader.getId());

        return mainExerciseRepository.findById(mainExerciseDto.getId())
                .map(changedMainExercise -> {
                    changedMainExercise.setName(mainExerciseDto.getName());
                    changedMainExercise.setRestTime(mainExerciseDto.getRestTime());
                    changedMainExercise.setWeightIncrement(mainExerciseDto.getWeightIncrement());
                    changedMainExercise.setOneRmKg(mainExerciseDto.getOneRmKg());
                    changedMainExercise.setOneRmReps(mainExerciseDto.getOneRmReps());
                    changedMainExercise.setOneRm(mainExerciseDto.getOneRm());
                    changedMainExercise.setNotes(mainExerciseDto.getNotes());

                    return convertToDTO(mainExerciseRepository.save(changedMainExercise));
                })
                .orElseThrow(() -> new NotFoundException("Main exercise was not updated with id " + mainExerciseDto.getId()));
    }

    @PutMapping("/{exerciseId}/assistance")
    public MainExerciseDto addAssistanceToMainExercise(@RequestBody MainExerciseDto mainExerciseDto, @PathVariable Long exerciseId) {

        AssistanceExercise assistanceExercise = assistanceExerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new NotFoundException("AssistanceExercise not found with id " + exerciseId));

        return mainExerciseRepository.findById(mainExerciseDto.getId())
                .map(changedMainExercise -> {
                    changedMainExercise.getAssistanceExercises().add(assistanceExercise);
                    return convertToDTO(mainExerciseRepository.save(changedMainExercise));
                })
                .orElseThrow(() -> new NotFoundException("Main assistanceExercise was not updated with id " + mainExerciseDto.getId()));
    }

    @PutMapping("/{exerciseId}/remove")
    public ResponseEntity<?> removeAssistanceFromMainExercise(@RequestBody MainExerciseDto mainExerciseDto, @PathVariable Long exerciseId) {
        AssistanceExercise assistanceExercise = assistanceExerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new NotFoundException("Assistance Exercise not found with id " + exerciseId));

        try {
            MainExercise mainExercise = mainExerciseRepository.getOne(mainExerciseDto.getId());
            mainExercise.removeAssistanceExercise(assistanceExercise);
            mainExerciseRepository.save(mainExercise);
        } catch (ClassCastException e){
            throw new NotFoundException("Main Exercise was not found with id " + mainExerciseDto.getId());
        }

        return ResponseEntity.noContent().build();
    }

    private MainExerciseDto convertToDTO(MainExercise mainExercise) {
        return modelMapper.map(mainExercise, MainExerciseDto.class);
    }
}
