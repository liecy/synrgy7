package com.rahmi.binfood.order_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "id_order")
    private Order order;

    private Integer quantity;

    @Column(name = "total_price")
    private Double totalPrice;

    // Hapus relasi ke Product jika tidak diperlukan
    // @ManyToOne(targetEntity = Product.class)
    // @JoinColumn(name = "id_product")
    // private Product product;
}