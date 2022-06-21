package com.tmus.prel.example.locations;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    private static final String CEP_REGEX = "[0-9]{8}";
    private final LocationClient locationClient;

    public Address getAddressByCep(final String cep) {
        log.info("M=getAddressByCep, message=Start retrieving address by cep, cep={}", cep);
        return this.locationClient.getAddressByCep(cep);
    }

    @Retry(name = "locationService", fallbackMethod = "fallbackAddressByCep")
    public Address getFallbackAddressByCep(final String cep) {
        log.info("M=getFallbackAddressByCep, message=Start retrieving address by cep from fallback call, cep={}", cep);
        return this.locationClient.getFallbackAddressByCep(cep);
    }

    private Address fallbackAddressByCep(final String cep, Exception e) {
        log.info("M=fallbackAddressByCep, message=Start retrieving address by cep from fallback method, cep={}, error={}", cep, e.getMessage());
        return this.locationClient.getAddressByCep(cep);
    }

}
