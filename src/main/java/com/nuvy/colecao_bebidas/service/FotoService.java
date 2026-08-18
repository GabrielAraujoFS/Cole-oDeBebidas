package com.nuvy.colecao_bebidas.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nuvy.colecao_bebidas.model.Foto;
import com.nuvy.colecao_bebidas.model.Item;
import com.nuvy.colecao_bebidas.repository.FotoRepository;
import com.nuvy.colecao_bebidas.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FotoService {

    private final Cloudinary cloudinary;
    private final FotoRepository fotoRepository;
    private final ItemRepository itemRepository;

    public FotoService(Cloudinary cloudinary, FotoRepository fotoRepository, ItemRepository itemRepository) {
        this.cloudinary = cloudinary;
        this.fotoRepository = fotoRepository;
        this.itemRepository = itemRepository;
    }

    public Foto uploadFoto(Long itemId, MultipartFile arquivo, boolean principal) throws IOException {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado com id: " + itemId));

        Map uploadResult = cloudinary.uploader().upload(arquivo.getBytes(), ObjectUtils.emptyMap());
        String url = (String) uploadResult.get("secure_url");

        Foto foto = new Foto();
        foto.setUrl(url);
        foto.setPrincipal(principal);
        foto.setItem(item);

        return fotoRepository.save(foto);
    }
}