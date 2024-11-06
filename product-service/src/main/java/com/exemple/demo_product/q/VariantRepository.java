package com.exemple.demo_product.q;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VariantRepository extends JpaRepository<Variant, String> {
}