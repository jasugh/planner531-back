package fi.jukka.planner531.controller;

import fi.jukka.planner531.config.Constants;
import fi.jukka.planner531.dto.LoginDto;
import fi.jukka.planner531.dto.LoginGetDto;
import fi.jukka.planner531.exception.AlreadyExistException;
import fi.jukka.planner531.exception.BadRequestException;
import fi.jukka.planner531.exception.NotFoundException;
import fi.jukka.planner531.model.Login;
import fi.jukka.planner531.repository.LoginRepository;

import fi.jukka.planner531.config.JwtTokenUtil;
import fi.jukka.planner531.service.JwtRequest;
import fi.jukka.planner531.service.JwtUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private Throwable cause(String field) {
        return new Exception(field);
    }

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private AuthenticationManager authenticationManager;

    private final LoginRepository loginRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public LoginController(LoginRepository loginRepository,
                           JwtTokenUtil jwtTokenUtil,
                           JwtUserDetailsService jwtUserDetailsService) {
        this.loginRepository = loginRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @GetMapping("")
    public List<LoginGetDto> findAll() {
        List<Login> logins = loginRepository.findAll();

        return logins.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/id")
    public LoginGetDto getOne(@PathVariable Long id) {
        return convertToDTO(loginRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Login not found with id " + id)));
    }

    @GetMapping("/{name}/name")
    public LoginGetDto getByLoginName(@PathVariable String name) {
        Login login = loginRepository.findFirstByLoginName(name);
        if (login == null) {
            throw new NotFoundException("Login data not found with name " + name);
        }
        return convertToDTO(login);
    }

    @PostMapping("/register")
    public LoginGetDto register(@RequestBody LoginDto loginDTO) {
        if (loginDTO.getLoginName().isEmpty()) {
            throw new BadRequestException("Username can not be blank", cause("loginName"));
        }
        if (!checkPasswordLength(loginDTO.getPassword())) {
            throw new BadRequestException("Incorrect password", cause("password"));
        }

        Login newLogin = loginRepository.findFirstByLoginName(loginDTO.getLoginName());
        if (newLogin == null) {
            return convertToDTO(jwtUserDetailsService.save(loginDTO));
        } else {
            throw new AlreadyExistException("Login already exists: " + newLogin.getLoginName());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateAndCreateToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        try {
            authenticate(jwtRequest.getLoginName(), jwtRequest.getPassword());
        } catch (Exception exception) {
            throw new BadRequestException("Bad credentials", cause("credentials"));
        }

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtRequest.getLoginName());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(token);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteOne(@PathVariable Long id) {
        getOne(id);
        loginRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void authenticate(String loginName, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginName, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
                password.length() >= Constants.PASSWORD_MIN_LENGTH &&
                password.length() <= Constants.PASSWORD_MAX_LENGTH;
    }

    private LoginGetDto convertToDTO(Login login) {
        return modelMapper.map(login, LoginGetDto.class);
    }
}
