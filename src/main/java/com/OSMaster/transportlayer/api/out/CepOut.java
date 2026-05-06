package com.OSMaster.transportlayer.api.out;

import com.OSMaster.transportlayer.dto.request.ConsultaCepRequest;
import com.OSMaster.transportlayer.dto.response.ConsultaCepErrorResponse;
import com.OSMaster.transportlayer.dto.response.ConsultaCepResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class CepOut {

    private final WebClient webClient = WebClient.create("http://localhost:8081");

    public ConsultaCepResponse consultaCep(String cep) {
        var request = new ConsultaCepRequest();
        request.setCep(cep);

        return webClient.post()
                .uri("/consulta-cep")
                .bodyValue(request)
                .retrieve()
                .onStatus(status -> status.equals(HttpStatus.NOT_FOUND), clientResponse ->
                        clientResponse.bodyToMono(ConsultaCepErrorResponse.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody.getMensagem())))
                )
                .bodyToMono(ConsultaCepResponse.class)
                .block();
    }
}
