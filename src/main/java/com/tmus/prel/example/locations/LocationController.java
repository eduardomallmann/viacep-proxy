package com.tmus.prel.example.locations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("address")
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/{cep}")
    public ResponseEntity<Address> getAddressByCep(@PathVariable @ValidCEP String cep) {
        log.info("M=getAddressByCep, message=Request received, cep={}", cep);
        return ResponseEntity.ok(locationService.getAddressByCep(cep));
    }

    @GetMapping("/fallback/{cep}")
    public ResponseEntity<Address> getFallbackAddressByCep(@PathVariable @ValidCEP String cep) {
        log.info("M=getFallbackAddressByCep, message=Request received, cep={}", cep);
        return ResponseEntity.ok(locationService.getFallbackAddressByCep(cep));
    }

}
