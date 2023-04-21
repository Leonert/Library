package com.Leonert.spring.Library.repositories;

import com.Leonert.spring.Library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    public List<Book> findByTitleContaining(String title);
}