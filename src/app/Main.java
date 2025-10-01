package app;

import model.ExchangeRate;
import exceptions.InvalidDataException;
import io.ExchangeParser;
import model.Item;
import repository.implement.InMemoryExchangeRepository;
import service.ExchangeRateService;

import java.io.IOException;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    ExchangeParser parser = new ExchangeParser();
    InMemoryExchangeRepository repository = new InMemoryExchangeRepository();
    ExchangeRateService rateService = new ExchangeRateService(repository);

    try {
      List<Item> exchangeItems = new java.util.ArrayList<>();
      exchangeItems.addAll(parser.parseBanksFromFile("banks.txt"));
      exchangeItems.addAll(parser.parseExchangeRatesFromFile("test1.txt"));
      exchangeItems.addAll(parser.parseQuotesFromFile("quotes.txt"));

      exchangeItems.stream()
              .filter(ExchangeRate.class::isInstance)
              .map(ExchangeRate.class::cast)
              .forEach(repository::add);

      new ConsoleMenu(rateService).loop();
    } catch (InvalidDataException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      System.out.println("Ошибка чтения файлов: " + e.getMessage());
    }
  }
}
