package APBook.diplom.contoller;

import APBook.diplom.models.Photo;
import APBook.diplom.service.PhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/photo")
@Api(tags = "Фотографии")
public class PhotoController {
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получение фотографии")
    public ResponseEntity<?> show(@PathVariable Long id){
        try {
            Photo photo = photoService.show(id);
            if (photo != null){
                return new ResponseEntity<>(photo, HttpStatus.OK);
            }
            else{
                throw new Exception();
            }
        } catch (Exception exception){
            log.error("Не удалось получить фото с идентификатором: {}", id, exception );
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление фотографии")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try{
            photoService.delete(id);
            return new ResponseEntity<>("Фото удалено", HttpStatus.OK);
        } catch (EmptyResultDataAccessException ex){
            return new ResponseEntity<>("Фото не найдено", HttpStatus.NOT_FOUND);
        }
    }
}
