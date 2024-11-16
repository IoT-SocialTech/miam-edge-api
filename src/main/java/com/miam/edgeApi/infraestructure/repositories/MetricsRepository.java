package com.miam.edgeApi.infraestructure.repositories;

import com.miam.edgeApi.domain.entities.Metrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricsRepository extends JpaRepository<Metrics, Integer> {

    @Query(value = "SELECT AVG(heart_rate) FROM (SELECT heart_rate FROM metrics WHERE patient_id = :patientId ORDER BY id DESC LIMIT 5) AS last_five", nativeQuery = true)
    Double findAverageHeartRate(@Param("patientId") int patientId);

    @Query(value = "SELECT AVG(temperature) FROM (SELECT temperature FROM metrics WHERE patient_id = :patientId ORDER BY id DESC LIMIT 5) AS last_five", nativeQuery = true)
    Double findAverageTemperature(@Param("patientId") int patientId);
}
