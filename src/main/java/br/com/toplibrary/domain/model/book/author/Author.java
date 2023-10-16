package br.com.toplibrary.domain.model.book.author;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity(name = "tb_author")
@Data
public class Author {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String name;
}
