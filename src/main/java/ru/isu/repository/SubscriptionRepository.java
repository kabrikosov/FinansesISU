package ru.isu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.isu.model.Subscription;
import ru.isu.model.User;
import ru.isu.projection.GrouppingSumProjection;

import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    List<Subscription> findAllByUser(User user);

    @Query("select sum(p.price*p.quantity) from Subscription s " +
            "join Product p on p.subscription = s " +
            "where ((s.startDateTime between :dateStart and :dateEnd)" +
            "or (s.startDateTime < :dateStart and (s.expirationDateTime is null or s.expirationDateTime >= :dateEnd))) and p.user = :user and s.user = :user")
    Integer findSumByDateBetween(@Param("dateStart") LocalDateTime dateStart, @Param("dateEnd") LocalDateTime dateEnd, @Param("user") User user);

    @Query("select s from Subscription s where ((s.startDateTime between :dateStart and :dateEnd) " +
            "or (s.startDateTime <= :dateStart and (s.expirationDateTime is null or s.expirationDateTime >= :dateStart))) and s.user = :user")
    List<Subscription> findSubscriptionsByDates(@Param("dateStart") LocalDateTime dateStart, @Param("dateEnd") LocalDateTime dateEnd, @Param("user") User user);

    @Query("select s.id as id, sum(p.price*p.quantity) as sum from Product p join Subscription s on p.subscription = s where ((s.startDateTime between :dateStart and :dateEnd) " +
            "or (s.startDateTime <= :dateStart and (s.expirationDateTime is null or s.expirationDateTime >= :dateStart))) and p.user = :user and s.user = :user group by s")
    List<GrouppingSumProjection> findSubscriptionsSumGroupping(@Param("dateStart") LocalDateTime dateStart, @Param("dateEnd") LocalDateTime dateEnd, @Param("user") User user);

//    List<SubscriptionInfo> findSubscriptionsByStartDateTimeBeforeAndAndExpirationDateTimeAfterOrStartDateTimeBeforeAndExpirationDateTimeIsNull(LocalDateTime startDateTime, LocalDateTime expirationDateTime);

}
