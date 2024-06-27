package com.springboot.joblisting.repository;

import com.springboot.joblisting.model.Post;

import java.util.List;

public interface SearchRepository {
    List<Post>findByText(String text);
}
