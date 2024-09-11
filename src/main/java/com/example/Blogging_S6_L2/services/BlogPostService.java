package com.example.Blogging_S6_L2.services;


import com.example.Blogging_S6_L2.entities.Autore;
import com.example.Blogging_S6_L2.entities.BlogPost;
import com.example.Blogging_S6_L2.entities.BlogPostPayload;
import com.example.Blogging_S6_L2.exceptions.NotFoundException;
import com.example.Blogging_S6_L2.repositories.AutoreRepository;
import com.example.Blogging_S6_L2.repositories.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class BlogPostService {
    @Autowired
    private BlogPostRepository  blogPostRepository;
    @Autowired
    private AutoreRepository autoreRepository;




    public Page<BlogPost> findAll(int page, int size, String sortBy){
        if(page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.blogPostRepository.findAll(pageable);
    }

    public BlogPost save(BlogPostPayload blogPostPayload){

        Autore autore = autoreRepository.findById(blogPostPayload.getAutore()).orElseThrow(() ->  new NotFoundException(blogPostPayload.getAutore()));
        BlogPost blogPost = new BlogPost( blogPostPayload.getCategoria(), blogPostPayload.getTitolo(), blogPostPayload.getCover(), blogPostPayload.getContenuto(), blogPostPayload.getTempoDiLettura(), autore);
        return this.blogPostRepository.save(blogPost);
    }


    public BlogPost findById(UUID blogPostId){
        return this.blogPostRepository.findById(blogPostId).orElseThrow(() -> new NotFoundException(blogPostId));

    }

    public BlogPost findByIdAndUpdate(UUID  blogPostId, BlogPost updatedBlogPost){
        BlogPost found = findById(blogPostId);

        found.setCategoria(updatedBlogPost.getCategoria());
        found.setContenuto(updatedBlogPost.getContenuto());
        found.setCover(updatedBlogPost.getCover());
        found.setTitolo(updatedBlogPost.getTitolo());
        found.setTempoDiLettura(updatedBlogPost.getTempoDiLettura());

        return this.blogPostRepository.save(found);
    }

    public void findByIdAndDelete(UUID  blogPostId){
        BlogPost found = findById(blogPostId);
        this.blogPostRepository.delete(found);
    }
}
