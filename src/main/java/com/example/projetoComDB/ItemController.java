package com.example.projetoComDB;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        return ResponseEntity.status(HttpStatus.OK).body(itemRepository.save(item));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findById(@PathVariable Long id) {
        Optional<Item> itemOptional = itemRepository.findById(id);

        if (itemOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(itemOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Item>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(itemRepository.findAll());
    }

    @PostMapping("/update")
    public ResponseEntity<Item> update(@RequestBody Item item) {
        Optional<Item> itemOptional = itemRepository.findById(item.getId());
        if (itemOptional.isPresent()) {
            Item item1 = itemOptional.get();

            item1.setName(item.getName());
            item1.setPrice(item.getPrice());

            return ResponseEntity.status(HttpStatus.OK).body(itemRepository.save(item1));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
