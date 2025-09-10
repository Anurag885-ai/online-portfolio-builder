package com.example.portfolio.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.*;
import java.nio.file.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PortfolioController {

    private String generatedFile = "portfolio.html";

    @PostMapping("/preview")
    public String previewPortfolio(@RequestBody Map<String, String> data) throws IOException {
        String template = """
            <html>
            <head><title>Portfolio</title></head>
            <body>
              <h1>%s</h1>
              <p><b>Skills:</b> %s</p>
              <p><b>Projects:</b> %s</p>
              <p><b>Social:</b> %s</p>
            </body>
            </html>
            """.formatted(data.get("name"), data.get("skills"), data.get("projects"), data.get("social"));

        Files.writeString(Path.of(generatedFile), template);
        return template;
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadPortfolio() throws IOException {
        File file = new File(generatedFile);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=portfolio.html")
                .contentType(MediaType.TEXT_HTML)
                .body(resource);
    }
}
