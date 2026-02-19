package com.example.ECOMMERCE_APP.Repository;

import com.example.ECOMMERCE_APP.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    // Find all saved addresses for a specific user
    List<Address> findByUserId(Long userId);
}