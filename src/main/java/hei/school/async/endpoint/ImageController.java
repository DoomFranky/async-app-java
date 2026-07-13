package hei.school.async.endpoint;

import hei.school.async.dto.ImageDTO;
import hei.school.async.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ImageController {
    private ImageService imageService;

    @PostMapping(value = "/black-and-white", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postAImage (@RequestBody ImageDTO imageAndEmail) {
        try{
            byte[] bwImageBytes = imageService.processAndStoreImage(imageAndEmail);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bwImageBytes);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
