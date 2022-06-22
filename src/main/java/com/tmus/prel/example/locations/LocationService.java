package com.tmus.prel.example.locations;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Responsible to call the client and implement the retryable and fallback resilience for the wrong endpoint call.
 *
 * @author 013087631
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationClient locationClient;

    /**
     * Responsible to call the client and retrieve its response.
     *
     * @param cep zip code to be searched
     *
     * @return {@code Address} object for the zip code searched.
     */
    public Address getAddressByCep(final String cep) {
        log.info("M=getAddressByCep, message=Start retrieving address by cep, cep={}", cep);
        return this.locationClient.getAddressByCep(cep);
    }

    /**
     * Responsible to call the wrong endpoint and apply the retry and call the fallback method when the call didn't work.
     *
     * @param cep zip code to be searched
     *
     * @return {@code Address} object for the zip code searched.
     */
    @Retry(name = "locationService", fallbackMethod = "fallbackAddressByCep")
    public Address getFallbackAddressByCep(final String cep) {
        log.info("M=getFallbackAddressByCep, message=Start retrieving address by cep from fallback call, cep={}", cep);
        return this.locationClient.getFallbackAddressByCep(cep);
    }

    /**
     * Fallback method that calls the correct client after the unsuccessful retries.
     *
     * @param cep zip code to be searched
     * @param e   error received from the "fallbacked" method.
     *
     * @return {@code Address} object for the zip code searched.
     */
    private Address fallbackAddressByCep(final String cep, Exception e) {
        log.info("M=fallbackAddressByCep, message=Start retrieving address by cep from fallback method, cep={}, error={}", cep, e.getMessage());
        return this.locationClient.getAddressByCep(cep);
    }

}
