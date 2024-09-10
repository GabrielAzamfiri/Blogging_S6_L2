package com.example.Blogging_S6_L2.services;

import com.example.Blogging_S6_L2.entities.Autore;
import com.example.Blogging_S6_L2.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class AutoreService {

    private List<Autore> autoriList = new ArrayList<>();



    public List<Autore> findAll(){
        return this.autoriList;
    }

    public Autore save(Autore autore){
        Random randomId = new Random();
        autore.setId(randomId.nextInt(1,1000000));
        this.autoriList.add(autore);
        return autore;
    }

    public Autore findById(int autoreId){
        Autore found = null;
        for (Autore autore: this.autoriList){
            if (autore.getId() == autoreId) {
                found = autore;
            }
        }
        if (found == null){
            throw new NotFoundException(autoreId);
        }else{
            return found;
        }
    }

    public Autore findByIdAndUpdate(int autoreId, Autore updatedAutore){
        Autore found = findById(autoreId);

        found.setNome(updatedAutore.getNome());
        found.setCognome(updatedAutore.getCognome());
        found.setEmail(updatedAutore.getEmail());
        found.setDataDiNascita(updatedAutore.getDataDiNascita());
        found.setAvatar(updatedAutore.getAvatar());
        if (found == null){
            throw new NotFoundException(autoreId);
        }
        return found;
    }

    public void findByIdAndDelete(int autoreId){
        Autore found = findById(autoreId);

        this.autoriList.remove(found);
    }

}
