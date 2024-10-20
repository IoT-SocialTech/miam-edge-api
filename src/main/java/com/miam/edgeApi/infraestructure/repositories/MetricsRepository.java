package com.miam.edgeApi.infraestructure.repositories;

import com.miam.edgeApi.domain.entities.Metrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricsRepository extends JpaRepository<Metrics, Integer> {

    @Query(value = "SELECT AVG(heart_rate) FROM (SELECT heart_rate FROM metrics ORDER BY id DESC LIMIT 5) AS last_five", nativeQuery = true)
    Double findAverageHeartRate();

    @Query(value = "SELECT AVG(temperature) FROM (SELECT temperature FROM metrics ORDER BY id DESC LIMIT 5) AS last_five", nativeQuery = true)
    Double findAverageTemperature();
}
