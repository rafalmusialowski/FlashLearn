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
    @GetMapping("/topic/{title}")
    public String viewTopic(@PathVariable String title, Model model) {
        FlashcardSet flashcardSet = service.getFlashcardSetByTitle(title)
                .orElseThrow(() -> new RuntimeException("FlashcardSet not found with title: " + title));
        model.addAttribute("flashcardSet", flashcardSet);
        return "flashcards/topic";
    }
    @PostMapping
    public String createFlashcardSet(@RequestParam("name") String name,
                                     @RequestParam("description") String description,
                                     Model model) {
        // Sprawdź, czy nazwa została podana
        if (name == null || name.isEmpty()) {
            model.addAttribute("error", "The topic name must not be empty!");
            return "flashcards/form";
        }

        // Sprawdź, czy zbiór o tej nazwie już istnieje
        Optional<FlashcardSet> existingSet = service.getFlashcardSetByTitle(name);
        if (existingSet.isPresent()) {
            model.addAttribute("error", "Topic with name '" + name + "' already exists!");
            return "flashcards/form";
        }

        // Utwórz nowy zbiór
        FlashcardSet newSet = new FlashcardSet();
        newSet.setTitle(name);
        newSet.setDescription(description);
        service.saveFlashcardSet(newSet);

        return "redirect:/flashcards";
    }
    @PostMapping("/topic/{title}/add")
    public String addFlashcardToTopic(@PathVariable String title,
                                      @RequestParam String name,
                                      @RequestParam String description,
                                      Model model) {
        Optional<FlashcardSet> flashcardSetOptional = service.getFlashcardSetByTitle(title);
        if (flashcardSetOptional.isEmpty()) {
            model.addAttribute("error", "Topic not found");
            return "flashcards/topic";
        }

        FlashcardSet flashcardSet = flashcardSetOptional.get();
        Flashcard flashcard = new Flashcard(name, description);
        service.addFlashcardToTopic(title, flashcard);

        return "redirect:/flashcards/topic/" + title;
    }
    @GetMapping("/topic/{title}/edit/{flashcardName}")
    public String showEditFlashcardForm(@PathVariable String title, @PathVariable String flashcardName, Model model) {
        Optional<FlashcardSet> flashcardSetOptional = service.getFlashcardSetByTitle(title);
        if (flashcardSetOptional.isPresent()) {
            FlashcardSet flashcardSet = flashcardSetOptional.get();
            for (Flashcard flashcard : flashcardSet.getFlashcards()) {
                if (flashcard.getName().equals(flashcardName)) {
                    model.addAttribute("flashcard", flashcard);
                    model.addAttribute("flashcardSetTitle", title);
                    return "flashcards/edit-flashcard";
                }
            }
        }
        return "redirect:/flashcards/topic/" + title;
    }

    @PostMapping("/topic/{title}/update/{flashcardName}")
    public String updateFlashcard(@PathVariable String title, @PathVariable String flashcardName,
                                  @RequestParam String name, @RequestParam String description) {
        Flashcard updatedFlashcard = new Flashcard(name, description);
        service.updateFlashcard(title, flashcardName, updatedFlashcard);
        return "redirect:/flashcards/topic/" + title;
    }

    @GetMapping("/topic/{title}/delete/{flashcardName}")
    public String deleteFlashcard(@PathVariable String title, @PathVariable String flashcardName) {
        service.deleteFlashcard(title, flashcardName);
        return "redirect:/flashcards/topic/" + title;
    }
}
