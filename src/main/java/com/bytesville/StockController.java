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

  public StockController(StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  @Get("/{id}")
  public Stock show(Long id) {
    return stockRepository
        .findById(id)
        .orElse(null);
  }

  @Put("/{id}")
  public HttpResponse update(Long id, @Body @Valid Stock stock) {
    Stock updatedStock = stockRepository.update(id, stock);

    return HttpResponse
        .noContent()
        .header(HttpHeaders.LOCATION, location(updatedStock.getId()).getPath());
  }

  @Get(value = "/list{?args*}")
  public List<Stock> list(@Valid String ...args) {
    return stockRepository.findAll();
  }

  @Post("/")
  public HttpResponse<Stock> save(@Body @Valid Stock stock) {
    stockRepository.save(stock);

    return HttpResponse
        .created(stock)
        .headers(headers -> headers.location(location(stock.getId())));
  }

  @Delete("/{id}")
  public HttpResponse delete(Long id) {
    stockRepository.deleteById(id);
    return HttpResponse.noContent();
  }

  protected URI location(Long id) {
    return URI.create("/stocks/" + id);
  }

  protected URI location(Stock stock) {
    return location(stock.getId());
  }
}
