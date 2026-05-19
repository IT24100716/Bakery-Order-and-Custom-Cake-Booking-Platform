package bake.nest.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/uploads")
public class FileUploadController {

    private final String uploadDir = "uploads/slips/";

    public FileUploadController() {
        createDir(uploadDir);
        createDir("uploads/custom-cakes/");
    }

    private void createDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @PostMapping("/payment-slip")
    public ResponseEntity<?> uploadPaymentSlip(@RequestParam("file") MultipartFile file) {
        return uploadFile(file, uploadDir);
    }

    @PostMapping("/custom-cake")
    public ResponseEntity<?> uploadCustomCakeImage(@RequestParam("file") MultipartFile file) {
        return uploadFile(file, "uploads/custom-cakes/");
    }

    private ResponseEntity<?> uploadFile(MultipartFile file, String targetDir) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get(targetDir + fileName);
            Files.copy(file.getInputStream(), path);

            return ResponseEntity.ok("/api/v1/uploads/files?path=" + targetDir + fileName);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Could not upload file: " + e.getMessage());
        }
    }

    @GetMapping("/files")
    public ResponseEntity<byte[]> getFile(@RequestParam String path) throws IOException {
        Path filePath = Paths.get(path);
        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }
        byte[] image = Files.readAllBytes(filePath);
        return ResponseEntity.ok(image);
    }
}
