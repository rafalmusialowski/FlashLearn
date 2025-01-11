package pl.wsb.flashlearn.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class StaticResourceController {

    @GetMapping(value = {"/styles.css", "/flashcards/styles.css", "/flashcards/topic/styles.css"})
    public ResponseEntity<Resource> getStaticResource(@RequestParam(value = "path", required = false) String path) {
        String resourcePath = "static/css/styles.css";
        if (path != null) {
            resourcePath = "static/css/" + path;
        }

        Resource resource = new ClassPathResource(resourcePath);
        return ResponseEntity
                .ok()
                .body(resource);
    }
}
