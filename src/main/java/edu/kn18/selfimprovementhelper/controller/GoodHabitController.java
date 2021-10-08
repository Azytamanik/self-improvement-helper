package edu.kn18.selfimprovementhelper.controller;

import edu.kn18.selfimprovementhelper.domain.UsefulHabit;
import edu.kn18.selfimprovementhelper.domain.UsefulHabitProgressDate;
import edu.kn18.selfimprovementhelper.service.UsefulHabitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;

@Controller
@RequestMapping("/habits/good")
public class GoodHabitController {
    final UsefulHabitService usefulHabitService;

    public GoodHabitController(UsefulHabitService usefulHabitService) {
        this.usefulHabitService = usefulHabitService;
    }

    @GetMapping("/666")
    public String lol() {

        return "good/progress";
    }

    @GetMapping()
    public String index(Model model) {

        model.addAttribute("completedHabits", usefulHabitService.findAllByCompletedIsTrueOrderByIdAsc());
        model.addAttribute("notCompletedHabits", usefulHabitService.findAllByCompletedIsFalseOrderByIdAsc());
        return "good/index";
    }

    @GetMapping("/new")
    public String newHabit(Model model) {
         model.addAttribute("habit", new UsefulHabit());

         return "good/new";
    }

    @GetMapping("/{id}")
    public String habit(@PathVariable long id, Model model) {
        model.addAttribute("habit", usefulHabitService.getById(id));

        var sortedList = usefulHabitService.getById(id).getUsefulHabitDates();
        sortedList.sort(Comparator.comparing(UsefulHabitProgressDate::getDate));

        model.addAttribute("dates", sortedList);

        return "/good/habit";
    }

    @GetMapping("/edit/{id}")
    public String editor(@PathVariable long id, Model model) {
        model.addAttribute("habit", usefulHabitService.getById(id));
        return "good/edit";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("habit") @Valid UsefulHabit habitRequest,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "good/new";
        }

        usefulHabitService.createHabit(habitRequest);

        return "redirect:/habits/good";
    }

    @DeleteMapping("/{id}")
    public String deleteHabit(@PathVariable long id) {

        usefulHabitService.deleteById(id);
        return "redirect:/habits/good";
    }

    @PatchMapping("/edit/{id}")
    public String changeHabit(@PathVariable long id,
                              @ModelAttribute("habit") @Valid UsefulHabit habit,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "good/edit";
        }

        usefulHabitService.updateHabit(id, habit);

        return "redirect:/habits/good/" + id;
    }

    @PatchMapping("{id}/{date}/done")
    public String setDone(@PathVariable long id, @PathVariable String date) {
        usefulHabitService.updateProgressDate(id, date, true);

        return "redirect:/habits/good/" + id;
    }

    @PatchMapping("{id}/{date}/failed")
    public String setFailed(@PathVariable long id, @PathVariable String date) {
        usefulHabitService.updateProgressDate(id, date, false);

        return "redirect:/habits/good/" + id;
    }
}
