package com.klleriston.walletservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "assets")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String ticker;

    @Column(nullable = false)
    private Integer quantity; // Alterado para Integer

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePrice; // Alterado para BigDecimal para precisão em valores monetários

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal currentValue; // Alterado para BigDecimal

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false) // Especifica a chave estrangeira
    private Wallet wallet;
}
