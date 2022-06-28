package com.aw2122.finalactivity.apirest.models;

import org.springframework.data.repository.CrudRepository;

public interface BooksDAO extends CrudRepository<BooksEntity, String> {
}
