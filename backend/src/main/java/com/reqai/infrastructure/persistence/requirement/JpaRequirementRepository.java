package com.reqai.infrastructure.persistence.requirement;

import com.reqai.domain.requirement.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaRequirementRepository extends JpaRepository<Requirement, UUID> {}
