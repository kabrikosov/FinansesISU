package ru.isu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isu.model.Subscription;
@Repository

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
}
