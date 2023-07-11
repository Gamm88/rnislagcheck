package com.example.demo.repository;

import com.example.demo.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DevicesRepository extends JpaRepository <Device, Integer> {
    List<Device> getDeviceByDeviceNumber(String deviceNumber);
}