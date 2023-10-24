package br.com.toplibrary.service;

import br.com.toplibrary.domain.model.book.author.Author;
import br.com.toplibrary.domain.repository.AuthorRepository;
import br.com.toplibrary.infra.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorService implements CrudService<Long, Author>{

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    @CacheEvict(value = "author", allEntries = true)
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new NotFoundException(Author.class));
    }

    @Transactional
    @CacheEvict(value = "author", allEntries = true)
    public Author update(Long id, Author authorToUpdated) {
        var author = findById(id);
        author.setName(authorToUpdated.getName());
        return save(author);
    }

    @Transactional
    @CacheEvict(value = "author", allEntries = true)
    public void delete(Long id) {
        var author = findById(id);
        authorRepository.delete(author);
    }
}
