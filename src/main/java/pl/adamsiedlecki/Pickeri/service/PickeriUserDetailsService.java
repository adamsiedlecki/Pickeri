package pl.adamsiedlecki.Pickeri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.UserRepository;
import pl.adamsiedlecki.Pickeri.entity.User;
import pl.adamsiedlecki.Pickeri.pojos.MyUserPrincipal;
import pl.adamsiedlecki.Pickeri.entity.UserRole;

import java.util.List;

@Service
public class PickeriUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;


    @Autowired
    public PickeriUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
        //addUser("a","a",List.of(new UserRole("ADMIN")));
        //addUser("admin","pass",List.of(new UserRole("ADMIN")));
        //addUser("b","b",List.of(new UserRole("ADMIN")));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if(user==null){
            throw new UsernameNotFoundException(s);
        }
        return new MyUserPrincipal(user);
    }

    public void addUser(String name, String password, List<UserRole> roles){
        User user = new User(name,password,roles);
        userRepository.save(user);
    }

    public void changePassword(String username, String newPassword){
        User user = userRepository.findByUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.saveAndFlush(user);
    }

}
