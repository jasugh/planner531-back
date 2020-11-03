package fi.jukka.planner531.config;

import fi.jukka.planner531.controller.CategoryController;
import fi.jukka.planner531.controller.ExerciseController;
import fi.jukka.planner531.dto.CategoryDto;
import fi.jukka.planner531.dto.ExerciseDto;
import fi.jukka.planner531.dto.LoginDto;
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
    private final ExerciseRepository exerciseRepository;
    private final MainExerciseRepository mainExerciseHeaderReporitory;
    private final MainExerciseRepository mainExerciseRepository;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final CategoryController categoryController;
    private final ExerciseController exerciseController;

    public AppConfiguration(
            LoginRepository loginRepository,
            CategoryRepository categoryRepository,
            ExerciseRepository exerciseRepository,
            ExerciseRepository exerciseRepository1,
            MainExerciseRepository mainExerciseHeaderReporitory,
            MainExerciseRepository mainExerciseRepository,
            JwtUserDetailsService jwtUserDetailsService,
            CategoryController categoryController,
            ExerciseController exerciseController) {
        this.loginRepository = loginRepository;
        this.categoryRepository = categoryRepository;
        this.exerciseRepository = exerciseRepository1;
        this.mainExerciseHeaderReporitory = mainExerciseHeaderReporitory;
        this.mainExerciseRepository = mainExerciseRepository;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.categoryController = categoryController;
        this.exerciseController = exerciseController;
    }

    @Bean
    CommandLineRunner initDatabase(
    ) {
        return args -> {
            Login login = loginRepository.findFirstByLoginName("admin");
            if (login == null) {
                LoginDto newLoginDTO = new LoginDto();
                newLoginDTO.setLoginName("admin");
                newLoginDTO.setPassword(secret);
                login = jwtUserDetailsService.save(newLoginDTO);
            }

            MainExerciseHeader mainExerciseHeader = login.getMainExerciseHeader();

            if (categoryRepository.findFirstByName("Shoulders") == null) {
                createCategory("Shoulders");
            }
            if (categoryRepository.findFirstByName("Back") == null) {
                createCategory("Back");
            }
            if (categoryRepository.findFirstByName("Chest") == null) {
                createCategory("Chest");
            }
            if (categoryRepository.findFirstByName("Legs") == null) {
                createCategory("Legs");
            }

            if (exerciseRepository.findFirstByName("Dumbbell Lateral Raise") == null) {
                createExercise(
                        "Dumbbell Lateral Raise",
                        (float) 2.5,
                        categoryRepository.findFirstByName("Shoulders").getId());
            }
            if (exerciseRepository.findFirstByName("Dumbbell Row") == null) {
                createExercise(
                        "Dumbbell Row",
                        (float) 5.0,
                        categoryRepository.findFirstByName("Back").getId());
            }
            if (exerciseRepository.findFirstByName("Close-grip bench press") == null) {
                createExercise(
                        "Close-grip bench press",
                        (float) 2.5,
                        categoryRepository.findFirstByName("Chest").getId());
            }
            if (exerciseRepository.findFirstByName("Front Squat") == null) {
                createExercise(
                        "Front Squat",
                        (float) 5.0,
                        categoryRepository.findFirstByName("Legs").getId());
            }

            if (mainExerciseRepository.findFirstByName("Overhead Press") == null) {
                createMainExercise(
                        "Overhead Press",
                        (float) 2.5,
                        (float) 50,
                        1,
                        (float) 50,
                        1,
                        mainExerciseHeader);
            }
            if (mainExerciseRepository.findFirstByName("Dead Lift") == null) {
                createMainExercise(
                        "Dead Lift",
                        (float) 5.0,
                        (float) 120,
                        2,
                        (float) 128,
                        2,
                        mainExerciseHeader);
            }
            if (mainExerciseRepository.findFirstByName("Bench Press") == null) {
                createMainExercise(
                        "Bench Press", (
                                float) 2.5,
                        (float) 75,
                        1,
                        (float) 75,
                        3,
                        mainExerciseHeader);
            }
            if (mainExerciseRepository.findFirstByName("Squat") == null) {
                createMainExercise(
                        "Squat",
                        (float) 5.0,
                        (float) 80,
                        1,
                        (float) 80,
                        4,
                        mainExerciseHeader);
            }
        };
    }

    void createCategory(String categoryName) {
        CategoryDto newCategory = new CategoryDto();
        newCategory.setName(categoryName);
        newCategory.setNotes("");
        categoryController.saveCategory(newCategory);
    }

    void createExercise(String exerciseName, float weightIncrement, Long categoryId) {
        ExerciseDto newExercise = new ExerciseDto();
        newExercise.setName(exerciseName);
        newExercise.setRestTime(120);
        newExercise.setWeightIncrement(weightIncrement);
        newExercise.setOneRmKg(0);
        newExercise.setOneRmReps(0);
        newExercise.setOneRm(0);
        newExercise.setNotes("");
        newExercise.setCategoryId(categoryId);
        exerciseController.saveExercise(newExercise);
    }

    void createMainExercise(
            String mainExerciseName,
            float weightIncrement,
            float oneRmKg,
            int oneRmReps,
            float oneRm,
            int exerciseNumber,
            MainExerciseHeader mainExerciseHeader
    ) {
        MainExercise newMainExercise = new MainExercise();
        newMainExercise.setName(mainExerciseName);
        newMainExercise.setRestTime(120);
        newMainExercise.setWeightIncrement(weightIncrement);
        newMainExercise.setOneRmKg(oneRmKg);
        newMainExercise.setOneRmReps(oneRmReps);
        newMainExercise.setOneRm(oneRm);
        newMainExercise.setNotes("");
        newMainExercise.setExerciseNumber(exerciseNumber);
        newMainExercise.setMainExerciseHeader(mainExerciseHeader);
        mainExerciseRepository.save(newMainExercise);
        mainExerciseHeader.getMainExercises().add(newMainExercise);
    }
}
