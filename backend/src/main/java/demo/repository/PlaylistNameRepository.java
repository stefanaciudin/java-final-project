package demo.repository;

import demo.entity.PlaylistName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PlaylistNameRepository - JPA repository for PlaylistName entity
 */
@Repository
public interface PlaylistNameRepository extends JpaRepository<PlaylistName, Long> {
    List<PlaylistName> findAll();
}

