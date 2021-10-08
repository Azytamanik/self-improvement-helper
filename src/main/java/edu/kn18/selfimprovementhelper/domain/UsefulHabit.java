package edu.kn18.selfimprovementhelper.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
public class UsefulHabit {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank(message = "Name should be not blank")
    private String name;

    @Column(updatable = false)
    private LocalDate startHabit;

    private LocalDate startGoal;

    @Positive(message = "Goal should be a positive number")
    private int goal;

    private boolean completed;

    @OneToMany(mappedBy = "usefulHabit", cascade = CascadeType.ALL, orphanRemoval = true)
    List<UsefulHabitProgressDate> usefulHabitDates = new ArrayList<>();

    private int progress;
    private double progressPercent;

    public UsefulHabit setGoal(int goal) {
        this.goal = Math.max(goal, progress);
        return this;
    }
}
