package ru.emobile.tinyurl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.emobile.tinyurl.domain.entity.Link;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);

    @Modifying(clearAutomatically = true)
    @Query("""
        update Link l
        set l.status = 'EXPIRED'
        where l.status = 'ACTIVE'
        and l.expiresAt < :now
    """)
    int expireLinks(Instant now);
}
