package com.rahmi.binfood.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_time")
    @LastModifiedDate
    private Date time;

    @Column(name = "destination_address")
    private String destinationAddress;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "id_user")
    private User user;

    private Boolean completed;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetail;
}