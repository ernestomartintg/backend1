package pe.joedayz.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.joedayz.backend.model.User;
import pe.joedayz.backend.repo.UserRepository;

import java.util.ArrayList;

@Service
public class JwtUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder bcryptEnconder;


    // CONFIGURACION PROPIA DEL FRAMEWORK

    // 1 Necesito un metodo para dado un username, obtener un UserDetail
    // Propio de Spring Security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=repo.findByUsername(username);
        //transformamos user a UserDetails
        if(user==null){
            throw new UsernameNotFoundException("User not found with username: "+username);
        }

        return new org.springframework.security.core.userdetails.User(user.getUsermame(),
                user.getPassword(),new ArrayList<>());// sin ROLES AUN
    }

    public User save(User user) {
        User newUser=new User(user.getUsermame(), bcryptEnconder.encode(user.getPassword()),
                user.getEmail(),user.getName(),user.getAddress(),user.getPhone());
        return repo.save(user);
    }




}
