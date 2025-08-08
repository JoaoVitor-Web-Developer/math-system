package com.joao.math_system.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "budgets")
@Getter
@Setter
public class Budget {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "client_name", nullable = false)
	private String clientName;

	@Column(name = "description")
	private String description;

	@Column(name = "total_area", nullable = false)
	private BigDecimal totalArea;

	@Column(name = "price_per_meter_at_creation", nullable = false)
	private BigDecimal pricePerMeterAtCreation;

	@Column(name = "total_price", nullable = false)
	private BigDecimal totalPrice;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
}