package pl.wsb.flashlearn.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StaticResourceController {

    @GetMapping("/styles.css")
    public ResponseEntity<Resource> getStylesCss() {
        Resource resource = new ClassPathResource("static/css/styles.css");
        return ResponseEntity
                .ok()
                .body(resource);
    }
}
