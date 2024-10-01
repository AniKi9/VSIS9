package com.prc.vsis9.repository;

import com.prc.vsis9.data.ShawermaOrder;
import org.springframework.data.repository.CrudRepository;
public interface OrderRepository extends CrudRepository<ShawermaOrder, Long> {

}
