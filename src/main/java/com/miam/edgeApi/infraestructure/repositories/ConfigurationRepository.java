package com.miam.edgeApi.infraestructure.repositories;

import com.miam.edgeApi.domain.entities.Configuration;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConfigurationRepository extends CrudRepository<Configuration, Integer> {

    Configuration getConfigurationById(int id);

    List<Configuration> getConfigurationsByDeviceId(String id);

}
