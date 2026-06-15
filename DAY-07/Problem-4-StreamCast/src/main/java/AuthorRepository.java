package com.streamcast;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    // TREAT operator for polymorphic downcasting
    @Query("SELECT m FROM MediaAsset m " +
           "WHERE m.author.authorId = :authorId " +
           "AND TREAT(m AS com.streamcast.VideoAsset).resolution = :resolution")
    List<MediaAsset> find4KVideosByAuthor(@Param("authorId") Long authorId, @Param("resolution") String resolution);
    
    // Find by username (for EntityGraph demonstration)
    Author findByUsername(String username);
}
