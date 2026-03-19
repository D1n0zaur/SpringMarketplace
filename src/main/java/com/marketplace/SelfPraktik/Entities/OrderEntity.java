package com.marketplace.SelfPraktik.Entities;

import com.marketplace.SelfPraktik.Entities.Enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToMany
    @JoinTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id") ,inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<ProductEntity> orderedProducts = new ArrayList<>();

    @Column(name = "order_price", nullable = false)
    private BigDecimal orderPrice;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    // Конструктор
    public OrderEntity(UserEntity user, List<ProductEntity> orderedProducts, BigDecimal orderPrice, LocalDate date, OrderStatus status) {
        this.user = user;
        this.orderedProducts = orderedProducts;
        this.orderPrice = orderPrice;
        this.date = date;
        this.status = status;
    }

}
