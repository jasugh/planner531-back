package fi.jukka.planner531.controller;

import fi.jukka.planner531.dto.*;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plan")
public class WorkoutDayPlanController {

    private final CategoryController categoryController;
    private final ExerciseController exerciseController;
    private final CategoryRepository categoryRepository;
    private final ExerciseRepository exerciseRepository;
    private final LoginRepository loginRepository;
    private final StartingDetailsRepository startingDetailsRepository;
    private final WorkoutDayPlanRepository workoutDayPlanRepository;
    private final WorkoutDayRepository workoutDayRepository;
    private final WorkoutDayExerciseRepository workoutDayExerciseRepository;
    private final WorkoutDaySetRepository workoutDaySetRepository;

    private Throwable cause(String field) {
        return new Exception(field);
    }

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public WorkoutDayPlanController(
            CategoryController categoryController,
            ExerciseController exerciseController,
            CategoryRepository
                    categoryRepository,
            ExerciseRepository exerciseRepository,
            LoginRepository loginRepository,
            StartingDetailsRepository startingDetailsRepository,
            WorkoutDayPlanRepository workoutDayPlanRepository,
            WorkoutDayRepository workoutDayRepository,
            WorkoutDayExerciseRepository workoutDayExerciseRepository,
            WorkoutDaySetRepository workoutDaySetRepository
    ) {
        this.categoryController = categoryController;
        this.exerciseController = exerciseController;
        this.categoryRepository = categoryRepository;
        this.exerciseRepository = exerciseRepository;
        this.loginRepository = loginRepository;
        this.startingDetailsRepository = startingDetailsRepository;
        this.workoutDayPlanRepository = workoutDayPlanRepository;
        this.workoutDayRepository = workoutDayRepository;
        this.workoutDayExerciseRepository = workoutDayExerciseRepository;
        this.workoutDaySetRepository = workoutDaySetRepository;
    }

    @GetMapping("/{id}/plan")
    public WorkoutDayPlanGetDto getWorkoutDayPlanById(@PathVariable Long id) {
        WorkoutDayPlan workoutDayPlan = workoutDayPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("WorkoutDayPlan not found with id: " + id));

