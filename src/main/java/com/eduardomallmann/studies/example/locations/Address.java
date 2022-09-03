package com.eduardomallmann.studies.example.locations;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

/**
 * Data transfer object for via-cep successful responses.
 *
 * @author eduardomallmann
 */
@Builder
@JsonInclude(NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public record Address(String logradouro,
               String complemento,
               String ibge,
               String cep,
               String bairro,
               String localidade,
               String uf,
               String gia,
               String ddd,
               String siafi) {
}