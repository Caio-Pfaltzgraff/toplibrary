package br.com.toplibrary.service;

import br.com.toplibrary.domain.model.book.publishingCompany.PublishingCompany;
import br.com.toplibrary.domain.repository.PublishingCompanyRepository;
import br.com.toplibrary.infra.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PublishingCompanyService implements CrudService<Long, PublishingCompany>{

    @Autowired
    private PublishingCompanyRepository publishingCompanyRepository;


    @Transactional
    @CacheEvict(value = "publishingCompany", allEntries = true)
    public PublishingCompany save(PublishingCompany publishingCompany) {
        return publishingCompanyRepository.save(publishingCompany);
    }

    @Transactional(readOnly = true)
    public List<PublishingCompany> findAll() {
        return publishingCompanyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PublishingCompany findById(Long id) {
        return publishingCompanyRepository.findById(id).orElseThrow(() -> new NotFoundException(PublishingCompany.class));
    }

    @Transactional
    @CacheEvict(value = "publishingCompany", allEntries = true)
    public PublishingCompany update(Long id, PublishingCompany publishingCompanyToUpdated) {
        var publishingCompany = findById(id);
        publishingCompany.setName(publishingCompanyToUpdated.getName());
        return save(publishingCompany);
    }

    @Transactional
    @CacheEvict(value = "publishingCompany", allEntries = true)
    public void delete(Long id) {
        var publishingCompany = findById(id);
        publishingCompanyRepository.delete(publishingCompany);
    }
}
