package com.eduardomallmann.studies.example.locations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller class, responsible to expose the endpoints and receive the external calls.
 *
 * @author 013087631
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("address")
public class LocationController {

    private final LocationService locationService;

    /**
     * Gets a CEP request and start the process to retrieve the address from this zip code.
     * <p>
     * The path variable is validated with the annotation "@ValidCEP" implemented by {@code ValidCEP} interface.
     * </p>
     *
     * @param cep zip code to be searched
     *
     * @return {@code Address} object for the zip code searched.
     */
    @GetMapping(value = "/{cep}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Address> getAddressByCep(@PathVariable @ValidCEP String cep) {
        log.info("M=getAddressByCep, message=Request received, cep={}", cep);
        return ResponseEntity.ok(locationService.getAddressByCep(cep));
    }

    /**
     * Responsible to show the resilience operation.
     * <p>
     * This endpoint receives a request and its flow calls a non existent client, making the application use the resilience retrying 3 times before turn to fallback. At
     * end the call is well succeed because of the fallback operation.
     * </p>
     *
     * @param cep zip code to be searched
     *
     * @return {@code Address} object for the zip code searched.
     */
    @GetMapping("/fallback/{cep}")
    public ResponseEntity<Address> getFallbackAddressByCep(@PathVariable @ValidCEP String cep) {
        log.info("M=getFallbackAddressByCep, message=Request received, cep={}", cep);
        return ResponseEntity.ok(locationService.getFallbackAddressByCep(cep));
    }

}
