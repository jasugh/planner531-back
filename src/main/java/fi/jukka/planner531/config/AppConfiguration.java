package fi.jukka.planner531.config;

import fi.jukka.planner531.controller.CategoryController;
import fi.jukka.planner531.controller.AssistanceExerciseController;
import fi.jukka.planner531.controller.MainExerciseController;
import fi.jukka.planner531.dto.CategoryDto;
import fi.jukka.planner531.dto.ExerciseDto;
import fi.jukka.planner531.dto.LoginDto;
import fi.jukka.planner531.dto.MainExerciseDto;
import fi.jukka.planner531.model.*;
import fi.jukka.planner531.repository.*;

import fi.jukka.planner531.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Value("${admin.secret}")
    private String secret;

    private final LoginRepository loginRepository;
    private final CategoryRepository categoryRepository;
    private final AssistanceExerciseRepository assistanceExerciseRepository;
    private final MainExerciseRepository mainExerciseRepository;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final CategoryController categoryController;
    private final AssistanceExerciseController assistanceExerciseController;
    private final MainExerciseController mainExerciseController;

    public AppConfiguration(
            LoginRepository loginRepository,
            CategoryRepository categoryRepository,
            AssistanceExerciseRepository assistanceExerciseRepository,
            MainExerciseRepository mainExerciseRepository,
            MainExerciseController mainExerciseController,
            JwtUserDetailsService jwtUserDetailsService,
            CategoryController categoryController,
            AssistanceExerciseController assistanceExerciseController) {
        this.loginRepository = loginRepository;
        this.categoryRepository = categoryRepository;
        this.assistanceExerciseRepository = assistanceExerciseRepository;
        this.mainExerciseRepository = mainExerciseRepository;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.categoryController = categoryController;
        this.assistanceExerciseController = assistanceExerciseController;
        this.mainExerciseController = mainExerciseController;
    }
