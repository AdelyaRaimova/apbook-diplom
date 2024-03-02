package APBook.diplom.service;

import APBook.diplom.models.Photo;
import APBook.diplom.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PhotoService {
    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo show(long id){
        return photoRepository.findById(id).orElse(null);
    }

    public Photo add(Photo photo){
        return photoRepository.save(photo);
    }

    public Photo update(Long id, Photo photo){
        Photo photo1 = photoRepository.findById(id).orElse(null);
        photo.setId(photo1.getId());
        photo.setLink(photo1.getLink());
        photo.setProject(photo1.getProject());
        photo.setPost(photo1.getPost());
        return photoRepository.save(photo);
    }

    public void delete(Long id){
        photoRepository.deleteById(id);
    }

}
