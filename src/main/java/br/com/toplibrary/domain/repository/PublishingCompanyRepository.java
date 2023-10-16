package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.book.publishingCompany.PublishingCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublishingCompanyRepository extends JpaRepository<PublishingCompany, Long> {
}
