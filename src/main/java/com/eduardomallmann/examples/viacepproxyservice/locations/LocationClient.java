package com.eduardomallmann.examples.viacepproxyservice.locations;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CacheConfig(cacheNames = "cep")
@CircuitBreaker(name = "locationClient")
@FeignClient(name = "LocationClient", url = "${app.endpoints.viacep.ws}")
public interface LocationClient {

    @GetMapping("${app.endpoints.viacep.right}")
    @Cacheable(key = "'cep_' + #cep")
    Address getAddressByCep(@PathVariable String cep);

    @GetMapping("${app.endpoints.viacep.wrong}")
    Address getFallbackAddressByCep(@PathVariable String cep);

}

