package com.example.mytravel.repository;
import com.example.mytravel.domain.TravelPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {
    List<TravelPackage> findByIsPopular(boolean isPopular);
}