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
    private final JwtUserDetailsService jwtUserDetailsService;
    private final CategoryController categoryController;
    private final ExerciseController exerciseController;

    public AppConfiguration(
            LoginRepository loginRepository,
            CategoryRepository categoryRepository,
            ExerciseRepository exerciseRepository,
            JwtUserDetailsService jwtUserDetailsService,
            CategoryController categoryController,
            ExerciseController exerciseController) {
        this.loginRepository = loginRepository;
        this.categoryRepository = categoryRepository;
        this.exerciseRepository = exerciseRepository;
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
                jwtUserDetailsService.save(newLoginDTO);
            }

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

            if (exerciseRepository.findFirstByName("Overhead Press") == null) {
                createExercise(
                        "Overhead Press",
                        (float) 2.5,
                        categoryRepository.findFirstByName("Shoulders").getId());
            }
            if (exerciseRepository.findFirstByName("Dead Lift") == null) {
                createExercise(
                        "Dead Lift",
                        (float) 5.0,
                        categoryRepository.findFirstByName("Back").getId());
            }
            if (exerciseRepository.findFirstByName("Bench Press") == null) {
                createExercise(
                        "Bench Press",
                        (float) 2.5,
                        categoryRepository.findFirstByName("Chest").getId());
            }
            if (exerciseRepository.findFirstByName("Squat") == null) {
                createExercise(
                        "Squat",
                        (float) 5.0,
                        categoryRepository.findFirstByName("Legs").getId());
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
        newExercise.setNotes("");
        newExercise.setCategoryId(categoryId);
        exerciseController.saveExercise(newExercise);
    }
}


