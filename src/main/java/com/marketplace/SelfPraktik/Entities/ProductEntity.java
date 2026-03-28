package com.marketplace.SelfPraktik.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    // Связи
    @ManyToMany(mappedBy = "orderedProducts")
    private List<OrderEntity> orders = new ArrayList<>();

    @ManyToMany(mappedBy = "products")
    private List<CartEntity> carts = new ArrayList<>();

    // Конструктор
    public ProductEntity(String name, String description, BigDecimal price, CategoryEntity category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    // Вспомогательные методы для синхронизации двусторонней связи
    public void addOrder(OrderEntity order) {
        orders.add(order);
        order.getOrderedProducts().add(this);
    }

    public void removeOrder(OrderEntity order) {
        orders.remove(order);
        order.getOrderedProducts().remove(this);
    }
}