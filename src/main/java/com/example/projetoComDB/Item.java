package com.example.projetoComDB;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_ITENS")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Float price;

    @ManyToMany
    private List<Pedido> pedidoList = new ArrayList<>();

    public Item(String name, Float price) {
        this.name = name;
        this.price = price;
    }

    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @JsonIgnore
    public List<Pedido> getPedido() {
        return this.pedidoList;
    }

    public void addPedido(Pedido pedido) {
        this.pedidoList.add(pedido);
    }

    public void removePedido(Pedido pedido) {
        this.pedidoList.remove(pedido);
    }

    public Long getId() {
        return id;
    }

}
