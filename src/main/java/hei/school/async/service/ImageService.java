package hei.school.async.service;

import hei.school.async.dto.ImageDTO;
import hei.school.async.entity.ImageEntity;
import hei.school.async.repository.ImageRepository;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageService {
  private ImageRepository imageRepository;

  public String processAndConvertToBase64(ImageDTO imageAndEmail) throws IOException {
    if (imageAndEmail.getFile().isEmpty()) {
      throw new IllegalArgumentException("Empty file");
    }

    BufferedImage originalImage = ImageIO.read(imageAndEmail.getFile().getInputStream());

    BufferedImage bwImage =
        new BufferedImage(
            originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

    bwImage.getGraphics().drawImage(originalImage, 0, 0, null);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bwImage, "jpg", baos);
    byte[] imageBytes = baos.toByteArray();

    bwImage.flush();
    originalImage.flush();

    saveImageNameAndEmailSender(imageAndEmail);
    return Base64.getEncoder().encodeToString(imageBytes);
  }

  public void saveImageNameAndEmailSender(ImageDTO imageDTO) {
    imageRepository.save(
        new ImageEntity(
            UUID.randomUUID().toString(),
            imageDTO.getFile().getName(),
            imageDTO.getEmail(),
            Instant.now()));
  }

  public List<ImageEntity> findAllExistingImage() {
    return imageRepository.findAll();
  }
}
