package br.com.toplibrary.service;

import br.com.toplibrary.domain.model.book.publishingCompany.PublishingCompany;
import br.com.toplibrary.domain.repository.PublishingCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PublishingCompanyService {

    @Autowired
    private PublishingCompanyRepository publishingCompanyRepository;


    @Transactional
    public PublishingCompany save(PublishingCompany publishingCompany) {
        return publishingCompanyRepository.save(publishingCompany);
    }

    @Transactional(readOnly = true)
    public List<PublishingCompany> findAll() {
        return publishingCompanyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PublishingCompany findById(Long id) {
        return publishingCompanyRepository.findById(id).get();
    }
}
