// app/App.java
package app;

import model.Bank;
import model.ExchangeQuote;
import model.ExchangeRate;
import exceptions.InvalidDataException;
import io.ExchangeParser;
import repository.implement.InMemoryExchangeRepository;
import service.AnalyticsService;
import service.ExchangeRateService;

import java.io.IOException;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    ExchangeParser parser = new ExchangeParser();
    AnalyticsService analytics = new AnalyticsService();
    InMemoryExchangeRepository repo = new InMemoryExchangeRepository();
    ExchangeRateService rateService = new ExchangeRateService(repo);

    try {
      List<ExchangeRate> rates = parser.parseExchangeRatesFromFile("test1.txt");
      rates.forEach(repo::add);
      List<Bank> banks = parser.parseBanksFromFile("banks.txt");
      List<ExchangeQuote> quotes = parser.parseQuotesFromFile("quotes.txt", banks);

      System.out.println("[БАНКИ]");
      banks.forEach(System.out::println);
      System.out.println("\n[КУРСЫ ВАЛЮТ]");
      repo.findAll().forEach(System.out::println);
      analytics
          .findBankWithMostClients(banks)
          .ifPresent(x -> System.out.println("\n[Банк с наибольшим кол-вом клиентов]\n" + x));
      analytics
          .findMinRate(repo.findAll())
          .ifPresent(x -> System.out.println("\n[Минимальный курс]\n" + x));
      System.out.println("\n[КОТИРОВКИ]");
      quotes.forEach(System.out::println);
      analytics
          .findBestQuote(quotes)
          .ifPresent(
              q -> {
                System.out.println("\n[ЛУЧШАЯ КОТИРОВКА]\n" + q);
              });

      new ConsoleMenu(rateService).loop();
    } catch (InvalidDataException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      System.out.println("Ошибка чтения файлов: " + e.getMessage());
    }
  }
}
