package edu.kn18.selfimprovementhelper.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "useful_date")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsefulHabitProgressDate {
    @Id
    @GeneratedValue
    private long id;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private UsefulHabitProgressState state;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "useful_habit_id")
    private UsefulHabit usefulHabit;

    public UsefulHabitProgressDate(LocalDate date, UsefulHabitProgressState state) {
        this.date = date;
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsefulHabitProgressDate that = (UsefulHabitProgressDate) o;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
