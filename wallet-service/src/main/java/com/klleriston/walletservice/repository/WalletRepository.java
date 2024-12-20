package com.klleriston.walletservice.repository;

import com.klleriston.walletservice.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
