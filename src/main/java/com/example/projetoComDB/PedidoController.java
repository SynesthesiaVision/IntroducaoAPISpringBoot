package com.example.projetoComDB;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public PedidoController(PedidoRepository pedidoRepository, UserRepository userRepository,
                            ItemRepository itemRepository) {
        this.pedidoRepository = pedidoRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Pedido> createPedido(@RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Pedido pedido = new Pedido();
        pedido.setUser(userOptional.get());
        userOptional.get().addPedido(pedido);

        pedidoRepository.save(pedido);
        userRepository.save(userOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(pedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);

        if (pedidoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(pedidoOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Pedido>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        pedidoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(@RequestParam Long pedidoId, @RequestParam Long itemId) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedidoId);
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        pedidoOptional.get().addItem(itemOptional.get());
        itemOptional.get().addPedido(pedidoOptional.get());
        itemRepository.save(itemOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(pedidoRepository.save(pedidoOptional.get()));
    }

    @PostMapping("/removeItem")
    public ResponseEntity<?> removeItem(@RequestParam Long pedidoId, @RequestParam Long itemId) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedidoId);
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        pedidoOptional.get().removeItem(itemOptional.get());

        itemOptional.get().removePedido(pedidoOptional.get());
        itemRepository.save(itemOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body(pedidoRepository.save(pedidoOptional.get()));

    }
}

