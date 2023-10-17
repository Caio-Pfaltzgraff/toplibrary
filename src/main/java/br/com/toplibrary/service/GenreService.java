package br.com.toplibrary.service;

import br.com.toplibrary.domain.model.book.genre.Genre;
import br.com.toplibrary.domain.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreService {
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
        return genreRepository.findById(id).get();
    }
}
