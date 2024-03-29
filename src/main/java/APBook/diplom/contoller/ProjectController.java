package APBook.diplom.contoller;

import APBook.diplom.dto.ProjectDto;
import APBook.diplom.mapper.ProjectMapper;
import APBook.diplom.models.Photo;
import APBook.diplom.models.Post;
import APBook.diplom.models.Project;
import APBook.diplom.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/api/project")
@Api(tags = "Проекты")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    public ProjectController(ProjectService projectService, ProjectMapper projectMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @GetMapping()
    @ApiOperation(value = "Получение всех проектов")
    public List<Project> showAll(){
        return projectService.getAll();
    }

    @GetMapping("/list")
    @ApiOperation(value = "Получение всех проектов для отображение в списке")
    public List<ProjectDto> showAllForList(@RequestParam Long id){
        List<Project> projects = projectService.getAllForNews(id);
        List<ProjectDto> projectDtos = projects.stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
        return projectDtos;
    }

    @GetMapping("/subscriptions")
    @ApiOperation(value = "Получение всех подписок для отображение в списке")
    public ResponseEntity<List<ProjectDto>> showAllSubscriptionsForList(@RequestParam Long id){
        try {
            List<Project> projects = projectService.getAllSubscriptions(id);
            if (projects == null){
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            List<ProjectDto> projectDtos = projects.stream()
                    .map(projectMapper::toDto)
                    .collect(Collectors.toList());
            return new ResponseEntity(projectDtos, HttpStatus.OK);
        } catch (Exception exception){
            log.error("Не удалось получить подписки у пользователя: {}", id, exception );
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "Получение одного проекта")
    public ResponseEntity<Project> show(@PathVariable Long id){
        try {
            return new ResponseEntity(projectService.get(id), HttpStatus.OK);
        } catch (Exception exception){
            log.error("Не удалось получить проект с идентификатором: {}", id, exception );
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/photo")
    @ApiOperation(value = "Получение всех фотографий проекта")
    public ResponseEntity<?> showAllPhotos(@ApiParam(value = "Номер проекта") @PathVariable Long id){
        try {
            List<Photo> photos = projectService.getAllPhotos(id);
            return new ResponseEntity<>(photos, HttpStatus.OK);
        } catch (Exception exception) {
            log.error("Не удалось получить фотографии для проекта с идентификатором: {}", id, exception);
            return new ResponseEntity<>("Не удалось получить фотографии. Подробности: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/post")
    @ApiOperation(value = "Получение всех постов проекта")
    public ResponseEntity<?> showAllPosts(@ApiParam(value = "Номер проекта") @PathVariable Long id){
        try {
            List<Post> posts = projectService.getAllPosts(id);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception exception) {
            log.error("Не удалось получить посты для проекта с идентификатором: {}", id, exception);
            return new ResponseEntity<>("Не удалось получить посты. Подробности: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @ApiOperation(value = "Добавление проекта")
    public ResponseEntity<Project> add(@RequestBody Project project, @RequestParam Long userId){
        try {
            Project addedProject = projectService.add(project, userId);
            return new ResponseEntity<>(addedProject, HttpStatus.CREATED);
        } catch (Exception exception) {
            log.error("Не удалось добавить проект", exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestParam Long userId, @RequestParam Long projectId){
        try {
            projectService.subscribe(userId, projectId);
            return new ResponseEntity<>("Пользователь подписался", HttpStatus.CREATED);
        } catch (RuntimeException exception){
            log.error("Пользователь уже подписан", exception);
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            log.error("Не удалось подписать пользователя", exception);
            return new ResponseEntity<>("Не удалось подписать пользователя", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/photo")
    @ApiOperation(value = "Добавление фотографии")
    public ResponseEntity<Photo> addPhoto(@PathVariable Long id, @RequestBody Photo photo){
        try{
            Photo addedPhoto = projectService.addPhoto(id, photo);
            return new ResponseEntity<>(addedPhoto, HttpStatus.CREATED);
        } catch (NullPointerException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            log.error("Не удалось добавить фотографию", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/post")
    @ApiOperation(value = "Добавление поста")
    public ResponseEntity<Post> addPost(@PathVariable Long id, @RequestBody Post post){
        try{
            Post addedPost = projectService.addPost(id, post);
            return new ResponseEntity<>(addedPost, HttpStatus.CREATED);
        } catch (NullPointerException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            log.error("Не удалось добавить пост", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Изменение проекта")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Project project){
        try {
            if (project.getId().equals(id)) {
                Project updatedProject = projectService.update(id, project);
                return new ResponseEntity<>(updatedProject, HttpStatus.OK);
            } else {
                log.warn("Номера не совпадают");
                return new ResponseEntity<>("Номера не совпадают", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            log.error("Не удалось обновить проект", exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="Удаление проекта")
    public ResponseEntity<String> delete(@PathVariable long id){
        try{
            projectService.delete(id);
            return new ResponseEntity<>("Проект удален", HttpStatus.OK);
        } catch (EmptyResultDataAccessException ex){
            return new ResponseEntity<>("Проект не найден", HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(@RequestParam Long userId, @RequestParam Long projectId){
        try {
            projectService.unsubscribe(userId, projectId);
            return new ResponseEntity<>("Пользователь отписался", HttpStatus.OK);
        } catch (RuntimeException exception){
            log.error("Пользователь не подписан", exception);
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            log.error("Не удалось отписать пользователя", exception);
            return new ResponseEntity<>("Не удалось отписать пользователя", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
