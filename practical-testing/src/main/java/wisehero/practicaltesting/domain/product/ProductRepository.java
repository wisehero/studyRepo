package wisehero.practicaltesting.domain.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

	/**
	 * select * from product where selling_status in (sellingStatus)
	 * @param sellingStatus
	 * @return
	 */
	List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatus);

	/**
	 * select * from product where product_number in (productNumbers)
	 * @param productNumbers
	 * @return
	 */
	List<Product> findAllByProductNumberIn(List<String> productNumbers);

}
