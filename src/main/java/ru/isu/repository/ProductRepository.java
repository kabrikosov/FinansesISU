package ru.isu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.isu.model.Category;
import ru.isu.model.Product;
import ru.isu.model.User;
import ru.isu.projection.ProductAndSum;
import ru.isu.projection.ProductSumGroupCategories;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByUser(User user);

    List<ProductAndSum> findProductsByDateBetweenAndUserEquals(@Param("dateStart") LocalDateTime dateStart, @Param("dateEnd") LocalDateTime dateEnd, @Param("user") User user);

    @Query("select sum(p.price*p.quantity) from Product p where (p.date between :dateStart and :dateEnd) and (p.user = :user)")
    Integer findSumByDateBetween(@Param("dateStart") LocalDateTime dateStart, @Param("dateEnd") LocalDateTime dateEnd, @Param("user") User user);

    @Query("select c as category, sum(p.price*p.quantity) as sum from Product p join Category c on p.category = c where p.date between :dateStart and :dateEnd and p.user = :user group by c")
    List<ProductSumGroupCategories> findSumByDateBetweenGroupByCategory(@Param("dateStart") LocalDateTime dateStart, @Param("dateEnd") LocalDateTime dateEnd, @Param("user") User user);
}
