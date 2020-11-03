package fi.jukka.planner531.service;

import java.util.*;

import fi.jukka.planner531.exception.NotFoundException;
import fi.jukka.planner531.model.MainExerciseHeader;
import fi.jukka.planner531.repository.MainExerciseHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fi.jukka.planner531.model.Login;
import fi.jukka.planner531.dto.LoginDto;
import fi.jukka.planner531.model.Role;
import fi.jukka.planner531.repository.LoginRepository;
import fi.jukka.planner531.repository.RoleRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final LoginRepository loginRepository;
    private final RoleRepository roleRepository;
    private final MainExerciseHeaderRepository mainExerciseHeaderRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    public JwtUserDetailsService(LoginRepository loginRepository,
                                 RoleRepository roleRepository,
                                 MainExerciseHeaderRepository mainExerciseHeaderRepository) {
        this.loginRepository = loginRepository;
        this.roleRepository = roleRepository;
        this.mainExerciseHeaderRepository = mainExerciseHeaderRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login login = loginRepository.findByLoginName(username);
        if (login == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Set<GrantedAuthority> userAuthorities = createGrantedAuthorities(login.getId());
        return new org.springframework.security.core.userdetails.User(login.getLoginName(), login.getPassword(),
                new ArrayList<>(userAuthorities));
    }

    public Set<GrantedAuthority> createGrantedAuthorities(Long loginId) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        Login login = loginRepository.findById(loginId)
                .orElseThrow(() -> new NotFoundException("Login not found with " + loginId));

        login.getRoles().forEach((role) -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return grantedAuthorities;
    }

    public Login save(LoginDto login) {
        if (roleRepository.findFirstByName("ADMIN") == null) {
            Role role = new Role();
            role.setName("ADMIN");
            roleRepository.save(role);
        }
        if (roleRepository.findFirstByName("USER") == null) {
            Role role = new Role();
            role.setName("USER");
            roleRepository.save(role);
        }

        MainExerciseHeader newMainExerciseHeader = new MainExerciseHeader();
        mainExerciseHeaderRepository.save(newMainExerciseHeader);

        Login newLogin = new Login();
        Role role = roleRepository.findFirstByName("USER");
        if(login.getLoginName().equals("admin")){
            role = roleRepository.findFirstByName("ADMIN");
        }
        newLogin.getRoles().add(role);
        newLogin.setLoginName(login.getLoginName().toLowerCase());
        newLogin.setPassword(bcryptEncoder.encode(login.getPassword()));
        newLogin.setMainExerciseHeader(newMainExerciseHeader);
        return loginRepository.save(newLogin);
    }
}
