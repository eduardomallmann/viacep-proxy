package com.tmus.prel.example.locations;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.resetAllRequests;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.cloud.contract.spec.internal.MediaTypes.APPLICATION_JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmus.prel.example.exceptions.ObjectError;
import com.tmus.prel.example.utils.PropertiesConstants;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AutoConfigureWireMock(port = 41393)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"app.endpoints.viacep.ws=http://localhost:41393", "spring.cache.type=none"})
@DisplayName("Location Functional Tests")
class LocationControllerTest {

    private static final String CEP = "88015420";
    private static final String WRONG_CEP = "88015";
    private static final String CEP_PARAM = "cep";
    private static final String CEP_PATH_PARAM = "{cep}";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTEXT_PATH = "/address";
    private static final String FALLBACK_PATH = "/fallback";
    private static final String REQUEST_ENDPOINT = "/{cep}";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    final Address addressExpected = Address.builder()
                                            .ibge("4205407")
                                            .logradouro("Rua Ferreira Lima")
                                            .bairro("Centro")
                                            .localidade("Florianópolis")
                                            .uf("SC")
                                            .cep("88015-420")
                                            .ddd("48")
                                            .siafi("8105")
                                            .build();

    @Autowired
    public TestRestTemplate restTemplate;

    @Value("${app.endpoints.viacep.right}")
    private String viacepRightEnpoint;
    @Value("${app.endpoints.viacep.wrong}")
    private String viacepWrongEndpoint;

    private String viacepRightURI;

    @BeforeEach
    void setUp() {
        this.viacepRightURI = this.viacepRightEnpoint.replace(CEP_PATH_PARAM, CEP);
    }

    @AfterEach
    void tearDown() {
        resetAllRequests();
    }

    @Test
    @DisplayName("Get address by cep with successful response")
    void getAddressByCep() throws JsonProcessingException {
        this.stubForGetSuccess();

        final var result = this.getRestTemplate(CONTEXT_PATH + REQUEST_ENDPOINT);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(addressExpected, result.getBody());
    }

    @Test
    @DisplayName("Get address by cep through fallback with successful response")
    void getFallbackAddressByCep() throws JsonProcessingException {
        final var viacepWrongURI = this.viacepWrongEndpoint.replace(CEP_PATH_PARAM, CEP);
        stubFor(get(viacepWrongURI).willReturn(aResponse().withStatus(HttpStatus.NOT_FOUND.value())));
        this.stubForGetSuccess();

        final var result = this.getRestTemplate(CONTEXT_PATH + FALLBACK_PATH + REQUEST_ENDPOINT);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(addressExpected, result.getBody());
        verify(3, getRequestedFor(urlEqualTo(viacepWrongURI)));
        verify(getRequestedFor(urlEqualTo(this.viacepRightURI)));
    }

    @Test
    @DisplayName("Check cep with wrong match")
    void checkCEPFailed() {
        final var objectErrorExpected = new ObjectError(PropertiesConstants.WRONG_CEP_NUMBER.getErrorKey(), "getAddressByCep.cep: Invalid CEP characters.");

        final var result = this.getRestTemplateError();

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(objectErrorExpected, result.getBody());
    }

    private void stubForGetSuccess() throws JsonProcessingException {
        stubFor(get(this.viacepRightURI)
                        .willReturn(aResponse()
                                            .withStatus(HttpStatus.OK.value())
                                            .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                                            .withBody(MAPPER.writeValueAsString(addressExpected))));
    }

    private ResponseEntity<Address> getRestTemplate(final String endpoint) {
        Map<String, String> vars = new HashMap<>();
        vars.put(CEP_PARAM, CEP);
        return restTemplate.exchange(endpoint,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                Address.class,
                vars);
    }

    private ResponseEntity<ObjectError> getRestTemplateError() {
        Map<String, String> vars = new HashMap<>();
        vars.put(CEP_PARAM, WRONG_CEP);
        return restTemplate.exchange(CONTEXT_PATH + REQUEST_ENDPOINT,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                ObjectError.class,
                vars);
    }
}