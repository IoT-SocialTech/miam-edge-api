package com.miam.edgeApi.security.service;

import com.miam.edgeApi.infraestructure.repositories.DeviceRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final DeviceRepository deviceRepository;

    public CustomUserDetailsService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String macAddress) throws UsernameNotFoundException {
        //busca al usuario por su username o email
        var device = deviceRepository.findDeviceById(macAddress)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró al dispositivo con el username o id: " + macAddress));

        //crea y retorna un objeto que representa al usuario autenticado
        return User.withUsername(device.getId())
                .password(device.getSecurityKey())
                .build();
    }
}

