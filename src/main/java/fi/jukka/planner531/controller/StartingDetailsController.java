package fi.jukka.planner531.controller;

import fi.jukka.planner531.dto.StartingDetailsDto;
import fi.jukka.planner531.exception.BadRequestException;
import fi.jukka.planner531.exception.NotFoundException;
import fi.jukka.planner531.model.Login;
import fi.jukka.planner531.model.StartingDetails;
import fi.jukka.planner531.repository.LoginRepository;
import fi.jukka.planner531.repository.StartingDetailsRepository;
import fi.jukka.planner531.repository.WorkoutDayPlanRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/starting")
public class StartingDetailsController {

    private final StartingDetailsRepository startingDetailsRepository;
    private final WorkoutDayPlanRepository workoutDayPlanRepository;
    private final LoginRepository loginRepository;

    private Throwable cause(String field) {
        return new Exception(field);
    }

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public StartingDetailsController(StartingDetailsRepository startingDetailsRepository,
                                     WorkoutDayPlanRepository workoutDayPlanRepository,
                                     LoginRepository loginRepository) {
        this.startingDetailsRepository = startingDetailsRepository;
        this.workoutDayPlanRepository = workoutDayPlanRepository;
        this.loginRepository = loginRepository;
    }

    @GetMapping("")
    public List<StartingDetailsDto> getAllStartingDetails() {
        List<StartingDetails> startingDetails = startingDetailsRepository.findAll();
        return startingDetails.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}/id")
    public StartingDetailsDto getStartingDetailsById(@PathVariable Long id) {
        return convertToDTO(startingDetailsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Starting Details not found with id " + id)));
    }

    @GetMapping("/{loginId}/user")
    public StartingDetailsDto getOneByLoginName(@PathVariable Long loginId) {
        Login login = loginRepository.findById(loginId)
                .orElseThrow(() -> new NotFoundException("Login data not found with id " + loginId));

        if (login.getStartingDetails() == null) {
            throw new NotFoundException("Starting Details not found with user id  " + loginId);
        }

        return convertToDTO(startingDetailsRepository.findById(login.getStartingDetails().getId())
                .orElseThrow(() ->
                        new NotFoundException("Starting Details not found with user id " + loginId)));
    }

    @PostMapping("")
    @Transactional
    public StartingDetailsDto saveStartingDetails(@RequestBody StartingDetailsDto startingDetailsDTO) {
        // Get login information
        Login login = loginRepository.findById(startingDetailsDTO.getLoginId())
                .orElseThrow(() -> new NotFoundException("Login data not found with id " + startingDetailsDTO.getLoginId()));

        if (startingDetailsDTO.getPress1rm() == 0) {
            throw new BadRequestException("1RM is 0 ", cause("pressKg"));
        }
        if (startingDetailsDTO.getDeadLift1rm() == 0) {
            throw new BadRequestException("1RM is 0 ", cause("deadLiftKg"));
        }
        if (startingDetailsDTO.getBenchPress1rm() == 0) {
            throw new BadRequestException("1RM is 0 ", cause("benchPressKg"));
        }
        if (startingDetailsDTO.getSquat1rm() == 0) {
            throw new BadRequestException("1RM is 0 ", cause("squatKg"));
        }
//        if (startingDetailsDTO.getWeightRounding() == 0) {
//            throw new BadRequestException("Weight rounding can not be 0 ", cause("weightRounding"));
//        }
        if (startingDetailsDTO.getPressIncrement() == 0) {
            throw new BadRequestException("Press increment can not be 0 ", cause("pressIncrement"));
        }
        if (startingDetailsDTO.getDeadLiftIncrement() == 0) {
            throw new BadRequestException("Dead lift increment can not be 0 ", cause("deadLiftIncrement"));
        }
        if (startingDetailsDTO.getBenchIncrement() == 0) {
            throw new BadRequestException("Bench press increment can not be 0 ", cause("benchIncrement"));
        }
        if (startingDetailsDTO.getSquatIncrement() == 0) {
            throw new BadRequestException("Squat increment can not be 0 ", cause("squatIncrement"));
        }
        if (startingDetailsDTO.getTrainingMax() == 0) {
            throw new BadRequestException("Training max can not be 0 ", cause("trainingMax"));
        }
        if (startingDetailsDTO.getNumberOfCycles() == 0) {
            throw new BadRequestException("Number of cycles can not be 0 ", cause("numberOfCycles"));
        }
        if (startingDetailsDTO.getW1percentages().size() == 0) {
            throw new BadRequestException("Week 1 percentages can not be 0 ", cause("w1percentages"));
        }
        if (startingDetailsDTO.getW2percentages().size() == 0) {
            throw new BadRequestException("Week 2 percentages can not be 0 ", cause("w2percentages"));
        }
        if (startingDetailsDTO.getW3percentages().size() == 0) {
            throw new BadRequestException("Week 3 percentages can not be 0 ", cause("w3percentages"));
        }
        if (startingDetailsDTO.getW4percentages().size() == 0) {
            throw new BadRequestException("Week 4 percentages can not be 0 ", cause("w4percentages"));
        }

        // Delete the old one if exists (startingDetails and workoutPlan)
        if (login.getStartingDetails() != null) {
            deleteOne(login.getStartingDetails().getId());
        }
        StartingDetails newStartingDetails = new StartingDetails();

        newStartingDetails.setStartingDate(startingDetailsDTO.getStartingDate());
        newStartingDetails.setPressKg(startingDetailsDTO.getPressKg());
        newStartingDetails.setPressReps(startingDetailsDTO.getPressReps());
        newStartingDetails.setPress1rm(startingDetailsDTO.getPress1rm());
        newStartingDetails.setDeadLiftKg(startingDetailsDTO.getDeadLiftKg());
        newStartingDetails.setDeadLiftReps(startingDetailsDTO.getDeadLiftReps());
        newStartingDetails.setDeadLift1rm(startingDetailsDTO.getDeadLift1rm());
        newStartingDetails.setBenchPressKg(startingDetailsDTO.getBenchPressKg());
        newStartingDetails.setBenchPressReps(startingDetailsDTO.getBenchPressReps());
        newStartingDetails.setBenchPress1rm(startingDetailsDTO.getBenchPress1rm());
        newStartingDetails.setSquatKg(startingDetailsDTO.getSquatKg());
        newStartingDetails.setSquatReps(startingDetailsDTO.getSquatReps());
        newStartingDetails.setSquat1rm(startingDetailsDTO.getSquat1rm());
        // TODO implement different weight roundings to calculations
//        newStartingDetails.setWeightRounding(startingDetailsDTO.getWeightRounding());
        newStartingDetails.setPressIncrement(startingDetailsDTO.getPressIncrement());
        newStartingDetails.setDeadLiftIncrement(startingDetailsDTO.getDeadLiftIncrement());
        newStartingDetails.setBenchIncrement(startingDetailsDTO.getBenchIncrement());
        newStartingDetails.setSquatIncrement(startingDetailsDTO.getSquatIncrement());
        newStartingDetails.setTrainingMax(startingDetailsDTO.getTrainingMax());
        newStartingDetails.setNumberOfCycles(startingDetailsDTO.getNumberOfCycles());
        // TODO implement selected percentages to calculations
        newStartingDetails.setW1percentages(startingDetailsDTO.getW1percentages());
        newStartingDetails.setW2percentages(startingDetailsDTO.getW2percentages());
        newStartingDetails.setW3percentages(startingDetailsDTO.getW3percentages());
        newStartingDetails.setW4percentages(startingDetailsDTO.getW4percentages());

        startingDetailsRepository.save(newStartingDetails);

        newStartingDetails.setLogin(login);
        login.setStartingDetails(newStartingDetails);


        return convertToDTO(newStartingDetails);
    }

    @DeleteMapping("/{id}")
    @Transactional
    ResponseEntity<?> deleteOne(@PathVariable Long id) {
        StartingDetails startingDetails = startingDetailsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Starting details not found with " + id));

        Long workoutDayPlanId = null;

        if (startingDetails.getLogin() != null) {
            Login login = loginRepository.findById(startingDetails.getLogin().getId())
                    .orElseThrow(() -> new NotFoundException("Login not found with " + startingDetails.getLogin().getId()));

            if (login.getWorkoutDayPlan() != null) {
                workoutDayPlanId = login.getWorkoutDayPlan().getId();
            }
            login.setStartingDetails(null);
            login.setWorkoutDayPlan(null);
            loginRepository.save(login);
        }

        if (workoutDayPlanId != null) {
            workoutDayPlanRepository.deleteById(workoutDayPlanId);
        }

        startingDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private StartingDetailsDto convertToDTO(StartingDetails startingDetails) {
        return modelMapper.map(startingDetails, StartingDetailsDto.class);
    }
}
