package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.book.publishingCompany.PublishingCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PublishingCompanyRepository extends JpaRepository<PublishingCompany, UUID> {
}
