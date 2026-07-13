package hei.school.async.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String fileName;

  private String email;

  private Instant createdAt;
}
