package com.nuvy.colecao_bebidas.service;

import com.nuvy.colecao_bebidas.dto.ItemRequestDTO;
import com.nuvy.colecao_bebidas.dto.ItemResponseDTO;
import com.nuvy.colecao_bebidas.dto.DashboardDTO;
import com.nuvy.colecao_bebidas.dto.ValorAquisicaoDTO;
import com.nuvy.colecao_bebidas.enums.EstadoConservacao;
import com.nuvy.colecao_bebidas.enums.TipoRecipiente;
import com.nuvy.colecao_bebidas.model.Fabricante;
import com.nuvy.colecao_bebidas.model.Item;
import com.nuvy.colecao_bebidas.repository.FabricanteRepository;
import com.nuvy.colecao_bebidas.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final FabricanteRepository fabricanteRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${seguranca.senha-hash}")
    private String senhaHash;

    public ItemService(ItemRepository itemRepository, FabricanteRepository fabricanteRepository, PasswordEncoder passwordEncoder) {
        this.itemRepository = itemRepository;
        this.fabricanteRepository = fabricanteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<ItemResponseDTO> listarTodos() {
        return itemRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ItemResponseDTO buscarPorId(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado com id: " + id));
        return toResponseDTO(item);
    }

    public ItemResponseDTO criar(ItemRequestDTO dto) {
        Item item = new Item();
        preencherItem(item, dto);
        Item salvo = itemRepository.save(item);
        return toResponseDTO(salvo);
    }

    public ItemResponseDTO atualizar(Long id, ItemRequestDTO dto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado com id: " + id));
        preencherItem(item, dto);
        Item atualizado = itemRepository.save(item);
        return toResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("Item não encontrado com id: " + id);
        }
        itemRepository.deleteById(id);
    }

    public List<ItemResponseDTO> filtrar(EstadoConservacao estado, TipoRecipiente tipo) {
        List<Item> itens;
        if (estado != null && tipo != null) {
            itens = itemRepository.findByEstadoAndTipo(estado, tipo);
        } else if (estado != null) {
            itens = itemRepository.findByEstado(estado);
        } else if (tipo != null) {
            itens = itemRepository.findByTipo(tipo);
        } else {
            itens = itemRepository.findAll();
        }

        return itens.stream().map(this::toResponseDTO).toList();
    }

    public DashboardDTO obterDashboard() {
        List<Item> todos = itemRepository.findAll();

        long total = todos.size();

        Map<String, Long> porTipo = todos.stream()
                .collect(Collectors.groupingBy(item -> item.getTipo().name(), Collectors.counting()));

        Map<String, Long> porEstado = todos.stream()
                .collect(Collectors.groupingBy(item -> item.getEstado().name(), Collectors.counting()));

        return new DashboardDTO(total, porTipo, porEstado);
    }

    public ValorAquisicaoDTO obterValorProtegido(Long id, String senhaInformada) {
        if (!passwordEncoder.matches(senhaInformada, senhaHash)) {
            throw new RuntimeException("Senha incorreta");
        }

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado com id: " + id));

        return new ValorAquisicaoDTO(item.getValorAquisicao());
    }

    private void preencherItem(Item item, ItemRequestDTO dto) {
        item.setNome(dto.nome());
        item.setTipo(dto.tipo());
        item.setVolumeMl(dto.volumeMl());
        item.setEstado(dto.estado());
        item.setOrigem(dto.origem());
        item.setObservacoes(dto.observacoes());
        item.setDataAquisicao(dto.dataAquisicao());
        item.setValorAquisicao(dto.valorAquisicao());

        if (dto.fabricanteId() != null) {
            Fabricante fabricante = fabricanteRepository.findById(dto.fabricanteId())
                    .orElseThrow(() -> new RuntimeException("Fabricante não encontrado com id: " + dto.fabricanteId()));
            item.setFabricante(fabricante);
        }
    }

    private ItemResponseDTO toResponseDTO(Item item) {
        return new ItemResponseDTO(
                item.getId(),
                item.getNome(),
                item.getTipo(),
                item.getVolumeMl(),
                item.getEstado(),
                item.getFabricante() != null ? item.getFabricante().getNome() : null,
                item.getOrigem(),
                item.getObservacoes(),
                item.getDataAquisicao(),
                item.getFotos().stream().map(foto -> foto.getUrl()).toList()
        );
    }
}