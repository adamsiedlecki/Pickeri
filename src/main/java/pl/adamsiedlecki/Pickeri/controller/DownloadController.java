package pl.adamsiedlecki.Pickeri.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/download")
public class DownloadController {

    private ServletContext context;

    @Autowired
    public DownloadController(ServletContext servletContext) {
        this.context = servletContext;
    }

    @RequestMapping(value = "/pdf/{fileName}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadPdf(@PathVariable("fileName") String fileName) throws IOException {
        System.out.println("Calling Download:- " + fileName);
        File file = new File("src\\main\\resources\\downloads\\" + fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_PDF).contentLength(file.length())
                .body(resource);

    }

    @RequestMapping(value = "/excel/{fileName}", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
    public ResponseEntity<InputStreamResource> downloadExcel(@PathVariable("fileName") String fileName) throws IOException {
        System.out.println("Calling Download:- " + fileName);
        File file = new File("src\\main\\resources\\downloads\\" + fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_PDF).contentLength(file.length())
                .body(resource);

    }
}
