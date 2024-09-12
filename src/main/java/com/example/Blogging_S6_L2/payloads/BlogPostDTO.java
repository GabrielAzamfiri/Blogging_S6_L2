package com.example.Blogging_S6_L2.payloads;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



import java.util.UUID;

public record BlogPostDTO(
        @NotEmpty(message = "La categoria è obbligatoria")
        @Size(min = 3, max = 40, message = "La categoria deve essere compresa tra 3 e 40 caratteri")
        String categoria,

        @NotEmpty(message = "Il titolo è obbligatorio")
        @Size(min = 3, max = 40, message = "Il titolo deve essere compreso tra 3 e 40 caratteri")
        String titolo,

        @NotEmpty(message = "Il contenuto è obbligatorio")
        @Size(min = 3, max = 40, message = "Il contenuto deve essere compreso tra 3 e 40 caratteri")
        String contenuto,

        @NotNull(message = "Il tempo di lettura è obbligatorio")
        @Min(value = 1, message = "Il tempo di lettura deve essere di almeno 1 minuto")
        int tempoDiLettura,

        @NotNull(message = "L'autore è obbligatorio")
        UUID autore
        ) {

}
