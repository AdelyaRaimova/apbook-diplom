package APBook.diplom.service;

import APBook.diplom.models.Photo;
import APBook.diplom.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {
    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo show(long id){
        return photoRepository.findById(id).orElse(null);
    }

    public void add(Photo photo){
        photoRepository.save(photo);
    }


}