//
//    @Bean
//    CommandLineRunner initDatabase() {
//        return args -> {
//            Login login = loginRepository.findFirstByLoginName("admin");
//            if (login == null) {
//                LoginDto newLoginDTO = new LoginDto();
//                newLoginDTO.setLoginName("admin");
//                newLoginDTO.setPassword(secret);
//                login = jwtUserDetailsService.save(newLoginDTO);
//            }
//
//            if (categoryRepository.findFirstByName("Shoulders") == null) {
//                createCategory("Shoulders");
//            }
//            if (categoryRepository.findFirstByName("Back") == null) {
//                createCategory("Back");
//            }
//            if (categoryRepository.findFirstByName("Chest") == null) {
//                createCategory("Chest");
//            }
//            if (categoryRepository.findFirstByName("Legs") == null) {
//                createCategory("Legs");
//            }
//
//            if (assistanceExerciseRepository.findFirstByName("Dumbbell Lateral Raise") == null) {
//                createExercise(
//                        "Dumbbell Lateral Raise",
//                        (float) 2.5,
//                        (float) 50,
//                        2,
//                        (float) 52,
//                        categoryRepository.findFirstByName("Shoulders").getId());
//            }
//            if (assistanceExerciseRepository.findFirstByName("Dumbbell Row") == null) {
//                createExercise(
//                        "Dumbbell Row",
//                        (float) 5.0,
//                        (float) 120,
//                        1,
//                        (float) 120,
//                        categoryRepository.findFirstByName("Back").getId());
//            }
//            if (assistanceExerciseRepository.findFirstByName("Close-grip bench press") == null) {
//                createExercise(
//                        "Close-grip bench press",
//                        (float) 2.5,
//                        (float) 70,
//                        1,
//                        (float) 70,
//                        categoryRepository.findFirstByName("Chest").getId());
//            }
//            if (assistanceExerciseRepository.findFirstByName("Front Squat") == null) {
//                createExercise(
//                        "Front Squat",
//                        (float) 5.0,
//                        (float) 80,
//                        1,
//                        (float) 80,
//                        categoryRepository.findFirstByName("Legs").getId());
//            }
//
//            MainExerciseHeader mainExerciseHeader = login.getMainExerciseHeader();
//
//            if (mainExerciseRepository.findFirstByName("Overhead Press") == null) {
//                MainExerciseDto mainExerciseDto = new MainExerciseDto();
//                mainExerciseDto.setName("Overhead Press");
//                mainExerciseDto.setRestTime(120);
//                mainExerciseDto.setWeightIncrement((float) 2.5);
//                mainExerciseDto.setOneRmKg((float) 50);
//                mainExerciseDto.setOneRmReps(1);
//                mainExerciseDto.setOneRm((float) 50);
//                mainExerciseDto.setNotes("");
//                mainExerciseDto.setExerciseNumber(1);
//                mainExerciseDto.setMainExerciseHeaderId(mainExerciseHeader.getId());
//                mainExerciseController.saveMainExercise(mainExerciseDto);
//            }
//
//            if (mainExerciseRepository.findFirstByName("Dead Lift") == null) {
//                MainExerciseDto mainExerciseDto = new MainExerciseDto();
//                mainExerciseDto.setName("Dead Lift");
//                mainExerciseDto.setRestTime(120);
//                mainExerciseDto.setWeightIncrement((float) 5.0);
//                mainExerciseDto.setOneRmKg((float) 120);
//                mainExerciseDto.setOneRmReps(1);
//                mainExerciseDto.setOneRm((float) 120);
//                mainExerciseDto.setNotes("");
//                mainExerciseDto.setExerciseNumber(2);
//                mainExerciseDto.setMainExerciseHeaderId(mainExerciseHeader.getId());
//                mainExerciseController.saveMainExercise(mainExerciseDto);
//            }
//            if (mainExerciseRepository.findFirstByName("Bench Press") == null) {
//                MainExerciseDto mainExerciseDto = new MainExerciseDto();
//                mainExerciseDto.setName("Bench Press");
//                mainExerciseDto.setRestTime(120);
//                mainExerciseDto.setWeightIncrement((float) 2.5);
//                mainExerciseDto.setOneRmKg((float) 70);
//                mainExerciseDto.setOneRmReps(1);
//                mainExerciseDto.setOneRm((float) 70);
//                mainExerciseDto.setNotes("");
//                mainExerciseDto.setExerciseNumber(3);
//                mainExerciseDto.setMainExerciseHeaderId(mainExerciseHeader.getId());
//                mainExerciseController.saveMainExercise(mainExerciseDto);
//
//            }
//            if (mainExerciseRepository.findFirstByName("Squat") == null) {
//                MainExerciseDto mainExerciseDto = new MainExerciseDto();
//                mainExerciseDto.setName("Squat");
//                mainExerciseDto.setRestTime(120);
//                mainExerciseDto.setWeightIncrement((float) 5.0);
//                mainExerciseDto.setOneRmKg((float) 80);
//                mainExerciseDto.setOneRmReps(1);
//                mainExerciseDto.setOneRm((float) 80);
//                mainExerciseDto.setNotes("");
//                mainExerciseDto.setExerciseNumber(4);
//                mainExerciseDto.setMainExerciseHeaderId(mainExerciseHeader.getId());
//                mainExerciseController.saveMainExercise(mainExerciseDto);
//            }
//        };
//    }
//
//    void createCategory(String categoryName) {
//        CategoryDto newCategory = new CategoryDto();
//        newCategory.setName(categoryName);
//        newCategory.setNotes("");
//        categoryController.saveCategory(newCategory);
//    }
//
//    void createExercise(
//            String exerciseName,
//            float weightIncrement,
//            float oneRmKg,
//            int onRmReps,
//            float setOneRm,
//            Long categoryId
//    ) {
//        ExerciseDto newExercise = new ExerciseDto();
//        newExercise.setName(exerciseName);
//        newExercise.setRestTime(120);
//        newExercise.setWeightIncrement(weightIncrement);
//        newExercise.setOneRmKg(oneRmKg);
//        newExercise.setOneRmReps(onRmReps);
//        newExercise.setOneRm(setOneRm);
//        newExercise.setNotes("");
//        newExercise.setCategoryId(categoryId);
//        assistanceExerciseController.saveExercise(newExercise);
//    }
}
