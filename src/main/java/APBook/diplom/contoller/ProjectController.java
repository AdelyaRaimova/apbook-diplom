package APBook.diplom.contoller;

import APBook.diplom.models.Photo;
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

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/api/project")
@Api(tags = "Проекты")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping()
    @ApiOperation(value = "Получение всех проектов")
    public List<Project> showAll(){
        return projectService.getAll();
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

    @PostMapping
    @ApiOperation(value = "Добавление проекта")
    public ResponseEntity<Project> add(@RequestBody Project project){
        try {
            Project addedProject = projectService.add(project);
            return new ResponseEntity<>(addedProject, HttpStatus.CREATED);
        } catch (Exception exception) {
            log.error("Не удалось добавить проект", exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
}
