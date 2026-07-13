package hei.school.async.repository;

import hei.school.async.entity.ImageEntity;
import java.awt.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, String> {}
