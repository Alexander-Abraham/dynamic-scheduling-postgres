package com.effectivejava.scheduling.service.repository;



import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.effectivejava.scheduling.service.entity.QueryEntity;

@Repository
public interface SchedulerRepository extends JpaRepository<QueryEntity, Integer>{

	@Transactional
	@Modifying
	@Query("UPDATE QueryEntity SET QUERY=:query, FREQUENCY=:frequency WHERE ID =:id")
	public void updateById(@Param("id") int id, @Param("query") String query, @Param("frequency") int frequency);
}
