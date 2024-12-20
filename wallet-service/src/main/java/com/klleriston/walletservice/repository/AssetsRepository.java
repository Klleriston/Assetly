package com.klleriston.walletservice.repository;

import com.klleriston.walletservice.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetsRepository extends JpaRepository<Asset, Long> {
}
