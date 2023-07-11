package com.example.demo.repository;

import com.example.demo.model.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationsRepository extends JpaRepository<Locations, Integer> {

    @Modifying
    @Query(value = "" +
            "SELECT " +
            "locations_p2023_07.device_id," +
            "locations_p2023_07.device_time," +
            "locations_p2023_07.insert_time " +
            "FROM data.locations_p2023_07 " +
            "WHERE locations_p2023_07.device_id = ? " +
            "ORDER BY locations_p2023_07.device_time DESC " +
            "LIMIT 1", nativeQuery = true)
    List<Locations> find(Long id);
}