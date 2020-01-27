package com.bytesville;

import com.bytesville.domain.Stock;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import com.bytesville.repository.StockRepository;

@Controller("/stocks")
public class StockController {
  protected final StockRepository stockRepository;

  public StockController(final StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  @Get("/{id}")
  public Stock show(final Long id) {
    return stockRepository
        .findById(id)
        .orElse(null);
  }

  @Put("/{id}")
  public HttpResponse update(final Long id, @Body @Valid final Stock stock) {
    final Stock updatedStock = stockRepository.update(id, stock);

    return HttpResponse
        .noContent()
        .header(HttpHeaders.LOCATION, location(updatedStock.getId()).getPath());
  }

  @Get(value = "/list")
  public List<Stock> list() {
    return stockRepository.findAll();
  }

  @Post("/")
  public HttpResponse<Stock> save(@Body @Valid final Stock stock) {
    stockRepository.save(stock);

    return HttpResponse
        .created(stock)
        .headers(headers -> headers.location(location(stock.getId())));
  }

  @Delete("/{id}")
  public HttpResponse delete(final Long id) {
    stockRepository.deleteById(id);
    return HttpResponse.noContent();
  }

  protected URI location(final Long id) {
    return URI.create("/stocks/" + id);
  }

  protected URI location(final Stock stock) {
    return location(stock.getId());
  }
}
