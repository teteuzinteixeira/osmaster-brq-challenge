package com.OSMaster.transportlayer.api.out;

import com.OSMaster.interactors.LogInteractor;
import com.OSMaster.transportlayer.dto.request.ConsultaCepRequest;
import com.OSMaster.transportlayer.dto.response.ConsultaCepErrorResponse;
import com.OSMaster.transportlayer.dto.response.ConsultaCepResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class CepOut {

    @Value("${external.api.consultarCep.url}")
    private String consultarCepUrl;

    private final LogInteractor logInteractor;
    private WebClient webClient;

    @PostConstruct
    private void init() {
        this.webClient = WebClient.create(consultarCepUrl);
    }

    public ConsultaCepResponse consultaCep(String cep) {
        var request = new ConsultaCepRequest();
        request.setCep(cep);
        var log = logInteractor.iniciarLog(request);

        return webClient.post()
                .uri("/consulta-cep")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(ConsultaCepErrorResponse.class)
                                .flatMap(error -> {
                                    logInteractor.finalizarLog(log, clientResponse.statusCode().value(), error.getMensagem());
                                    return Mono.error(new RuntimeException(error.getMensagem()));
                                })
                )
                .toEntity(ConsultaCepResponse.class)
                .map(res -> {
                    logInteractor.finalizarLog(log, res.getStatusCode().value(), res.getBody());
                    return res.getBody();
                })
                .block();
    }
}
