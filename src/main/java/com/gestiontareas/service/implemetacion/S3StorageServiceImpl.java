package com.gestiontareas.service.implemetacion;

import com.gestiontareas.service.interfaces.IStorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;


@Service
public class S3StorageServiceImpl implements IStorageService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "webp");

    // Extension valida: 1-5 caracteres alfanumericos, sin puntos ni barras.
    private static final Pattern SAFE_EXTENSION = Pattern.compile("^[a-zA-Z0-9]{1,5}$");

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.s3.presign-duration-minutes:15}")
    private long presignDurationMinutes;

    private S3Client s3Client;
    private S3Presigner s3Presigner;

    @Override
    @PostConstruct
    public void init() {
        Region awsRegion = Region.of(region);
        this.s3Client = S3Client.builder()
                .region(awsRegion)
                .build();
        this.s3Presigner = S3Presigner.builder()
                .region(awsRegion)
                .build();
    }

    @Override
    public String store(MultipartFile filename) {
        if (filename == null || filename.isEmpty()) {
            throw new RuntimeException("Failed to store empty file");
        }

        String extension = extractSafeExtension(filename.getOriginalFilename());

        // La key nunca deriva del nombre original: siempre un UUID generado
        // por el servidor. Esto elimina cualquier posibilidad de path/key
        // injection, sin importar lo que envie el cliente.
        String key = UUID.randomUUID() + (extension.isEmpty() ? "" : "." + extension);

        try (var inputStream = filename.getInputStream()) {
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(filename.getContentType())
                    .build();

            s3Client.putObject(putRequest, RequestBody.fromInputStream(inputStream, filename.getSize()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }

        return key;
    }

    @Override
    public Resource loadResource(String key) {
        String safeKey = requireExistingKey(key);
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(presignDurationMinutes))
                .getObjectRequest(GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(safeKey)
                        .build())
                .build();

        URL presignedUrl = s3Presigner.presignGetObject(presignRequest).url();
        return new UrlResource(presignedUrl);
    }

    @Override
    public void delete(String key) {
        String safeKey = requireExistingKey(key);
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(safeKey)
                .build());
    }

    /**
     * Confirma que la key referencia un objeto que realmente existe en el
     * bucket antes de operar sobre ella, y rechaza cualquier valor que no
     * tenga la forma de una key generada por este servicio (UUID[.ext]).
     * Este servicio nunca interpreta la key como una ruta de filesystem.
     */
    private String requireExistingKey(String key) {
        if (key == null || key.isBlank()) {
            throw new RuntimeException("File key must not be empty");
        }

        try {
            s3Client.headObject(HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build());
        } catch (NoSuchKeyException e) {
            throw new RuntimeException("File not found: " + key, e);
        }

        return key;
    }

    private String extractSafeExtension(String originalFilename) {
        if (originalFilename == null) {
            return "";
        }

        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == originalFilename.length() - 1) {
            return "";
        }

        String extension = originalFilename.substring(dotIndex + 1).toLowerCase(Locale.ROOT);

        if (!SAFE_EXTENSION.matcher(extension).matches() || !ALLOWED_EXTENSIONS.contains(extension)) {
            throw new RuntimeException("Unsupported file type: " + extension);
        }

        return extension;
    }
}
