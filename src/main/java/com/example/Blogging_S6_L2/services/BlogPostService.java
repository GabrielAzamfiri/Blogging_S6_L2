package com.example.Blogging_S6_L2.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Blogging_S6_L2.entities.Autore;
import com.example.Blogging_S6_L2.entities.BlogPost;
import com.example.Blogging_S6_L2.payloads.BlogPostDTO;
import com.example.Blogging_S6_L2.exceptions.NotFoundException;
import com.example.Blogging_S6_L2.repositories.AutoreRepository;
import com.example.Blogging_S6_L2.repositories.BlogPostRepository;
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
public class BlogPostService {
    @Autowired
    private BlogPostRepository  blogPostRepository;
    @Autowired
    private AutoreRepository autoreRepository;
    @Autowired
    private Cloudinary cloudinaryUploader;



    public Page<BlogPost> findAll(int page, int size, String sortBy){
        if(page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.blogPostRepository.findAll(pageable);
    }

    public BlogPost save(BlogPostDTO blogPostPayload){

        Autore autore = autoreRepository.findById(blogPostPayload.autore()).orElseThrow(() ->  new NotFoundException(blogPostPayload.autore()));
        BlogPost blogPost = new BlogPost( blogPostPayload.categoria(), blogPostPayload.titolo(),
                "https://ui-avatars.com/api/?name="+blogPostPayload.categoria()+"+"+blogPostPayload.titolo(), blogPostPayload.contenuto(), blogPostPayload.tempoDiLettura(), autore);

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

    public BlogPost uploadImage(MultipartFile file , UUID blogPostId) throws IOException {
        BlogPost blogPost = findById(blogPostId);
        String url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        System.out.println("URL: " + url);
        blogPost.setCover(url);
        // ... poi l'url lo salvo nel db per quello specifico utente
        blogPostRepository.save(blogPost);
        return blogPost;
    }
}
