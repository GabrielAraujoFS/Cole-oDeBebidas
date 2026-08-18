package com.nuvy.colecao_bebidas.controller;

import com.nuvy.colecao_bebidas.model.Foto;
import com.nuvy.colecao_bebidas.service.FotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/fotos")
public class FotoController {

    private final FotoService fotoService;

    public FotoController(FotoService fotoService) {
        this.fotoService = fotoService;
    }

    @PostMapping(value = "/upload/{itemId}", consumes = "multipart/form-data")
    public ResponseEntity<Foto> upload(
            @PathVariable Long itemId,
            @RequestParam("arquivo") MultipartFile arquivo,
            @RequestParam(value = "principal", defaultValue = "false") boolean principal
    ) throws IOException {
        Foto foto = fotoService.uploadFoto(itemId, arquivo, principal);
        return ResponseEntity.status(201).body(foto);
    }
}