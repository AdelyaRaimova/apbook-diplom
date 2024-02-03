package APBook.diplom.mapper;

import APBook.diplom.dto.ProjectDto;
import APBook.diplom.models.Project;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toEntity(ProjectDto projectDto);
    ProjectDto toDto(Project project);
}
