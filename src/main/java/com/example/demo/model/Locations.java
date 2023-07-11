package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "locations_p2023_06", schema="data")
public class Locations {
    @Id
    @Column(name = "device_id")
    private Integer id;

    @Column(name = "device_time")
    private LocalDateTime deviceTime;

    @Column(name = "insert_time")
    private LocalDateTime insertTime;
}