package com.example.projetoComDB;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="TB_PEDIDO")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "pedido")
    private List<Item> itemList = new ArrayList<>();

    public Pedido(String name) {
        this.name = name;
    }

    public Pedido() {
    }

    public Pedido(String name, User user) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void addItem(Item item){
        this.itemList.add(item);
    }
    public void removeItem(Item item){
        this.itemList.remove(item);
    }
}
