package com.bytesville.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Stock")
public class Stock {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public Long id;
  public String ticker;
  public String Name;
  public BigDecimal lastTradedPrice;
}
