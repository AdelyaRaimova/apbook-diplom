package APBook.diplom.service;

import APBook.diplom.models.*;
import APBook.diplom.repository.UserCategoryRepository;
import APBook.diplom.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserCategoryRepository userCategoryRepository;

    public UserService(UserRepository userRepository, UserCategoryRepository userCategoryRepository) {
        this.userRepository = userRepository;
        this.userCategoryRepository = userCategoryRepository;
    }

    public User show(long id){
        return userRepository.findById(id).orElse(null);
    }

    public List<User> showAll(){
        return userRepository.findAll();
    }

    public User auth(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<Project> showSubscriptions(Long id){
        List<Project> projectList = new ArrayList<>();
        User user = userRepository.findById(id).orElse(null);
        Set<UserProject> list = user.getSubscriptions();
        Set<Project> projects = list.stream()
                .map(UserProject::getProject)
                .collect(Collectors.toSet());
        projectList.addAll(projects);
        return projectList;
    }

    public User add(User user){
        User user1 = userRepository.findByEmail(user.getEmail()).orElse(null);
        if(user1 != null){
            return null;
        }
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

    public User updateCategories(long id, Set<Category> newCategories){
        User uUser = userRepository.findById(id).orElse(null);
        if(uUser == null){
            return null;
        }
        Set<Category> oldCategories = userCategoryRepository.findUserCategoriesByUserId(id)
                .stream()
                .map(UserCategory::getCategory)
                .collect(Collectors.toSet());
        if(newCategories.equals(oldCategories)){
            return uUser;
        }
        Set<Category> thirdCategories = new HashSet<>(newCategories);
        newCategories.removeAll(oldCategories);
        oldCategories.removeAll(thirdCategories);
        newCategories.forEach(category -> userCategoryRepository.save(new UserCategory(uUser, category)));
        if(oldCategories.isEmpty()){
            return uUser;
        }
        for(Category category: oldCategories){
            UserCategory userCategory = userCategoryRepository.findUserCategoryByUserIdAndCategoryId(id, category.getId());
            if(userCategory == null){
                continue;
            }
            userCategoryRepository.delete(userCategory);
        }
        return uUser;
    }

    public void delete(long id){
        userRepository.deleteById(id);
    }
}
