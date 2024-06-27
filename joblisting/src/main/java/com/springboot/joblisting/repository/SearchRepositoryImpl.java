package com.springboot.joblisting.repository;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.springboot.joblisting.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import org.bson.Document;
//import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@Component

public class SearchRepositoryImpl implements SearchRepository{

    @Autowired
    MongoClient client;

    @Autowired
    MongoConverter converter;

    @Override
    public List<Post>findByText(String text){
        final List<Post>posts= new ArrayList<>();

        MongoDatabase database = client.getDatabase("springBoot_Projectz");
        MongoCollection<org.bson.Document> collection = database.getCollection("ProjectCollectionz");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                        new Document("text",
                        new Document("query", "java")
                        .append("path", Arrays.asList("techs", "desc", "profile")))),
                        new Document("$sort",
                        new Document("exp", 1L)),
                        new Document("$limit", 2L)));

        result.forEach(doc ->posts.add(converter.read(Post.class,doc)));

        return posts;
    }
}
