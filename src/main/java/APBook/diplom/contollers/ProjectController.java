package APBook.diplom.contollers;

import APBook.diplom.models.Project;
import APBook.diplom.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping()
    public List<Project> show(){
        return projectService.getProjects();
    }

}
