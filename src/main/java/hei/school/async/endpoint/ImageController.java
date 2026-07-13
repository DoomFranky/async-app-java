package hei.school.async.endpoint;

import hei.school.async.dto.ImageDTO;
import hei.school.async.endpoint.event.EventProducer;
import hei.school.async.endpoint.event.model.SendEmailRequested;
import hei.school.async.entity.ImageEntity;
import hei.school.async.service.ImageService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ImageController {
  private final EventProducer<SendEmailRequested> eventProducer;
  private final ImageService imageService;

  @PostMapping(value = "/black-and-white", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @SneakyThrows
  public String postAImage(@RequestBody ImageDTO imageAndEmail) {
    try {
      var event = SendEmailRequested.builder().imageDTO(imageAndEmail).build();
      eventProducer.accept(List.of(event));

      return "The mail as been send";

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping("/findAll")
  public List<ImageEntity> getAllExistingImage() {
    return imageService.findAllExistingImage();
  }
}
