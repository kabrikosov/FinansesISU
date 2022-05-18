package ru.isu.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
public class Product implements DateToStringConvertable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;

    private Integer price;

    private Integer quantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    public Product() {
        this.date = LocalDateTime.now();
        this.quantity = 1;
    }

    public Product(String name, Integer price, LocalDateTime date, Category category) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.category = category;
    }
}
