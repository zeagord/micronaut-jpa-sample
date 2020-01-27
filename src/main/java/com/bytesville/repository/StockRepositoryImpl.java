package com.bytesville.repository;

import com.bytesville.domain.Stock;
import io.micronaut.spring.tx.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Singleton // <1>
public class StockRepositoryImpl implements StockRepository{

  @PersistenceContext
  private final EntityManager entityManager;  // <2>

  public StockRepositoryImpl(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional(readOnly = true) // <3>
  @Override public Optional<Stock> findById(@NotNull final Long id) {
    return Optional.ofNullable(entityManager.find(Stock.class, id));
  }

  @Transactional
  @Override public Stock save(@NotBlank final Stock s) {
    entityManager.persist(s);
    return s;
  }


  @Transactional
  @Override public void deleteById(@NotNull final Long id) {
    findById(id).ifPresent(stock -> entityManager.remove(stock));  }

  @Transactional
  @Override public List<Stock> findAll() {
    return entityManager.createQuery("SELECT s FROM Stock as s").getResultList();
  }

  @Transactional
  @Override public @NotBlank Stock update(@NotNull final Long id, @NotBlank final Stock s) {
    s.setId(id);
    return entityManager.merge(s);
  }
}
