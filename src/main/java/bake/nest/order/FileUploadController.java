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
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @PostMapping("/payment-slip")
    public ResponseEntity<?> uploadPaymentSlip(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), path);

            // In a real app, you'd return a full URL. For local dev, we return the path/filename.
            // We should also serve these files.
            return ResponseEntity.ok("/api/v1/uploads/files/" + fileName);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Could not upload file: " + e.getMessage());
        }
    }

    @GetMapping("/files/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) throws IOException {
        Path path = Paths.get(uploadDir + fileName);
        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }
        byte[] image = Files.readAllBytes(path);
        return ResponseEntity.ok(image);
    }
}
