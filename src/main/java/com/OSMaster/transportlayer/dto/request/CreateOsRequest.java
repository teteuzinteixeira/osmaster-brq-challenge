package com.OSMaster.transportlayer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateOsRequest {

    @NotBlank(message = "O título da ordem de serviço é obrigatório.")
    @Size(min = 1, max = 150, message = "O título da ordem de serviço deve ter entre 1 e 150 caracteres.")
    private String title;
    @NotBlank(message = "A descrição da ordem de serviço é obrigatório.")
    @Size(min = 1, max = 255, message = "A descrição da ordem de serviço deve ter entre 1 e 255 caracteres.")
    private String description;
    @NotBlank(message = "O cep da ordem de serviço é obrigatório.")
    @Size(min = 8, max = 8, message = "Insira os 8 numeros do cep, sem traços ou espaços.")
    @Pattern(
            regexp = "^\\d{8}$",
            message = "Insira os 8 numeros do cep, sem traços ou espaços.")
    private String cep;
    @NotNull(message = "O numero do local da ordem de serviço é obrigatório.")
    private Integer number;
    @NotNull(message = "A data da ordem de serviço é obrigatório.")
    private LocalDate date;
}
