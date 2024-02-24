package APBook.diplom.contoller;

import APBook.diplom.models.Post;
import APBook.diplom.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/post")
@Api(tags = "Посты")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получение поста")
    public ResponseEntity<?> show(@PathVariable Long id){
        try {
            Post post = postService.show(id);
            if (post != null){
                return new ResponseEntity<>(post, HttpStatus.OK);
            }
            else{
                throw new Exception();
            }
        } catch (Exception exception){
            log.error("Не удалось получить пост с идентификатором: {}", id, exception );
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    @ApiOperation(value = "Получение всех постов пользователя")
    public ResponseEntity<?> showNews(@RequestParam Long user){
        try {
            List<Post> news = postService.showNews(user);
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (Exception exception){
            log.error("Не удалось получить посты у пользователя: {}", user, exception );
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление поста")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try{
            postService.delete(id);
            return new ResponseEntity<>("Пост удален", HttpStatus.OK);
        } catch (EmptyResultDataAccessException ex){
            return new ResponseEntity<>("Пост не найден", HttpStatus.NOT_FOUND);
        }
    }
}
