package fi.jukka.planner531.controller;

import fi.jukka.planner531.config.Constants;
import fi.jukka.planner531.dto.LoginDto;
import fi.jukka.planner531.dto.LoginGetDto;
import fi.jukka.planner531.exception.AlreadyExistException;
import fi.jukka.planner531.exception.BadRequestException;
import fi.jukka.planner531.exception.NotFoundException;
import fi.jukka.planner531.model.*;
import fi.jukka.planner531.repository.*;
import fi.jukka.planner531.service.JwtUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/login/admin")
public class LoginMaintenanceController {

    private Throwable cause(String field) {
        return new Exception(field);
    }

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private final JwtUserDetailsService jwtUserDetailsService;
    private final PasswordEncoder bcryptEncoder;
    private final LoginRepository loginRepository;
    private final MainExerciseHeaderRepository mainExerciseHeaderRepository;
    private final StartingDetailsRepository startingDetailsRepository;
    private final WorkoutDayPlanRepository workoutDayPlanRepository;

    @Autowired
    public LoginMaintenanceController(JwtUserDetailsService jwtUserDetailsService,
                                      PasswordEncoder bcryptEncoder,
                                      LoginRepository loginRepository,
                                      MainExerciseHeaderRepository mainExerciseHeaderRepository,
                                      StartingDetailsRepository startingDetailsRepository,
                                      WorkoutDayPlanRepository workoutDayPlanRepository) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.bcryptEncoder = bcryptEncoder;
        this.loginRepository = loginRepository;
        this.mainExerciseHeaderRepository = mainExerciseHeaderRepository;
        this.startingDetailsRepository = startingDetailsRepository;
        this.workoutDayPlanRepository = workoutDayPlanRepository;
    }

    @PostMapping("/register")
    public LoginGetDto register(@RequestBody LoginDto loginDTO) {
        checkLoginData(loginDTO);

        return convertToDTO(jwtUserDetailsService.save(loginDTO));
    }

    @PutMapping("/register/{id}")
    public LoginGetDto registerUpdate(@RequestBody LoginDto loginDTO, @PathVariable Long id) {

        Login login = loginRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Login not found with id " + id));

        if (!loginDTO.getLoginName().isEmpty()) {
            Login newLogin = loginRepository.findFirstByLoginName(loginDTO.getLoginName());

            if (newLogin != null && id != newLogin.getId()) {
                throw new AlreadyExistException("Login already exists: " + newLogin.getLoginName());
            }

            if (!login.getLoginName().equals(loginDTO.getLoginName())) {
                Date changed = new Date();
                login.setLoginName(loginDTO.getLoginName());
                login.setChanged(new Timestamp(changed.getTime()));
            }
        }

        if (loginDTO.getPassword() != null) {
            if (checkPasswordLength(loginDTO.getPassword())) {
                throw new BadRequestException("Incorrect password", cause("password"));
            }

            Date changed = new Date();
            login.setPassword(bcryptEncoder.encode(loginDTO.getPassword()));
            login.setPasswordChanged(new Timestamp(changed.getTime()));
        }

        return convertToDTO(loginRepository.save(login));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteOne(@PathVariable Long id) {
        Optional<Login> login = loginRepository.findById(id);

        if (login.isPresent()) {
            Login log = login.get();

            MainExerciseHeader mainExerciseHeader = log.getMainExerciseHeader();
            StartingDetails startingDetails = log.getStartingDetails();
            WorkoutDayPlan workoutDayPlan = log.getWorkoutDayPlan();

            log.setMainExerciseHeader(null);
            log.setStartingDetails(null);
            log.setWorkoutDayPlan(null);
            log.setRoles(null);

            if (mainExerciseHeader != null) {
                mainExerciseHeaderRepository.deleteById(mainExerciseHeader.getId());
            }
            if (startingDetails != null) {
                startingDetailsRepository.deleteById(startingDetails.getId());
            }
            if (workoutDayPlan != null) {
                workoutDayPlanRepository.deleteById(workoutDayPlan.getId());
            }
            loginRepository.deleteById(id);
        } else {
            throw new NotFoundException("Login not found with id " + id);
        }

        return ResponseEntity.noContent().build();
    }

    private void checkLoginData(LoginDto loginDTO) {
        if (loginDTO.getLoginName().isEmpty()) {
            throw new BadRequestException("Username can not be blank", cause("loginName"));
        }
        if (checkPasswordLength(loginDTO.getPassword())) {
            throw new BadRequestException("Incorrect password", cause("password"));
        }
        Login newLogin = loginRepository.findFirstByLoginName(loginDTO.getLoginName());

        if (newLogin != null) {
            throw new AlreadyExistException("Login already exists: " + newLogin.getLoginName());
        }
    }

    private static boolean checkPasswordLength(String password) {
        return StringUtils.isEmpty(password) ||
                password.length() < Constants.PASSWORD_MIN_LENGTH ||
                password.length() > Constants.PASSWORD_MAX_LENGTH;
    }

    private LoginGetDto convertToDTO(Login login) {
        return modelMapper.map(login, LoginGetDto.class);
    }
}
