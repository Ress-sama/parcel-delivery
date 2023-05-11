package com.guava.parcelservice.repository;

import com.guava.parcelservice.domain.Parcel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Integer> {
    Page<Parcel> findAllByUserId(Pageable pageable, Integer userId);
}
