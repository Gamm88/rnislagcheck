package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "devices", schema="data")
public class Device {
    @Id
    @Column(name = "device_id")
    private Integer id;

    @Column(name = "number")
    private String deviceNumber;
}
