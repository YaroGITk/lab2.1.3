package repository;

import model.ExchangeRate;
import java.util.List;

public interface ExchangeRepository {
  List<ExchangeRate> findAll();

  void add(ExchangeRate r);

  ExchangeRate removeAt(int index);

  int size();

  boolean isEmpty();
}
