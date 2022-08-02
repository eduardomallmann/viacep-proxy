package com.eduardomallmann.examples.viacepproxyservice.locations;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

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
