package com.example.Blogging_S6_L2.controllers;


import com.example.Blogging_S6_L2.entities.Autore;
import com.example.Blogging_S6_L2.exceptions.BadRequestException;
import com.example.Blogging_S6_L2.payloads.AutoreDTO;
import com.example.Blogging_S6_L2.services.AutoreService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController // Specializzazione di @Component, ci serve per definire una "collezione" di endpoints
// Ogni endpoint sarà definito tramite un metodo di questa classe
@RequestMapping("/authors") // Serve per definire un prefisso comune nell'URL di tutti gli endpoint di questo controller
public class AutoreController {
    @Autowired
    private AutoreService autoreService;

    @GetMapping() // Qua sto definendo il metodo HTTP da utilizzare per questo endpoint e l'ultima parte dell'URL
    // Per contattare questo endpoint dovrò mandare una richiesta GET a http://localhost:3001/authors
    public Page<Autore> getAll(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String sortBy){
        return this.autoreService.findAll(page, size, sortBy);
    }

    @GetMapping("/{authorId}")
    private Autore getById(@PathVariable UUID  authorId){
        return autoreService.findById(authorId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Serve per customizzare lo status code (CREATED --> 201)
    private Autore createAuthor(@RequestBody @Validated AutoreDTO autore, BindingResult validationResult){
        // @Validated serve per 'attivare' le regole di validazione descritte nel DTO
        // BindingResult mi permette di capire se ci sono stati errori e quali errori ci sono stati
        if(validationResult.hasErrors())  {
            // Se ci sono stati errori lanciamo un'eccezione custom
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            // Se non ci sono stati salviamo l'utente
            return autoreService.save(autore);
        }

    }

    @PutMapping("/{authorId}")
    private Autore findByIdAndUpdate(@PathVariable UUID  authorId, @RequestBody Autore updatedAuthor){
        return autoreService.findByIdAndUpdate(authorId,updatedAuthor);
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Serve per customizzare lo status code (NO_CONTENT --> 204)
    private void findByIdAndDelete(@PathVariable UUID  authorId){
        autoreService.findByIdAndDelete(authorId);
    }

    @PostMapping("/{authorId}/avatar")
    public void uploadAvatar(@RequestParam("avatar") MultipartFile image, @PathVariable UUID authorId) throws IOException {
        // "avatar" deve corrispondere ESATTAMENTE come il campo del FormData che ci invia il Frontend
        // Se non corrisponde non troverò il file
        this.autoreService.uploadImage(image, authorId);
    }
}
