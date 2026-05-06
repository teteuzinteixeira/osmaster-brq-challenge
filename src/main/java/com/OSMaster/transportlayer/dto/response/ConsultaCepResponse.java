package com.OSMaster.transportlayer.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaCepResponse {
    @JsonProperty("Logradouro")
    private String logradouro;
    @JsonProperty("Número")
    private Integer numero;
    @JsonProperty("Bairro")
    private String bairro;
    @JsonProperty("Cidade")
    private String cidade;
    @JsonProperty("Estado")
    private String estado;
}
