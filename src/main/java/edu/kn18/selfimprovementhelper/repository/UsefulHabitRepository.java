package edu.kn18.selfimprovementhelper.repository;

import edu.kn18.selfimprovementhelper.domain.UsefulHabit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsefulHabitRepository extends JpaRepository<UsefulHabit, Long> {
    List<UsefulHabit> findAllByCompletedIsTrueOrderByIdAsc();
    List<UsefulHabit> findAllByCompletedIsFalseOrderByIdAsc();
}
