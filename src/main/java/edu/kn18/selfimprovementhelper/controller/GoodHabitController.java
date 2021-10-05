package edu.kn18.selfimprovementhelper.controller;

import edu.kn18.selfimprovementhelper.domain.GoodHabit;
import edu.kn18.selfimprovementhelper.service.GoodHabitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.TreeMap;


@Controller
@RequestMapping("/habits/good")
public class GoodHabitController {
    final GoodHabitService goodHabitService;

    public GoodHabitController(GoodHabitService goodHabitService) {
        this.goodHabitService = goodHabitService;
    }

    @GetMapping("/666")
    public String lol() {

        return "good/progress";
    }

    @GetMapping()
    public String index(Model model) {

        model.addAttribute("habits", goodHabitService.findAllByOrderByIdAsc());
        return "good/index";
    }

    @GetMapping("/new")
    public String newHabit(Model model) {
         model.addAttribute("habit", new GoodHabit());

         return "good/new";
    }

    @GetMapping("/{id}")
    public String habit(@PathVariable long id, Model model) {
        model.addAttribute("habit", goodHabitService.getById(id));
        var sortedMap = new TreeMap<>(goodHabitService.getById(id).getDaysToComplete());
        model.addAttribute("dates", sortedMap);
        return "/good/habit";
    }

    @GetMapping("/edit/{id}")
    public String editor(@PathVariable long id, Model model) {
        model.addAttribute("habit", goodHabitService.getById(id));
        return "good/edit";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("habit") @Valid GoodHabit habitRequest,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "good/new";
        }

        goodHabitService.saveNew(habitRequest);

        return "redirect:/habits/good";
    }

    @DeleteMapping("/{id}")
    public String deleteHabit(@PathVariable long id) {

        goodHabitService.deleteById(id);
        return "redirect:/habits/good";
    }

    @PatchMapping("/edit/{id}")
    public String changeHabit(@PathVariable long id,
                              @ModelAttribute("habit") @Valid GoodHabit habit,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "good/edit";
        }

        goodHabitService.updateHabit(id, habit);

        return "redirect:/habits/good/";
    }

    @PatchMapping("{id}/{date}/done")
    public String setDone(@PathVariable long id, @PathVariable String date) {
        goodHabitService.updateProgressDate(id, date, true);

        return "redirect:/habits/good/" + id;
    }

    @PatchMapping("{id}/{date}/failed")
    public String setFailed(@PathVariable long id, @PathVariable String date) {
        goodHabitService.updateProgressDate(id, date, false);

        return "redirect:/habits/good/" + id;
    }
}
