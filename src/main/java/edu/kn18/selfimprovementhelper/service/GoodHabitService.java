package edu.kn18.selfimprovementhelper.service;

import edu.kn18.selfimprovementhelper.domain.GoodHabit;
import edu.kn18.selfimprovementhelper.repository.GoodHabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GoodHabitService {
    private final GoodHabitRepository goodHabitRepository;

    @Autowired
    public GoodHabitService(GoodHabitRepository goodHabitRepository) {
        this.goodHabitRepository = goodHabitRepository;
    }

    public GoodHabit saveNew(GoodHabit habit) {

        GoodHabit goodHabit = new GoodHabit();

        goodHabit.setName(habit.getName());
        goodHabit.setStart(LocalDate.now());
        goodHabit.setCompleted(false);
        goodHabit.setGoal(habit.getGoal());
        goodHabit.setDaysToComplete();
        setProgressInfo(goodHabit);

        return goodHabitRepository.save(goodHabit);
    }

    public void saveExist(GoodHabit gh) {
        goodHabitRepository.save(gh);
    }

    public List<GoodHabit> findAllByOrderByIdAsc() {
        return goodHabitRepository.findAllByOrderByIdAsc();
    }

    public void deleteById(long id) {
        goodHabitRepository.deleteById(id);
    }

    public GoodHabit getById(long id) {
        return goodHabitRepository.getById(id);
    }

    public void updateHabit(long id, GoodHabit habit) {
        GoodHabit goodHabit = goodHabitRepository.getById(id);
        goodHabit.setName(habit.getName());
        goodHabit.setGoal(habit.getGoal());
        setProgressInfo(goodHabit);
        if (goodHabit.getGoal() > goodHabit.getDaysToComplete().size()) {
            goodHabit.setDaysToComplete();
        } else if (goodHabit.getGoal() < goodHabit.getDaysToComplete().size()) {
            var map = new TreeMap<>(goodHabit.getDaysToComplete());
            map.subMap(LocalDate.now().plusDays(1), true,
                    LocalDate.now().plusDays(goodHabit.getGoal()), true);
            var newMap = new ConcurrentHashMap<>(map);
            goodHabit.setDaysToComplete(newMap);
        }
        saveExist(goodHabit);
    }

    public void updateProgressDate(long id, String date, boolean isDone) {
        GoodHabit gh = getById(id);
        if (isDone) {
            gh.getDaysToComplete().put(LocalDate.parse(date), 1);
        }
        else {
            gh.getDaysToComplete().put(LocalDate.parse(date), -1);
        }
        setProgressInfo(gh);
        saveExist(gh);
    }

    public void setProgressInfo(GoodHabit gh) {
        gh.setProgress((int) gh.getDaysToComplete()
                .entrySet()
                .stream()
                .filter((entry) -> entry.getValue() == 1)
                .count());
        double d = ((double) gh.getProgress() / gh.getGoal()) * 100;
        d = Math.round(d * 10.0) / 10.0;
        gh.setProgressPercent(d);
    }
}
