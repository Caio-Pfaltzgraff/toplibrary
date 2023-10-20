package br.com.toplibrary.service;

import br.com.toplibrary.domain.model.book.genre.Genre;
import br.com.toplibrary.domain.repository.GenreRepository;
import br.com.toplibrary.infra.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreService implements CrudService<Long, Genre>{
    @Autowired
    private GenreRepository genreRepository;


    @Transactional
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Genre findById(Long id) {
        return genreRepository.findById(id).orElseThrow(() -> new NotFoundException(Genre.class));
    }

    @Transactional
    public Genre update(Long id, Genre genreToUpdated) {
        var genre = findById(id);
        genre.setName(genreToUpdated.getName());
        return save(genre);
    }

    @Transactional
    public void delete(Long id) {
        var genre = findById(id);
        genreRepository.delete(genre);
    }
}
