package APBook.diplom.contoller;

import APBook.diplom.models.Project;
import APBook.diplom.models.User;
import APBook.diplom.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/users")
@Api(tags = "Пользователи")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> showAll(){
        return userService.showAll();
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user){
        try {
            User addedUser = userService.add(user);
            return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
        } catch (Exception exception) {
            log.error("Не удалось добавить пользователя", exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody User user){
        try {
            if (user.getId().equals(id)){
                User updatedUser = userService.update(id, user);
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            } else {
                log.warn("Номера не совпадают");
                return new ResponseEntity<>("Номера не совпадают", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            log.error("Не удалось обновить пользователя", exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try{
            userService.delete(id);
            return new ResponseEntity<>("Пользователь удален", HttpStatus.OK);
        }
        catch (EmptyResultDataAccessException exception){
            return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND);
        }
    }
}
