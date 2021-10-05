package edu.kn18.selfimprovementhelper.repository;

import edu.kn18.selfimprovementhelper.domain.GoodHabit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodHabitRepository extends JpaRepository<GoodHabit, Long> {
    GoodHabit save(GoodHabit goodHabit);

    List<GoodHabit> findAll();

    List<GoodHabit> findAllByOrderByIdAsc();
}
