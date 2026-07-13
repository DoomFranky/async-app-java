package hei.school.async.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private MultipartFile file;
    private String email;
}
