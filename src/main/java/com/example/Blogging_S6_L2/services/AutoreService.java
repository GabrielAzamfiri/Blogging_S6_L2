package com.example.Blogging_S6_L2.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Blogging_S6_L2.entities.Autore;
import com.example.Blogging_S6_L2.exceptions.BadRequestException;
import com.example.Blogging_S6_L2.exceptions.NotFoundException;
import com.example.Blogging_S6_L2.payloads.AutoreDTO;
import com.example.Blogging_S6_L2.repositories.AutoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.UUID;

@Service
public class AutoreService {
    @Autowired
    private AutoreRepository autoreRepository;
    @Autowired
    private Cloudinary cloudinaryUploader;




    public Page<Autore> findAll(int page, int size, String sortBy){
        if(page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.autoreRepository.findAll(pageable);
    }

    public Autore save(AutoreDTO autore){
        // 1. Verifico che l'email non sia già stata utilizzata
        this.autoreRepository.findByEmail(autore.email()).ifPresent(
                // 1.1 Se lo è triggero un errore (400 Bad Request)
                author -> {
                    throw new BadRequestException("L'email " + autore.email() + " è già in uso!");
                }
        );

        // 2. Se tutto è ok procedo con l'aggiungere campi 'server-generated' (nel nostro caso avatarURL)
        Autore newAutore = new Autore(autore.nome(), autore.cognome(), autore.email(), autore.dataDiNascita(),"https://ui-avatars.com/api/?name="+autore.nome()+"+"+autore.cognome());

        // 3. Salvo lo User
        return this.autoreRepository.save(newAutore);
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

    public Autore uploadImage(MultipartFile file , UUID authorId) throws IOException {
        Autore autore = findById(authorId);
        String url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        System.out.println("URL: " + url);
        autore.setAvatar(url);
        // ... poi l'url lo salvo nel db per quello specifico utente
        autoreRepository.save(autore);
        return autore;
    }

}
