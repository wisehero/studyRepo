package wisehero.settlementsystem.settlement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import wisehero.settlementsystem.settlement.entity.Settlement;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
}