        return convertToGetDto(workoutDayPlan);
    }

    @GetMapping("/{loginId}/user")
    public WorkoutDayPlanGetDto getWorkoutDayPlanByLoginId(@PathVariable Long loginId) {
        // Get login information
        Login login = loginRepository.findById(loginId)
                .orElseThrow(() -> new NotFoundException("Login data not found with id " + loginId));

        if (login.getWorkoutDayPlan() == null) {
            throw new NotFoundException("Workout Day Plan was not found with Login id " + loginId);
        }
        return convertToGetDto(workoutDayPlanRepository
                .getOne(login.getWorkoutDayPlan().getId()));
    }

    @GetMapping("/{loginId}/user/next")
    public WorkoutDay getNextWorkoutByLoginId(@PathVariable Long loginId) {
        // Get login information
        Login login = loginRepository.findById(loginId)
                .orElseThrow(() -> new NotFoundException("Login data not found with id " + loginId));

        if (login.getWorkoutDayPlan() == null) {
            throw new NotFoundException("Workout Day Plan was not found with Login id " + loginId);
        }

        WorkoutDayPlan workoutDayPlan = workoutDayPlanRepository.getOne(login.getWorkoutDayPlan().getId());

        Optional<WorkoutDay> workoutDays = workoutDayPlan.getWorkoutDays()
                .stream()
                .filter(workoutDay -> !workoutDay.isCompleted())
                .findFirst();

        if (workoutDays.isPresent()) {
            return workoutDays.get();
        } else {
            throw new NotFoundException("No open workouts for user: " + login.getLoginName() + " / " + loginId);
        }
    }

    @PutMapping("/{id}/skip")
    public WorkoutDay skipWorkout(@PathVariable Long id) {
        WorkoutDay workoutDay = workoutDayRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workout not found with id " + id));

        workoutDay.setCompleted(true);
        workoutDayRepository.save(workoutDay);
        return workoutDay;
    }

    @PutMapping("/{id}/complete")
    public WorkoutDay completeWorkout(@PathVariable Long id) {
        WorkoutDay workoutDay = workoutDayRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workout not found with id " + id));

        workoutDay.setCompleted(true);
        workoutDayRepository.save(workoutDay);
        return workoutDay;
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> saveWorkoutDayPlan(@PathVariable Long id) {
        StartingDetails startingDetails = startingDetailsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Starting details not found with " + id));

        if (startingDetails.getLogin() == null) {
            throw new NotFoundException("There are no Login in Starting Details with id " + id);
        }
        Login login = startingDetails.getLogin();
        if (login.getWorkoutDayPlan() != null) {
            Long workoutDayPlanId = login.getWorkoutDayPlan().getId();
            login.setWorkoutDayPlan(null);
            loginRepository.save(login);
            workoutDayPlanRepository.deleteById(workoutDayPlanId);
        }

        // Create a new Workout Plan
        WorkoutDayPlan workoutDayPlan = new WorkoutDayPlan();
        workoutDayPlan.setStartingDate(startingDetails.getStartingDate());
        WorkoutDayPlan newWorkoutDayPlan = workoutDayPlanRepository.save(workoutDayPlan);

        login.setWorkoutDayPlan(newWorkoutDayPlan);
        loginRepository.save(login);

        // Create individual exercises for each workout day
        createWorkoutDays(startingDetails, workoutDayPlan);

        String message = "New Workout Plan was created with id " + workoutDayPlan.getId();
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

    public void createWorkoutDays(StartingDetails startingDetails, WorkoutDayPlan workoutDayPlan) {
        // Check that all the 4 5/3/1-exercises exist and load them to an array
        List<Exercise> defaultExercises = checkExercisesAndCategories();

        float warmup1Kg = 0;
        float warmup2Kg = 0;
        float warmup3Kg = 0;
        float main1Kg = 0;
        float main2Kg = 0;
        float main3Kg = 0;
        int main1Reps = 0;
        int main2Reps = 0;
        int main3Reps = 0;
        float bbbKg = 0;

        float[] warmUpKgs;
        float[] mainKgs;
        int[] mainReps;

        float press1RM = startingDetails.getPress1rm();
        float deadLift1RM = startingDetails.getDeadLift1rm();
        float benchPress1RM = startingDetails.getBenchPress1rm();
        float squat1RM = startingDetails.getSquat1rm();

        float pressKg = press1RM * (startingDetails.getTrainingMax() / 100);
        float deadLiftKg = deadLift1RM * (startingDetails.getTrainingMax() / 100);
        float benchPressKg = benchPress1RM * (startingDetails.getTrainingMax() / 100);
        float squatKg = squat1RM * (startingDetails.getTrainingMax() / 100);

        int cycle = 1;
        int week = 1;
        int day = 1;

        while (cycle <= startingDetails.getNumberOfCycles()) {
            while (week <= 4) {

                switch (week) {
                    case 1:
                    case 4: {
                        main1Reps = 5;
                        main2Reps = 5;
                        main3Reps = 5;
                        break;
                    }
                    case 2: {
                        main1Reps = 3;
                        main2Reps = 3;
                        main3Reps = 3;
                        break;
                    }
                    case 3: {
                        main1Reps = 5;
                        main2Reps = 3;
                        main3Reps = 1;
                        break;
                    }
                }
                while (day <= 4) {

                    // day numbers always correspond to exercises (1,2,3,4)
                    switch (day) {
                        case 1: {
                            warmup1Kg = roundKgs(pressKg * 0.4f);
                            warmup2Kg = roundKgs(pressKg * 0.5f);
                            warmup3Kg = roundKgs(pressKg * 0.6f);
                            bbbKg = roundKgs(pressKg * 0.65f);
                            switch (week) {
                                case 1: {
                                    main1Kg = roundKgs(pressKg * 0.65f);
                                    main2Kg = roundKgs(pressKg * 0.75f);
                                    main3Kg = roundKgs(pressKg * 0.85f);
                                    break;
                                }
                                case 2: {
                                    main1Kg = roundKgs(pressKg * 0.70f);
                                    main2Kg = roundKgs(pressKg * 0.80f);
                                    main3Kg = roundKgs(pressKg * 0.90f);
                                    break;
                                }
                                case 3: {
                                    main1Kg = roundKgs(pressKg * 0.75f);
                                    main2Kg = roundKgs(pressKg * 0.85f);
                                    main3Kg = roundKgs(pressKg * 0.95f);
                                    break;
                                }
                                case 4: {
                                    main1Kg = roundKgs(pressKg * 0.40f);
                                    main2Kg = roundKgs(pressKg * 0.50f);
                                    main3Kg = roundKgs(pressKg * 0.60f);
                                    break;
                                }
                            }
                        }
                        break;
                        case 2: {
                            warmup1Kg = roundKgs(deadLiftKg * 0.4f);
                            warmup2Kg = roundKgs(deadLiftKg * 0.5f);
                            warmup3Kg = roundKgs(deadLiftKg * 0.6f);
                            bbbKg = roundKgs(deadLiftKg * 0.65f);
                            switch (week) {
                                case 1: {
                                    main1Kg = roundKgs(deadLiftKg * 0.65f);
                                    main2Kg = roundKgs(deadLiftKg * 0.75f);
                                    main3Kg = roundKgs(deadLiftKg * 0.85f);
                                    break;
                                }
                                case 2: {
                                    main1Kg = roundKgs(deadLiftKg * 0.70f);
                                    main2Kg = roundKgs(deadLiftKg * 0.80f);
                                    main3Kg = roundKgs(deadLiftKg * 0.90f);
                                    break;
                                }
                                case 3: {
                                    main1Kg = roundKgs(deadLiftKg * 0.75f);
                                    main2Kg = roundKgs(deadLiftKg * 0.85f);
                                    main3Kg = roundKgs(deadLiftKg * 0.95f);
                                    break;
                                }
                                case 4: {
                                    main1Kg = roundKgs(deadLiftKg * 0.40f);
                                    main2Kg = roundKgs(deadLiftKg * 0.50f);
                                    main3Kg = roundKgs(deadLiftKg * 0.60f);
                                    break;
                                }
                            }
                        }
                        break;
                        case 3: {
                            warmup1Kg = roundKgs(benchPressKg * 0.4f);
                            warmup2Kg = roundKgs(benchPressKg * 0.5f);
                            warmup3Kg = roundKgs(benchPressKg * 0.6f);
                            bbbKg = roundKgs(benchPressKg * 0.65f);
                            switch (week) {
                                case 1: {
                                    main1Kg = roundKgs(benchPressKg * 0.65f);
                                    main2Kg = roundKgs(benchPressKg * 0.75f);
                                    main3Kg = roundKgs(benchPressKg * 0.85f);
                                    break;
                                }
                                case 2: {
                                    main1Kg = roundKgs(benchPressKg * 0.70f);
                                    main2Kg = roundKgs(benchPressKg * 0.80f);
                                    main3Kg = roundKgs(benchPressKg * 0.90f);
                                    break;
                                }
                                case 3: {
                                    main1Kg = roundKgs(benchPressKg * 0.75f);
                                    main2Kg = roundKgs(benchPressKg * 0.85f);
                                    main3Kg = roundKgs(benchPressKg * 0.95f);
                                    break;
                                }
                                case 4: {
                                    main1Kg = roundKgs(benchPressKg * 0.40f);
                                    main2Kg = roundKgs(benchPressKg * 0.50f);
                                    main3Kg = roundKgs(benchPressKg * 0.60f);
                                    break;
                                }
                            }
                        }
                        break;
                        case 4: {
                            warmup1Kg = roundKgs(squatKg * 0.4f);
                            warmup2Kg = roundKgs(squatKg * 0.5f);
                            warmup3Kg = roundKgs(squatKg * 0.6f);
                            bbbKg = roundKgs(squatKg * 0.65f);
                            switch (week) {
                                case 1: {
                                    main1Kg = roundKgs(squatKg * 0.65f);
                                    main2Kg = roundKgs(squatKg * 0.75f);
                                    main3Kg = roundKgs(squatKg * 0.85f);
                                    break;
                                }
                                case 2: {
                                    main1Kg = roundKgs(squatKg * 0.70f);
                                    main2Kg = roundKgs(squatKg * 0.80f);
                                    main3Kg = roundKgs(squatKg * 0.90f);
                                    break;
                                }
                                case 3: {
                                    main1Kg = roundKgs(squatKg * 0.75f);
                                    main2Kg = roundKgs(squatKg * 0.85f);
                                    main3Kg = roundKgs(squatKg * 0.95f);
                                    break;
                                }
                                case 4: {
                                    main1Kg = roundKgs(squatKg * 0.40f);
                                    main2Kg = roundKgs(squatKg * 0.50f);
                                    main3Kg = roundKgs(squatKg * 0.60f);
                                    break;
                                }
                            }
                        }
                    }
                    warmUpKgs = new float[]{warmup1Kg, warmup2Kg, warmup3Kg};
                    mainKgs = new float[]{main1Kg, main2Kg, main3Kg};
                    mainReps = new int[]{main1Reps, main2Reps, main3Reps};

                    createWorkoutDay(cycle, week, day, warmUpKgs, mainKgs, mainReps, bbbKg, workoutDayPlan, defaultExercises);

                    day++;
                }
                day = 1;
                week++;
            }
            // After each cycle increase weights to 1RM according starting details selections (2.5 upper body, 5.0 lower body)
            press1RM = press1RM + startingDetails.getPressIncrement();
            deadLift1RM = deadLift1RM + startingDetails.getDeadLiftIncrement();
            benchPress1RM = benchPress1RM + startingDetails.getBenchIncrement();
            squat1RM = squat1RM + startingDetails.getSquatIncrement();

            // Calculate new training max.
            pressKg = press1RM * (startingDetails.getTrainingMax() / 100);
            deadLiftKg = deadLift1RM * (startingDetails.getTrainingMax() / 100);
            benchPressKg = benchPress1RM * (startingDetails.getTrainingMax() / 100);
            squatKg = squat1RM * (startingDetails.getTrainingMax() / 100);

            week = 1;
            cycle++;
        }
    }

    public void createWorkoutDay(
            int cycle,
            int week,
            int day,
            float[] warmUpKgs,
            float[] mainKgs,
            int[] mainReps,
            float bbbKg,
            WorkoutDayPlan workoutDayPlan,
            List<Exercise> defaultExercises) {

        WorkoutDay workoutDay = new WorkoutDay();
        workoutDay.setCycle(cycle);
        workoutDay.setWeek(week);
        workoutDay.setDayNumber(day);
        workoutDay.setCompleted(false);
        workoutDay.setWorkoutDate(null);
        workoutDay.setWorkoutDayPlan(workoutDayPlan);
        workoutDayRepository.save(workoutDay);

        WorkoutDayExercise workoutDayExercise = createExercise(defaultExercises.get(day - 1), workoutDay);

        createWarmUpSets(warmUpKgs, workoutDayExercise);
        createMainSets(mainKgs, mainReps, workoutDayExercise);
        createBBBSets(bbbKg, workoutDayExercise);
    }

    public WorkoutDayExercise createExercise(Exercise exercise, WorkoutDay workoutDay) {
        WorkoutDayExercise workoutDayExercise = new WorkoutDayExercise();
        workoutDayExercise.setExercise(exercise);
        workoutDayExercise.setWorkoutDay(workoutDay);
        return workoutDayExerciseRepository.save(workoutDayExercise);
    }

    public void createWarmUpSets(float[] warmUpKgs, WorkoutDayExercise workoutDayExercise) {
        int i;
        for (i = 1; i < 4; i++) {
            WorkoutDaySet workoutDaySet = new WorkoutDaySet();
            workoutDaySet.setKgs(warmUpKgs[i - 1]);
            workoutDaySet.setReps(5);
            if (i == 3) {
                workoutDaySet.setReps(3);
            }
            workoutDaySet.setFinished(false);
            workoutDaySet.setTypeOfSet("W");
            workoutDaySet.setWorkoutDayExercise(workoutDayExercise);
            workoutDaySetRepository.save(workoutDaySet);
        }
    }

    public void createMainSets(float[] mainKgs, int[] mainReps, WorkoutDayExercise workoutDayExercise) {
        int i;
        for (i = 1; i < 4; i++) {
            WorkoutDaySet workoutDaySet = new WorkoutDaySet();
            workoutDaySet.setKgs(mainKgs[i - 1]);
            workoutDaySet.setReps(mainReps[i - 1]);
            workoutDaySet.setFinished(false);
            workoutDaySet.setTypeOfSet("M");
            workoutDaySet.setWorkoutDayExercise(workoutDayExercise);
            workoutDaySetRepository.save(workoutDaySet);
        }
    }

    public void createBBBSets(float bbbKg, WorkoutDayExercise workoutDayExercise) {
        int i;
        for (i = 1; i < 6; i++) {
            WorkoutDaySet workoutDaySet = new WorkoutDaySet();
            workoutDaySet.setKgs(bbbKg);
            workoutDaySet.setReps(10);
            workoutDaySet.setTypeOfSet("B");
            workoutDaySet.setFinished(false);
            workoutDaySet.setWorkoutDayExercise(workoutDayExercise);
            workoutDaySetRepository.save(workoutDaySet);
        }
    }

    // round kgs up to nearest 2.5
    static float roundKgs(float n) {
        return (float) Math.ceil(n * 0.4f) / 0.4f;
    }

    public List<Exercise> checkExercisesAndCategories() {
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

        List<Exercise> exercises = new ArrayList<>();
        if (exerciseRepository.findFirstByName("Overhead Press") == null) {
            createExercise(
                    "Overhead Press",
                    (float) 2.5,
                    categoryRepository.findFirstByName("Shoulders").getId());
        }
        exercises.add(exerciseRepository.findFirstByName("Overhead Press"));

        if (exerciseRepository.findFirstByName("Dead Lift") == null) {
            createExercise(
                    "Dead Lift",
                    (float) 5.0,
                    categoryRepository.findFirstByName("Back").getId());
        }
        exercises.add(exerciseRepository.findFirstByName("Dead Lift"));

        if (exerciseRepository.findFirstByName("Bench Press") == null) {
            createExercise(
                    "Bench Press",
                    (float) 2.5,
                    categoryRepository.findFirstByName("Chest").getId());
        }
        exercises.add(exerciseRepository.findFirstByName("Bench Press"));

        if (exerciseRepository.findFirstByName("Squat") == null) {
            createExercise(
                    "Squat",
                    (float) 5.0,
                    categoryRepository.findFirstByName("Legs").getId());
        }
        exercises.add(exerciseRepository.findFirstByName("Squat"));

        return exercises;
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
//        newExercise.setCategoryId(categoryId);
        exerciseController.saveExercise(newExercise);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        WorkoutDayPlan workoutDayPlan = workoutDayPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workout Day Plan not found with id " + id));

        if (workoutDayPlan.getLogin() == null) {
            throw new NotFoundException("There is no Login in Workout Day Plan with id " + id);
        }


        Login login = loginRepository.findById(workoutDayPlan.getLogin().getId())
                .orElseThrow(() -> new NotFoundException("Login not found with id " + workoutDayPlan.getLogin().getId()));
        login.setWorkoutDayPlan(null);
        loginRepository.save(login);

        workoutDayPlanRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private WorkoutDayPlanGetDto convertToGetDto(WorkoutDayPlan workoutDayPlan) {
        WorkoutDayPlanGetDto workoutDayPlanGetDto = new WorkoutDayPlanGetDto();

        workoutDayPlanGetDto.setId(workoutDayPlan.getId());
        workoutDayPlanGetDto.setStartingDate(workoutDayPlan.getStartingDate());
        workoutDayPlanGetDto.setCycleDto(toCycleDto(workoutDayPlan.getWorkoutDays()));

        return workoutDayPlanGetDto;
    }

    private List<CycleDto> toCycleDto(List<WorkoutDay> workoutDays) {
        List<CycleDto> cycleDtoS = new ArrayList<>();

        int i;
        int cycle = 0;

        for (i = 0; i < workoutDays.size(); i++) {
            if (workoutDays.get(i).getCycle() != cycle) {
                cycle = workoutDays.get(i).getCycle();

                CycleDto cycleDto = new CycleDto();
                cycleDto.setCycle(workoutDays.get(i).getCycle());
                cycleDto.setWeekDtos(toWeekDto(workoutDays, cycle, i));

                cycleDtoS.add(cycleDto);
            }
        }
        return cycleDtoS;
    }

    private List<WeekDto> toWeekDto(List<WorkoutDay> workoutDays, int cycle, int i) {
        List<WeekDto> weekDtoS = new ArrayList<>();

        int ii;
        int week = 0;
        for (ii = i; ii < workoutDays.size(); ii++) {
            if (workoutDays.get(ii).getCycle() == cycle) {
                if (workoutDays.get(ii).getWeek() != week) {
                    week = workoutDays.get(ii).getWeek();

                    WeekDto weekDto = new WeekDto();
                    weekDto.setWeek(workoutDays.get(ii).getWeek());
                    weekDto.setDayDtos(toDayDto(workoutDays, cycle, week, ii));
                    weekDtoS.add(weekDto);
                }
            }
        }
        return weekDtoS;
    }

    private List<DayDto> toDayDto(List<WorkoutDay> workoutDays, int cycle, int week, int ii) {
        List<DayDto> dayDtoS = new ArrayList<>();

        int iii;
        int dayNumber = 0;

        for (iii = ii; iii < workoutDays.size(); iii++) {
            if (workoutDays.get(iii).getCycle() == cycle && workoutDays.get(iii).getWeek() == week) {
                if (workoutDays.get(iii).getDayNumber() != dayNumber) {
                    dayNumber = workoutDays.get(iii).getDayNumber();

                    DayDto dayDto = new DayDto();
                    dayDto.setDay(workoutDays.get(iii).getDayNumber());
                    dayDto.setWorkoutDate(workoutDays.get(iii).getWorkoutDate());
                    dayDto.setCompleted(workoutDays.get(iii).isCompleted());
                    dayDto.setDayExerciseDtos(toDayExerciseDto(workoutDays.get(iii).getWorkoutDayExercises()));
                    dayDtoS.add(dayDto);
                }
            }
        }
        return dayDtoS;
    }

    private List<DayExerciseDto> toDayExerciseDto(List<WorkoutDayExercise> workoutDayExercises) {
        List<DayExerciseDto> dayExerciseDtos = new ArrayList<>();
        int i;

        for (i = 0; i < workoutDayExercises.size(); i++) {
            DayExerciseDto dayExerciseDto = new DayExerciseDto();
            dayExerciseDto.setExercise(workoutDayExercises.get(i).getExercise().getName());
            dayExerciseDto.setSetDtos(toSetDto(workoutDayExercises.get(i).getWorkoutDaySets()));
            dayExerciseDtos.add(dayExerciseDto);
        }
        return dayExerciseDtos;
    }

    private List<SetDto> toSetDto(List<WorkoutDaySet> workoutDaySets) {
        List<SetDto> setDtoS = new ArrayList<>();
        int i;

        for (i = 0; i < workoutDaySets.size(); i++) {
            SetDto setDto = new SetDto();

            setDto.setKgs(workoutDaySets.get(i).getKgs());
            setDto.setReps(workoutDaySets.get(i).getReps());
            setDto.setFinished(false);
            setDto.setNotes("");
            setDto.setTypeOfSet(workoutDaySets.get(i).getTypeOfSet());

            setDtoS.add(setDto);
        }

        return setDtoS;
    }
}
