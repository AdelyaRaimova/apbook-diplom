package APBook.diplom.contollers;

import APBook.diplom.models.Project;
import APBook.diplom.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public void addProject(@RequestBody Project project){
        projectService.addProject(project);
    }


}
