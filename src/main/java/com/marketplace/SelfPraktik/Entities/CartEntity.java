package com.marketplace.SelfPraktik.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @ManyToMany
    @JoinTable(
            name = "cart_products",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> products = new ArrayList<>();

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    // Конструктор
    public CartEntity(UserEntity user) {
        this.user = user;
        this.totalPrice = BigDecimal.ZERO;
    }

    // Вспомогательные методы
    public void addProduct(ProductEntity product) {
        this.products.add(product);
        this.totalPrice = this.totalPrice.add(product.getPrice());
    }

    public boolean removeProduct(ProductEntity product) {
        boolean removed = this.products.remove(product);
        if (removed) {
            this.totalPrice = this.totalPrice.subtract(product.getPrice());
        }
        return removed;
    }

    public void clear() {
        products.clear();
        totalPrice = BigDecimal.ZERO;
    }
}