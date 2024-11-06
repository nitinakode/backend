package com.exemple.demo_product.q;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private Double price;
    private String category_id;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Variant> variants;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product",orphanRemoval = true)
    private Set<Image> images;

    private String createdBy;
    private String updatedBy;
    private String status;

    public void setvariants(Set<Variant> variants,Set<Image> images) {
        if (this.variants == null) {
            this.variants = new HashSet<>();
        }
        if(this.images==null)
        {
            this.images=new HashSet<>();
        }
        this.variants.clear();
        this.images.clear();
        this.variants.addAll(variants);
        this.images.addAll(images);

    }

    public void addReference() {
        if (this.variants != null) {
            this.variants.forEach(
                    variant -> variant.setProduct(this));
        }
        if(this.images!=null)
        {
            this.images.forEach(
                    image -> image.setProduct(this));
        }
    }

}
