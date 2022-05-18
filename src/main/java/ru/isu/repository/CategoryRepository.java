package ru.isu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isu.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
