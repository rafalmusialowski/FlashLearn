package pl.wsb.flashlearn.controller;

import pl.wsb.flashlearn.model.Flashcard;
import pl.wsb.flashlearn.model.FlashcardSet;
import pl.wsb.flashlearn.service.FlashcardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/flashcards")
public class FlashcardController {

    private final FlashcardService service;

    public FlashcardController(FlashcardService service) {
        this.service = service;
    }

    @GetMapping
    public String displayFlashcardsDashboard(Model model) {
        model.addAttribute("flashcards", service.getAllFlashcardSets());
        return "flashcards/dashboard";
    }

    @PostMapping
    public String createFlashcardSet(@RequestParam("name") String name,
                                     @RequestParam("description") String description,
                                     Model model) {
        try {
            service.createFlashcardSet(name, description);
            return "redirect:/flashcards";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "flashcards/form";
        }
    }

    @GetMapping("/new_set")
    public String showCreateSetForm(Model model) {
        model.addAttribute("flashcardset", new FlashcardSet());
        return "flashcards/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteFlashcard(@PathVariable String id) {
        service.deleteFlashcard(id);
        return "redirect:/flashcards";
    }

    @GetMapping("/topic/{topic}")
    public String displayTopic(@PathVariable String topic, Model model) {
        try {
            FlashcardSet flashcardSet = getFlashcardSetOrThrow(topic);
            model.addAttribute("flashcardSet", flashcardSet);
            return "flashcards/topic";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "flashcards/form";
        }
    }

    private FlashcardSet getFlashcardSetOrThrow(String topic) {
        return service.getFlashcardSetByTitle(topic)
                .orElseThrow(() -> new RuntimeException("Topic not found with title: " + topic));
    }

    @PostMapping("/topic/{topicTitle}/add")
    public String addFlashcardToTopic(@PathVariable String topicTitle,
                                      @RequestParam String name,
                                      @RequestParam String description,
                                      Model model) {
        if (name == null || name.isBlank() || description == null || description.isBlank()) {
            model.addAttribute("error", "Name and description must not be empty.");
            return "redirect:/flashcards/topic/" + topicTitle;
        }

        try {
            service.addFlashcardToTopic(topicTitle, name, description);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/flashcards/topic/" + topicTitle;
    }

    @GetMapping("/topic/{title}/delete/{flashcardName}")
    public String deleteFlashcard(@PathVariable String title, @PathVariable String flashcardName) {
        service.deleteFlashcard(title, flashcardName);
        return "redirect:/flashcards/topic/" + title;
    }

    @GetMapping("/topic/{topic}/study")
    @ResponseBody
    public List<Flashcard> enterStudyMode(@PathVariable String topic) {
        FlashcardSet flashcardSet = getFlashcardSetOrThrow(topic);
        List<Flashcard> flashcards = flashcardSet.getFlashcards();
        Collections.shuffle(flashcards);
        return flashcards;
    }

    @GetMapping("/topic/{title}/start")
    public String startLearning(@PathVariable String title, Model model) {
        model.addAttribute("title", title);
        return "flashcards/study";
    }
}