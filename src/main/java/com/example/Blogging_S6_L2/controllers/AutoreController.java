package com.example.Blogging_S6_L2.controllers;


import com.example.Blogging_S6_L2.entities.Autore;
import com.example.Blogging_S6_L2.services.AutoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController // Specializzazione di @Component, ci serve per definire una "collezione" di endpoints
// Ogni endpoint sarà definito tramite un metodo di questa classe
@RequestMapping("/authors") // Serve per definire un prefisso comune nell'URL di tutti gli endpoint di questo controller
public class AutoreController {
    @Autowired
    private AutoreService autoreService;

    @GetMapping() // Qua sto definendo il metodo HTTP da utilizzare per questo endpoint e l'ultima parte dell'URL
    // Per contattare questo endpoint dovrò mandare una richiesta GET a http://localhost:3001/authors
    public List<Autore> getAll(){
        return autoreService.findAll();
    }

    @GetMapping("/{authorId}")
    private Autore getById(@PathVariable int authorId){
        return autoreService.findById(authorId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Serve per customizzare lo status code (CREATED --> 201)
    private Autore createAuthor(@RequestBody Autore autore){
        return autoreService.save(autore);
    }

    @PutMapping("/{authorId}")
    private Autore findByIdAndUpdate(@PathVariable int authorId, @RequestBody Autore updatedAuthor){
        return autoreService.findByIdAndUpdate(authorId,updatedAuthor);
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Serve per customizzare lo status code (NO_CONTENT --> 204)
    private void findByIdAndDelete(@PathVariable int authorId){
        autoreService.findByIdAndDelete(authorId);
    }
}
