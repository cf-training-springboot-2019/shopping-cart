package com.trainingspringboot.shoppingcart.service;

import java.util.List;
import org.springframework.data.domain.Page;

public interface ICrudService<T extends Object> {

	Page<T> list(int page, int size);

	List<T> list();

	T get(Long id);

	void delete(Long id);

	T update(T entity);

	T save(T entity);

}
