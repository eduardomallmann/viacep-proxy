package com.eduardomallmann.studies.example.locations;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Viacep client call, that handles also caching responses and circuit breaker resilience.
 * <p>
 * Creates also a call to a non existent endpoint in order to simulate retrial and fallback resilience.
 * </p>
 *
 * @author eduardomallmann
 */
@CacheConfig(cacheNames = "cep")
@CircuitBreaker(name = "locationClient")
@FeignClient(name = "LocationClient", url = "${app.endpoints.viacep.ws}")
public interface LocationClient {

    /**
     * Calls viacep endpoint that replies with the address for the zip code wanted.
     * <p>
     * Implements cache in the call using redis.
     * </p>
     *
     * @param cep cep zip code to be searched
     *
     * @return {@code Address} object for the zip code searched.
     */
    @GetMapping("${app.endpoints.viacep.right}")
    @Cacheable(key = "'cep_' + #cep")
    Address getAddressByCep(@PathVariable String cep);

    /**
     * Calls Viacep non-existent endpoint that replies with an error to provoke resilience.
     *
     * @param cep cep zip code to be searched
     *
     * @return {@code FeignException} for a not found response from the client.
     */
    @GetMapping("${app.endpoints.viacep.wrong}")
    Address getFallbackAddressByCep(@PathVariable String cep);

}

