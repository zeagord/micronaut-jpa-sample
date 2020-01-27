package com.bytesville.repository;

import com.bytesville.domain.Stock;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface StockRepository {
  Optional<Stock> findById(@NotNull Long id);

  Stock save(@NotBlank Stock s);

  void deleteById(@NotNull Long id);

  List<Stock> findAll();

  @NotBlank Stock update(@NotNull Long id, @NotBlank Stock s);
}
