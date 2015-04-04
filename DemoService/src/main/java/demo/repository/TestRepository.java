package demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import demo.entity.Test;

@Repository
public interface TestRepository extends
		CrudRepository<Test, Integer> {

}
