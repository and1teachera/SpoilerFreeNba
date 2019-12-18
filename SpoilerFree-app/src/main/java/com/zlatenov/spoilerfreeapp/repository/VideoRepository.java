package com.zlatenov.spoilerfreeapp.repository;

import com.zlatenov.spoilerfreeapp.model.entity.Game;
import com.zlatenov.spoilerfreeapp.model.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Angel Zlatenov
 */

public interface VideoRepository extends JpaRepository<Video, String> {

    Video findByVideoId(String videoId);

    List<Video> findByGame(Game game);

    void deleteByVideoId(String videoId);

    List<Video> findByDate(Date date);

    List<Video> findByGameIn(List<Game> games);
}
