package io.HCL.ppmtool.repositories;

import io.HCL.ppmtool.domain.ProjectTask;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

	List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);
	
	List<ProjectTask> findByProjectIdentifier(String id, Sort sort);
	
	List<ProjectTask> findByProjectIdentifierOrderByProjectSequence(String id);

	ProjectTask findByProjectSequence(String sequence);
}
