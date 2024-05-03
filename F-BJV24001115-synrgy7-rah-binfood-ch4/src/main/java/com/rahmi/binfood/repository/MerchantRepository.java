package com.rahmi.binfood.repository;

import com.rahmi.binfood.model.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
    List<Merchant> findByOpen(boolean open);

    Page<Merchant> findAll(Pageable pageable);
}