package pe.joedayz.backend.service;

import org.springframework.stereotype.Service;
import pe.joedayz.backend.exceptions.UserNotFoundException;
import pe.joedayz.backend.model.User;
import pe.joedayz.backend.repo.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getUsers(){
        return repo.findAll();
    }

    public User getUser(Long id){
        return repo.findById(id).orElseThrow(()->
                new UserNotFoundException("User by id "+id+"was not found"));
    }
}
