package com.hainguyen.security.model;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class  BaseEntity<T> {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private T id;

    @CreatedDate
    private LocalDate createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
