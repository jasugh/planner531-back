package fi.jukka.planner531.controller;

import fi.jukka.planner531.dto.MainExerciseDto;
import fi.jukka.planner531.exception.BadRequestException;
import fi.jukka.planner531.exception.NotFoundException;
import fi.jukka.planner531.model.*;
import fi.jukka.planner531.repository.LoginRepository;
import fi.jukka.planner531.repository.MainExerciseRepository;
import fi.jukka.planner531.repository.MainExerciseHeaderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/main")
public class MainExerciseController {

    private final MainExerciseRepository mainExerciseRepository;
    private final MainExerciseHeaderRepository mainExerciseHeaderRepository;
    private final LoginRepository loginRepository;

    private static final ModelMapper modelMapper = new ModelMapper();

    private Throwable cause() {
        return new Exception("name");
    }

    @Autowired
    public MainExerciseController(MainExerciseRepository mainExerciseRepository,
                                  MainExerciseHeaderRepository mainExerciseHeaderRepository,
                                  LoginRepository loginRepository) {
        this.mainExerciseRepository = mainExerciseRepository;
        this.mainExerciseHeaderRepository = mainExerciseHeaderRepository;
        this.loginRepository = loginRepository;
    }

    @GetMapping("{loginId}")
    public List<MainExercise> getMainExercises(@PathVariable Long loginId) {
        Login login = loginRepository.findById(loginId)
                .orElseThrow(() -> new NotFoundException("Login not found with id " + loginId));

        if(login.getMainExerciseHeader().getId() == null){
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
        if (mainExerciseRepository.findFirstByName(mainExerciseDto.getName()) != null) {
            throw new BadRequestException("Main exercise with that name already exist", cause());
        }

        MainExercise newMainExercise = new MainExercise();
        newMainExercise.setName(mainExerciseDto.getName());
        newMainExercise.setRestTime(mainExerciseDto.getRestTime());
        newMainExercise.setWeightIncrement(mainExerciseDto.getWeightIncrement());
        newMainExercise.setOneRmKg(mainExerciseDto.getOneRmKg());
        newMainExercise.setOneRmReps(mainExerciseDto.getOneRmReps());
        newMainExercise.setOneRm(mainExerciseDto.getOneRm());
        newMainExercise.setNotes(mainExerciseDto.getNotes());
        return convertToDTO(mainExerciseRepository.save(newMainExercise));

    }


    @PutMapping("/{id}")
    public MainExerciseDto changeMainExercise(@RequestBody MainExercise mainExercise, @PathVariable Long id)  {
        if (mainExercise.getName().isEmpty()) {
            throw new BadRequestException("Main exercise name can not be blank", cause());
        }
        MainExercise exc = mainExerciseRepository.findFirstByName(mainExercise.getName());
        if (exc != null && !exc.getId().equals(id)) {
            throw new BadRequestException("Main exercise with that name already exist", cause());
        }

//        return mainExerciseRepository.findById(id)
//                .map(changedMainExercise -> {
//                    changedMainExercise.setName(mainExerciseDto.getName());
//                    changedMainExercise.setRestTime(mainExerciseDto.getRestTime());
//                    changedMainExercise.setWeightIncrement(mainExerciseDto.getWeightIncrement());
//                    changedMainExercise.setOneRmKg(mainExerciseDto.getOneRmKg());
//                    changedMainExercise.setOneRmReps(mainExerciseDto.getOneRmReps());
//                    changedMainExercise.setOneRm(mainExerciseDto.getOneRm());
//                    changedMainExercise.setNotes(mainExerciseDto.getNotes());
//
//                    return convertToDTO(mainExerciseRepository.save(changedMainExercise));
//                })
//                .orElseThrow(() -> new NotFoundException("Main exercise was not updated with id " + id));

        return null;
    }

    private MainExerciseDto convertToDTO(MainExercise mainExercise) {
        return modelMapper.map(mainExercise, MainExerciseDto.class);
    }
}
