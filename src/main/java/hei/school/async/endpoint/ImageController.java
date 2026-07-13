package hei.school.async.endpoint;

import hei.school.async.dto.ImageDTO;
import hei.school.async.endpoint.event.EventProducer;
import hei.school.async.endpoint.event.model.SendEmailRequested;
import hei.school.async.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ImageController {
    private final EventProducer<SendEmailRequested> eventProducer;

    @PostMapping(value = "/black-and-white", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SneakyThrows
    public ResponseEntity<?> postAImage (@RequestBody ImageDTO imageAndEmail) {
        try{
            var event = SendEmailRequested.builder().imageDTO(imageAndEmail).build();
            eventProducer.accept(List.of(event));

            return ResponseEntity.ok().body("The mail as been send");

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
