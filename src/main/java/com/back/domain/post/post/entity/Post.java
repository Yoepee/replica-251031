package com.back.domain.post.post.entity;

import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class Post extends BaseEntity {
    @Column(length = 100)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
}
