package br.com.toplibrary.domain.model.book.genre;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity(name = "tb_genre")
@Data
public class Genre {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String name;
}
