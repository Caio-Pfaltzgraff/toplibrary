package br.com.toplibrary.domain.model.rental;

import br.com.toplibrary.domain.model.book.Book;
import br.com.toplibrary.domain.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "tb_rental")
@Data
public class Rental {

    @Id @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tb_user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;

    @CreationTimestamp
    private LocalDateTime rentalDate;
    private LocalDateTime devolutionDate;
}
