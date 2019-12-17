package com.zlatenov.spoilerfreeapp.repository;

import com.zlatenov.spoilerfreeapp.model.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Angel Zlatenov
 */

public interface VideoRepository extends JpaRepository<Video, String> {
}
