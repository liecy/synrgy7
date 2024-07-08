package com.rahmi.binfood.model;

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

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "id_product")
    private Product product;

    private Integer quantity;

    @Column(name = "total_price")
    private Double totalPrice;

}