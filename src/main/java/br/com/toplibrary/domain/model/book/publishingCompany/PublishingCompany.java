package br.com.toplibrary.domain.model.book.publishingCompany;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity(name="tb_publishingCompany")
@Data
public class PublishingCompany {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String name;
}
