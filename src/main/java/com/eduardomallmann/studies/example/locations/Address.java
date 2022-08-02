package com.eduardomallmann.studies.example.locations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address implements Serializable {

    private static final long serialVersionUID = 8976707935900584810L;

    private String logradouro;
    private String complemento;
    private String ibge;
    private String cep;
    private String bairro;
    private String localidade;
    private String uf;
    private String gia;
    private String ddd;
    private String siafi;

}