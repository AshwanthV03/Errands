package com.Loop.Erands.Repository;

import com.Loop.Erands.Models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    @Query("SELECT a FROM Address a WHERE a.user.userId = :userId")
    public List<Address> findAllAddressByUserId( @Param("userId") UUID userId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Address a WHERE a.addressId = :addressId")
    void deleteAddressById(@Param("addressId") UUID addressId);
}
