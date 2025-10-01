package service;

import model.ExchangeRate;
import repository.ExchangeRepository;
import java.util.List;

public class ExchangeRateService {
  private final ExchangeRepository repo;

  public ExchangeRateService(ExchangeRepository repo) {
    this.repo = repo;
  }

  public List<ExchangeRate> list() {
    return repo.findAll();
  }

  public void add(ExchangeRate r) {
    repo.add(r);
  }

  public ExchangeRate deleteByUiIndex(int uiIndex) {
    if (uiIndex < 1 || uiIndex > repo.size()) {
      throw new IllegalArgumentException("Неверный номер записи");
    }
    return repo.removeAt(uiIndex - 1);
  }
}
