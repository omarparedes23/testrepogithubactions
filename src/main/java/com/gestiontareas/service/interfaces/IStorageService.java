package com.gestiontareas.service.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IStorageService {
    void init() throws IOException;

    String store(MultipartFile filename);

    Resource loadResource(String filename);

    void delete(String filename);
}
