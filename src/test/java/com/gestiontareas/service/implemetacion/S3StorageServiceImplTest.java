package com.gestiontareas.service.implemetacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.net.URI;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class S3StorageServiceImplTest {

    @Mock
    private S3Client s3Client;

    @Mock
    private S3Presigner s3Presigner;

    private S3StorageServiceImpl storageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        storageService = new S3StorageServiceImpl();
        ReflectionTestUtils.setField(storageService, "bucketName", "test-bucket");
        ReflectionTestUtils.setField(storageService, "region", "us-east-1");
        ReflectionTestUtils.setField(storageService, "presignDurationMinutes", 15L);
        // Se inyectan directamente los mocks de S3Client/S3Presigner en lugar
        // de invocar init(), que crearia clientes reales contra AWS.
        ReflectionTestUtils.setField(storageService, "s3Client", s3Client);
        ReflectionTestUtils.setField(storageService, "s3Presigner", s3Presigner);
    }

    @Test
    void testStoreGeneratesServerSideUuidKeyIgnoringOriginalFilename() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "../../etc/passwd.png", "image/png", "content".getBytes());

        String key = storageService.store(file);

        // La key nunca debe contener el nombre original ni secuencias de path traversal.
        assertFalse(key.contains(".."));
        assertFalse(key.contains("/"));
        assertTrue(key.matches("^[0-9a-fA-F-]{36}\\.png$"));

        verify(s3Client, times(1)).putObject(any(PutObjectRequest.class), any(software.amazon.awssdk.core.sync.RequestBody.class));
    }

    @Test
    void testStoreWithEmptyFileThrows() {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "photo.png", "image/png", new byte[0]);

        assertThrows(RuntimeException.class, () -> storageService.store(emptyFile));
        verify(s3Client, never()).putObject(any(PutObjectRequest.class), any(software.amazon.awssdk.core.sync.RequestBody.class));
    }

    @Test
    void testStoreWithDisallowedExtensionThrows() {
        MockMultipartFile maliciousFile = new MockMultipartFile(
                "file", "payload.exe", "application/octet-stream", "content".getBytes());

        assertThrows(RuntimeException.class, () -> storageService.store(maliciousFile));
        verify(s3Client, never()).putObject(any(PutObjectRequest.class), any(software.amazon.awssdk.core.sync.RequestBody.class));
    }

    @Test
    void testStoreWithoutExtensionStoresKeyWithoutExtension() {
        MockMultipartFile file = new MockMultipartFile("file", "noextension", "image/png", "content".getBytes());

        String key = storageService.store(file);

        assertTrue(key.matches("^[0-9a-fA-F-]{36}$"));
    }

    @Test
    void testLoadResourceWithExistingKeyReturnsPresignedUrlResource() throws Exception {
        String key = "existing-key.png";
        when(s3Client.headObject(any(HeadObjectRequest.class)))
                .thenReturn(software.amazon.awssdk.services.s3.model.HeadObjectResponse.builder().build());

        PresignedGetObjectRequest presignedRequest = mock(PresignedGetObjectRequest.class);
        when(presignedRequest.url()).thenReturn(new URI("https://test-bucket.s3.amazonaws.com/" + key + "?sig=abc").toURL());
        when(s3Presigner.presignGetObject(any(GetObjectPresignRequest.class))).thenReturn(presignedRequest);

        var resource = storageService.loadResource(key);

        assertNotNull(resource);
        assertTrue(resource.getURL().toString().contains(key));
    }

    @Test
    void testLoadResourceWithNonExistingKeyThrows() {
        when(s3Client.headObject(any(HeadObjectRequest.class)))
                .thenThrow(NoSuchKeyException.builder().message("not found").build());

        assertThrows(RuntimeException.class, () -> storageService.loadResource("missing-key.png"));
        verify(s3Presigner, never()).presignGetObject(any(GetObjectPresignRequest.class));
    }

    @Test
    void testDeleteWithExistingKeyCallsS3Delete() {
        String key = "existing-key.png";
        when(s3Client.headObject(any(HeadObjectRequest.class)))
                .thenReturn(software.amazon.awssdk.services.s3.model.HeadObjectResponse.builder().build());

        storageService.delete(key);

        verify(s3Client, times(1)).deleteObject(any(DeleteObjectRequest.class));
    }

    @Test
    void testDeleteWithNonExistingKeyThrowsAndDoesNotCallDelete() {
        when(s3Client.headObject(any(HeadObjectRequest.class)))
                .thenThrow(NoSuchKeyException.builder().message("not found").build());

        assertThrows(RuntimeException.class, () -> storageService.delete("missing-key.png"));
        verify(s3Client, never()).deleteObject(any(DeleteObjectRequest.class));
    }

    @Test
    void testDeleteWithBlankKeyThrows() {
        assertThrows(RuntimeException.class, () -> storageService.delete(" "));
        verify(s3Client, never()).headObject(any(HeadObjectRequest.class));
        verify(s3Client, never()).deleteObject(any(DeleteObjectRequest.class));
    }
}
