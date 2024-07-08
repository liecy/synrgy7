package com.rahmi.binfood.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "merchant")
public class Merchant extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "merchant_name")
    private String name;

    @Column(name = "merchant_location")
    private String location;

    @Builder.Default
    private Boolean open = false;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL)
    private List<Product> product;
}