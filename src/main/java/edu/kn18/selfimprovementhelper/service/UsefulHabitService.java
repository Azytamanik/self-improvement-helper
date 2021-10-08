package edu.kn18.selfimprovementhelper.service;

import edu.kn18.selfimprovementhelper.domain.UsefulHabit;
import edu.kn18.selfimprovementhelper.domain.UsefulHabitProgressDate;
import edu.kn18.selfimprovementhelper.domain.UsefulHabitProgressState;
import edu.kn18.selfimprovementhelper.repository.UsefulHabitProgressDateRepository;
import edu.kn18.selfimprovementhelper.repository.UsefulHabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class UsefulHabitService {
    private final UsefulHabitRepository habitRepo;
    private final UsefulHabitProgressDateRepository dateRepo;

    @Autowired
    public UsefulHabitService(UsefulHabitRepository habitRepo,
                              UsefulHabitProgressDateRepository dateRepo) {
        this.habitRepo = habitRepo;
        this.dateRepo = dateRepo;
    }

    public void createHabit(UsefulHabit useful) {

        UsefulHabit usefulHabit = new UsefulHabit()
                .setName(useful.getName())
                .setStartHabit(LocalDate.now())
                .setStartGoal(LocalDate.now())
                .setCompleted(false)
                .setGoal(useful.getGoal());


        for (int i = 1; i <= useful.getGoal(); i++) {

            UsefulHabitProgressDate usefulHabitProgressDate = new UsefulHabitProgressDate(
                    LocalDate.now().plusDays(i), UsefulHabitProgressState.UNCHECKED);

            usefulHabitProgressDate.setUsefulHabit(usefulHabit);
            dateRepo.save(usefulHabitProgressDate);
        }

        setProgressInfo(usefulHabit);

        habitRepo.save(usefulHabit);
    }

    public List<UsefulHabit> findAllByCompletedIsTrueOrderByIdAsc() {
        return habitRepo.findAllByCompletedIsTrueOrderByIdAsc();
    }

    public List<UsefulHabit> findAllByCompletedIsFalseOrderByIdAsc() {
        return habitRepo.findAllByCompletedIsFalseOrderByIdAsc();
    }

    public void deleteById(long id) {
        habitRepo.deleteById(id);
    }

    public UsefulHabit getById(long id) {
        return habitRepo.getById(id);
    }

    public void updateHabit(long id, UsefulHabit useful) {

        UsefulHabit usefulHabit = habitRepo.getById(id);
        usefulHabit.setName(useful.getName());
        usefulHabit.setGoal(useful.getGoal());
        setProgressInfo(usefulHabit);

        int goal = usefulHabit.getGoal();
        List<UsefulHabitProgressDate> dateList = dateRepo
                .findAllByUsefulHabitIdEqualsAndDateAfterOrderByDateAsc(id, usefulHabit.getStartGoal());
        int dateListSize = dateRepo
                .findAllByUsefulHabitIdEqualsAndDateAfterOrderByDateAsc(id, usefulHabit.getStartGoal()).size();

        if (goal > dateListSize) {

            for (int i = dateListSize; i < goal; i++) {
                UsefulHabitProgressDate usefulHabitProgressDate = new UsefulHabitProgressDate(
                        dateList.get(0).getDate().plusDays(i), UsefulHabitProgressState.UNCHECKED);
                usefulHabitProgressDate.setUsefulHabit(usefulHabit);
                dateRepo.save(usefulHabitProgressDate);
            }

        } else if (goal < dateListSize) {

            for (int i = dateListSize - 1; i >= goal; i--) {
                dateRepo.delete(dateList.get(i));
            }
        }

        habitRepo.save(usefulHabit);
    }

    public void updateProgressDate(long id, String date, boolean isDone) {

        UsefulHabit usefulHabit = getById(id);

        UsefulHabitProgressDate usefulHabitDate = dateRepo
                .findByUsefulHabitIdAndDateEquals(id, LocalDate.parse(date));

        if (isDone) {
            usefulHabitDate.setState(UsefulHabitProgressState.COMPLETED);
        } else {
            usefulHabitDate.setState(UsefulHabitProgressState.FAILED);
            usefulHabit.setStartGoal(usefulHabitDate.getDate());

            for (var i = usefulHabit.getStartGoal();
                 i.isBefore(usefulHabit.getStartGoal().plusDays(usefulHabit.getGoal()));
                 i = i.plusDays(1)) {

                UsefulHabitProgressDate usefulHabitProgressDate
                        = new UsefulHabitProgressDate(i.plusDays(1), UsefulHabitProgressState.UNCHECKED);

                if (!usefulHabit.getUsefulHabitDates().contains(usefulHabitProgressDate)) {
                    usefulHabitProgressDate.setUsefulHabit(usefulHabit);
                    dateRepo.save(usefulHabitProgressDate);
                }
            }
        }

        dateRepo.save(usefulHabitDate);

        setProgressInfo(usefulHabit);

        habitRepo.save(usefulHabit);
    }

    public void setProgressInfo(UsefulHabit usefulHabit) {

        usefulHabit.setProgress(dateRepo
                .countAllByUsefulHabitAndStateEqualsAndDateAfter(usefulHabit,
                        UsefulHabitProgressState.COMPLETED,
                        usefulHabit.getStartGoal()));

        double d = ((double) usefulHabit.getProgress() / usefulHabit.getGoal()) * 100;

        d = Math.round(d * 10.0) / 10.0;

        usefulHabit.setCompleted(d == 100.0);

        usefulHabit.setProgressPercent(d);
    }
}
