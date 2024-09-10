package com.example.Blogging_S6_L2.services;

import com.example.Blogging_S6_L2.entities.Autore;
import com.example.Blogging_S6_L2.entities.BlogPost;
import com.example.Blogging_S6_L2.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BlogPostService {

    private List<BlogPost> blogPostList = new ArrayList<>();



    public List<BlogPost> findAll(){
        return this.blogPostList;
    }

    public BlogPost save(BlogPost blogPost){
        Random randomId = new Random();
        blogPost.setId(randomId.nextInt(1,1000000));
        this.blogPostList.add(blogPost);
        return blogPost;
    }

    public BlogPost findById(int blogPostId){
        BlogPost found = null;
        for (BlogPost blogPost: this.blogPostList){
            if (blogPost.getId() == blogPostId) {
                found = blogPost;
            }
        }
        if (found == null){
            throw new NotFoundException(blogPostId);
        }else{
            return found;
        }
    }

    public BlogPost findByIdAndUpdate(int blogPostId, BlogPost updatedBlogPost){
        BlogPost found = findById(blogPostId);

        found.setCategoria(updatedBlogPost.getCategoria());
        found.setContenuto(updatedBlogPost.getContenuto());
        found.setCover(updatedBlogPost.getCover());
        found.setTitolo(updatedBlogPost.getTitolo());
        found.setTempoDiLettura(updatedBlogPost.getTempoDiLettura());

        return found;
    }

    public void findByIdAndDelete(int blogPostId){
        BlogPost found = findById(blogPostId);

        this.blogPostList.remove(found);
    }
}
