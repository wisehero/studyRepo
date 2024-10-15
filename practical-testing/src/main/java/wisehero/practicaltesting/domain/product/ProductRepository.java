package wisehero.practicaltesting.domain.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatus);
}
