package com.miam.edgeApi.infraestructure.repositories;

import com.miam.edgeApi.domain.entities.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DeviceRepository extends CrudRepository<Device, String> {

    Device getDeviceById(String id);

    Optional<Device> findDeviceById(String id);

}
