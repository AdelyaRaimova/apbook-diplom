package APBook.diplom.contollers;

import APBook.diplom.models.Project;
import APBook.diplom.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/project")
@Api(tags = "Проекты", description = "Контроллер для управления проектами")
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
    public Project show(@PathVariable long id){
        return projectService.get(id);
    }

    @PostMapping
    @ApiOperation(value = "Добавление проекта")
    public void add(@RequestBody Project project){
        projectService.add(project);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Изменение проекта")
    public void update(@PathVariable long id, @RequestBody Project project){
        projectService.update(id, project);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="Удаление проекта")
    public void delete(@PathVariable long id){
        projectService.delete(id);
    }
}
