package APBook.diplom.service;

import APBook.diplom.models.*;
import APBook.diplom.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User show(long id){
        return userRepository.findById(id).orElse(null);
    }

    public List<User> showAll(){
        return userRepository.findAll();
    }

    public User auth(String email){
        return userRepository.findByEmail(email);
    }

    public List<Project> showSubscriptions(Long id){
        List<Project> projectList = new ArrayList<>();
        User user = userRepository.findById(id).orElse(null);
        Set<UserProject> list =  user.getSubscriptions();
        Set<Project> projects = list.stream()
                .map(UserProject::getProject)
                .collect(Collectors.toSet());
        projectList.addAll(projects);
        return projectList;
    }

    public User add(User user){
        return userRepository.save(user);
    }

    public User update(long id, User user){
        User uUser = userRepository.findById(id).orElse(null);
        if(uUser == null){
            return null;
        }
        uUser.setAge(user.getAge());
        uUser.setEmail(user.getEmail());
        uUser.setFirstName(user.getFirstName());
        uUser.setPassword(user.getPassword());
        uUser.setPhoto(user.getPhoto());
        uUser.setProjects(user.getProjects());
        uUser.setSecondName(user.getSecondName());
        userRepository.save(uUser);
        return uUser;
    }

    public User updateCategories(long id, Set<UserCategory> categories){
        User uUser = userRepository.findById(id).orElse(null);
        if(uUser == null){
            return null;
        }
        uUser.setSelectedCategories(categories);
        userRepository.save(uUser);
        return uUser;
    }

    public void delete(long id){
        userRepository.deleteById(id);
    }
}
