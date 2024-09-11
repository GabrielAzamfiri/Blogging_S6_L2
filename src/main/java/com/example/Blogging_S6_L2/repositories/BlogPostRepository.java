package com.example.Blogging_S6_L2.repositories;

import com.example.Blogging_S6_L2.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, UUID> {

}
