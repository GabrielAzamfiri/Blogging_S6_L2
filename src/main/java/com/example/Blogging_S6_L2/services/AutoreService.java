package com.example.Blogging_S6_L2.services;

import com.example.Blogging_S6_L2.entities.Autore;
import com.example.Blogging_S6_L2.exceptions.BadRequestException;
import com.example.Blogging_S6_L2.exceptions.NotFoundException;
import com.example.Blogging_S6_L2.repositories.AutoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



import java.util.UUID;

@Service
public class AutoreService {
    @Autowired
    private AutoreRepository autoreRepository;





    public Page<Autore> findAll(int page, int size, String sortBy){
        if(page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.autoreRepository.findAll(pageable);
    }

    public Autore save(Autore autore){
        // 1. Verifico che l'email non sia già stata utilizzata
        this.autoreRepository.findByEmail(autore.getEmail()).ifPresent(
                // 1.1 Se lo è triggero un errore (400 Bad Request)
                author -> {
                    throw new BadRequestException("L'email " + autore.getEmail() + " è già in uso!");
                }
        );

        // 2. Se tutto è ok procedo con l'aggiungere campi 'server-generated' (nel nostro caso avatarURL)
        autore.setAvatar("https://ui-avatars.com/api/?name="+autore.getNome()+"+"+autore.getCognome());

        System.out.println("hello");
        // 3. Salvo lo User
        return this.autoreRepository.save(autore);
    }

    public Autore findById(UUID autoreId){
        return this.autoreRepository.findById(autoreId).orElseThrow(() -> new NotFoundException(autoreId));
    }

    public Autore findByIdAndUpdate(UUID autoreId, Autore updatedAutore){
        // 1. Controllo se l'email nuova è già in uso
        this.autoreRepository.findByEmail(updatedAutore.getEmail()).ifPresent(
                // 1.1 Se lo è triggero un errore (400 Bad Request)
                autore -> {
                    throw new BadRequestException("L'email " + updatedAutore.getEmail() + " è già in uso!");
                }
        );
        Autore found = this.findById(autoreId);
        found.setNome(updatedAutore.getNome());
        found.setCognome(updatedAutore.getCognome());
        found.setEmail(updatedAutore.getEmail());
        found.setDataDiNascita(updatedAutore.getDataDiNascita());
        found.setAvatar("https://ui-avatars.com/api/?name="+updatedAutore.getNome()+"+"+updatedAutore.getCognome());
        return this.autoreRepository.save(found);
    }

    public void findByIdAndDelete(UUID autoreId){
        Autore found = this.findById(autoreId);
        this.autoreRepository.delete(found);
    }

}
