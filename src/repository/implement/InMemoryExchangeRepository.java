package repository.implement;

import model.ExchangeRate;
import repository.ExchangeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryExchangeRepository implements ExchangeRepository {
  private final List<ExchangeRate> data = new ArrayList<>();

  @Override
  public List<ExchangeRate> findAll() {
    return Collections.unmodifiableList(data);
  }

  @Override
  public void add(ExchangeRate rate) {
    data.add(rate);
  }

  @Override
  public ExchangeRate removeAt(int index) {
    return data.remove(index);
  }

  @Override
  public int size() {
    return data.size();
  }

  @Override
  public boolean isEmpty() {
    return data.isEmpty();
  }
}
