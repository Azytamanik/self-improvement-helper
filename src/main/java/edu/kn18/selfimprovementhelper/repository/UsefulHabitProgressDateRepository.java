package edu.kn18.selfimprovementhelper.repository;

import edu.kn18.selfimprovementhelper.domain.UsefulHabit;
import edu.kn18.selfimprovementhelper.domain.UsefulHabitProgressDate;
import edu.kn18.selfimprovementhelper.domain.UsefulHabitProgressState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UsefulHabitProgressDateRepository extends JpaRepository<UsefulHabitProgressDate, Long> {
    UsefulHabitProgressDate findByUsefulHabitIdAndDateEquals(long usefulHabitId, LocalDate date);

    int countAllByUsefulHabitAndStateEqualsAndDateAfter(UsefulHabit usefulHabit,
                                                        UsefulHabitProgressState state,
                                                        LocalDate usefulHabit_startHabit);

    List<UsefulHabitProgressDate> findAllByUsefulHabitIdEqualsAndDateAfterOrderByDateAsc(long usefulHabit_id, LocalDate date);
}
