package APBook.diplom.contollers;

import APBook.diplom.models.Project;
import APBook.diplom.service.ProjectService;
import io.swagger.annotations.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping()
    public List<Project> showAll(){
        return projectService.getProjects();
    }

    @GetMapping("/{id}")
    public Project show(@PathVariable long id){
        return projectService.getProject(id);
    }

    @PostMapping
    public void add(@RequestBody Project project){
        projectService.addProject(project);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody Project project){
        projectService.updateProject(id, project);
    }
}
