package com.rahmi.binfood.product_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_name")
    private String name;

    private Double price;

    private Integer quantity;

    @ManyToOne(targetEntity = Merchant.class)
    @JoinColumn(name = "id_merchant")
    private Merchant merchant;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    private List<OrderDetail> orderDetail;
}
