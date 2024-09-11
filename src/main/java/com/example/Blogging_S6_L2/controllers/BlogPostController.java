package com.example.Blogging_S6_L2.controllers;

import com.example.Blogging_S6_L2.entities.BlogPost;
import com.example.Blogging_S6_L2.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController // Specializzazione di @Component, ci serve per definire una "collezione" di endpoints
// Ogni endpoint sarà definito tramite un metodo di questa classe
@RequestMapping("/blogPost") // Serve per definire un prefisso comune nell'URL di tutti gli endpoint di questo controller
public class BlogPostController {
    @Autowired
    private BlogPostService blogPostService;

    @GetMapping() // Qua sto definendo il metodo HTTP da utilizzare per questo endpoint e l'ultima parte dell'URL
    // Per contattare questo endpoint dovrò mandare una richiesta GET a http://localhost:3001/authors
    public Page<BlogPost> getAll(@RequestParam(defaultValue = "0" ) int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sortBy){
        return blogPostService.findAll(page, size, sortBy);
    }

    @GetMapping("/{blogPostId}")
    private BlogPost getById(@PathVariable UUID  blogPostId){
        return blogPostService.findById(blogPostId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Serve per customizzare lo status code (CREATED --> 201)
    private BlogPost createAuthor(@RequestBody BlogPost blogPostId){
        return blogPostService.save(blogPostId);
    }

    @PutMapping("/{blogPostId}")
    private BlogPost findByIdAndUpdate(@PathVariable UUID  blogPostId, @RequestBody BlogPost updatedBlogPostId){
        return blogPostService.findByIdAndUpdate(blogPostId,updatedBlogPostId);
    }

    @DeleteMapping("/{blogPostId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Serve per customizzare lo status code (NO_CONTENT --> 204)
    private void findByIdAndDelete(@PathVariable UUID blogPostId){
        blogPostService.findByIdAndDelete(blogPostId);
    }
}
