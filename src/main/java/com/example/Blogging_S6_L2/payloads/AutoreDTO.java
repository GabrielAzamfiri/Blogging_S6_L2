package com.example.Blogging_S6_L2.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AutoreDTO(
        @NotEmpty(message = "Il nome proprio è obbligatorio")
        @Size(min = 3, max = 40, message = "Il nome proprio deve essere compreso tra 3 e 40 caratteri")
        String nome,

        @NotEmpty(message = "Il cognome è obbligatorio")
        @Size(min = 3, max = 40, message = "Il cognome deve essere compreso tra 3 e 40 caratteri")
        String cognome,

        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "L'email inserita non è valida")
        String email,

        @NotNull(message = "La data di nascita è obbligatoria")
        LocalDate dataDiNascita
) {

}
