package br.com.toplibrary.domain.model.book.publishingCompany;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="tb_publishingCompany")
@Data
@NoArgsConstructor
public class PublishingCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    public PublishingCompany(String name) {
        this.name = name;
    }
}
