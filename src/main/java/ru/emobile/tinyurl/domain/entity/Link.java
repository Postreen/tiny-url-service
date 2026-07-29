package ru.emobile.tinyurl.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.emobile.tinyurl.domain.entity.enums.Status;

import java.time.Instant;

@Entity
@Table(name = "links")
@Getter
@Setter
@NoArgsConstructor
public class Link extends BaseEntity {

    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl;

    @Column(name = "short_code", unique = true, length = 32)
    private String shortCode;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.ACTIVE;
}