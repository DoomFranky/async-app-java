package hei.school.async.service.event;

import hei.school.async.endpoint.event.model.SendEmailRequested;
import hei.school.async.mail.Email;
import hei.school.async.mail.Mailer;
import hei.school.async.service.ImageService;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendEmailRequestedService implements Consumer<SendEmailRequested> {
  private final Mailer mailer;
  private final ImageService imageService;

  @SneakyThrows
  @Override
  public void accept(SendEmailRequested sendEmailRequested) {
    String base64Image = imageService.processAndConvertToBase64(sendEmailRequested.getImageDTO());

    String imageDataUrl = "data:image/jpeg;base64," + base64Image;

    InternetAddress recipientAddress =
        new InternetAddress(sendEmailRequested.getImageDTO().getEmail());
    String htmlResponse =
        """
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Votre Image est prête</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; margin-top: 50px; background-color: #f4f4f9; }
        .container { background: white; padding: 30px; display: inline-block; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }
        .btn-download { display: inline-block; padding: 12px 24px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; font-weight: bold; margin-top: 20px; }
        .btn-download:hover { background-color: #0056b3; }
        .preview { max-width: 300px; margin-top: 20px; border: 1px solid #ddd; border-radius: 4px; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Bonjour, %s !</h2>
        <p>Votre image a bien été transformée en noir et blanc.</p>

        <div>
            <p>Aperçu :</p>
            <img src="%s" class="preview" alt="Aperçu Noir et Blanc"/>
        </div>

        <a href="%s" download="image-noir-et-blanc.jpg" class="btn-download">
            Télécharger l'image
        </a>
    </div>
</body>
</html>
"""
            .formatted(sendEmailRequested.getImageDTO().getEmail(), imageDataUrl, imageDataUrl);
    mailer.accept(
        new Email(
            recipientAddress,
            List.of(),
            List.of(),
            "Image in black and white",
            htmlResponse,
            List.of()));
  }
}
