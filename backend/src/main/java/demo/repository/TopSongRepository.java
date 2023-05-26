package demo.repository;

import demo.entity.TopSong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * TopSongRepository - JPA repository for TopSong entity
 */
public interface TopSongRepository extends JpaRepository<TopSong, Long> {
    List<TopSong> findAll();

}
