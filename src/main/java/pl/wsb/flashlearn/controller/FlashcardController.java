package pl.wsb.flashlearn.controller;

import pl.wsb.flashlearn.model.Flashcard;
import pl.wsb.flashlearn.model.FlashcardSet;
import pl.wsb.flashlearn.service.FlashcardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/flashcards")
public class FlashcardController {

    private final FlashcardService service;

    public FlashcardController(FlashcardService service) {
        this.service = service;
    }

    @GetMapping
    public String flashcardsDashboard(Model model) {
        model.addAttribute("flashcards", service.getAllFlashcardSets());
        return "flashcards/dashboard";
    }

    @GetMapping("/new_set")
    public String showCreateForm(Model model) {
        model.addAttribute("flashcardset", new FlashcardSet());
        return "flashcards/form";
    }

    @PostMapping
    public String createFlashcardSet(@ModelAttribute FlashcardSet set) {
        service.saveFlashcardSet(set);
        return "redirect:/flashcards";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Optional<FlashcardSet> flashcard = service.getFlashcardSetById(id);
        if (flashcard.isPresent()) {
            model.addAttribute("flashcard", flashcard.get());
            return "flashcards/form";
        } else {
            return "redirect:/flashcards";
        }
    }

    @PostMapping("/update/{id}")
    public String updateFlashcard(@PathVariable String id, @ModelAttribute FlashcardSet flashcard) {
        flashcard.setId(id);
        service.saveFlashcardSet(flashcard);
        return "redirect:/flashcards";
    }

    @GetMapping("/delete/{id}")
    public String deleteFlashcard(@PathVariable String id) {
        service.deleteFlashcard(id);
        return "redirect:/flashcards";
    }
}
