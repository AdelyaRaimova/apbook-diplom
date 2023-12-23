package APBook.diplom.contollers;

import APBook.diplom.models.Project;
import APBook.diplom.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public ResponseEntity<Project> show(@PathVariable long id){
        try {
            return new ResponseEntity(projectService.get(id), HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    @ApiOperation(value = "Добавление проекта")
    public ResponseEntity<String> add(@RequestBody Project project){
        projectService.add(project);
        String createdObject = "Проект успешно создан";
        return new ResponseEntity<>(createdObject, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Изменение проекта")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody Project project){
        if(project.getId().equals(id)){
            projectService.update(id, project);
            return new ResponseEntity<>("Проект изменен", HttpStatus.OK);
        }
        return new ResponseEntity<>("Номера не совпадают", HttpStatus.BAD_REQUEST);
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
