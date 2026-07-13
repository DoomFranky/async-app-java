package hei.school.async.service;

import hei.school.async.dto.ImageDTO;
import hei.school.async.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@AllArgsConstructor
public class ImageService {
    private ImageRepository imageRepository;

    public byte[] processAndStoreImage(ImageDTO imageAndEmail) throws IOException {
        if (imageAndEmail.getFile().isEmpty()) {
            throw new IllegalArgumentException("Empty file");
        }

        BufferedImage originalImage = ImageIO.read(imageAndEmail.getFile().getInputStream());

        BufferedImage bwImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY
        );

        bwImage.getGraphics().drawImage(originalImage, 0, 0, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bwImage, "jpg", baos);

        bwImage.flush();
        originalImage.flush();

        return baos.toByteArray();
    }
}
