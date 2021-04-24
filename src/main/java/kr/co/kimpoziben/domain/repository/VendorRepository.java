package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
