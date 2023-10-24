package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.book.publishingCompany.PublishingCompany;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PublishingCompanyRepository extends JpaRepository<PublishingCompany, Long> {
    @Override
    @Cacheable("publishingCompany")
    Optional<PublishingCompany> findById(Long aLong);

    @Override
    @Cacheable("publishingCompany")
    List<PublishingCompany> findAll();
}
