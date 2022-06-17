package com.tmus.prel.example.locations;

import com.tmus.prel.example.exceptions.BusinessException;
import com.tmus.prel.example.utils.PropertiesConstants;
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

    public Address getAddressByCep(final String cep) throws BusinessException {
        log.info("M=getAddressByCep, message=Start retrieving address by cep, cep={}", cep);
        this.checkCEP(cep);
        return this.locationClient.getAddressByCep(cep);
    }

    @Retry(name = "locationService", fallbackMethod = "fallbackAddressByCep")
    public Address getFallbackAddressByCep(final String cep) throws BusinessException {
        log.info("M=getFallbackAddressByCep, message=Start retrieving address by cep from fallback call, cep={}", cep);
        this.checkCEP(cep);
        return this.locationClient.getFallbackAddressByCep(cep);
    }

    public Address fallbackAddressByCep(final String cep, Exception e) {
        log.info("M=fallbackAddressByCep, message=Start retrieving address by cep from fallback method, cep={}, error={}", cep, e.getMessage());
        return this.locationClient.getAddressByCep(cep);
    }

    private void checkCEP(final String cep) throws BusinessException {
        log.info("M=checkCEP, message=Start cep validation, cep={}", cep);
        if (!cep.matches(CEP_REGEX)) {
            log.error("M=checkCEP, message=Invalid cep throwing exception, cep={}", cep);
            throw new BusinessException(PropertiesConstants.WRONG_CEP_NUMBER.getErrorKey(), "Invalid CEP characters");
        }
        log.info("M=checkCEP, message=Valid cep, cep={}", cep);
    }
}
