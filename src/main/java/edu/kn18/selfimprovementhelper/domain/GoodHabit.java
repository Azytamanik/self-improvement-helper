package edu.kn18.selfimprovementhelper.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
@Entity
public class GoodHabit {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank(message = "Name should be not blank")
    private String name;

    @Column(updatable = false)
    private LocalDate start;

    @Positive(message = "Goal should be a positive number")
    private int goal;

    private boolean isCompleted;

    @ElementCollection
    @CollectionTable(name = "dates",
            joinColumns = {@JoinColumn(name = "goodhabit_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "date")
    @Column(name = "value")
    Map<LocalDate, Integer> daysToComplete = new ConcurrentHashMap<>();

    private int progress;
    private double progressPercent;

    public void setGoal(int goal) {
        this.goal = Math.max(goal, progress);
    }

    public void setDaysToComplete() {
        for (int i = 1; i <= this.goal; i++) {
            daysToComplete.putIfAbsent(LocalDate.now().plusDays(i), 0);
        }
    }
}
