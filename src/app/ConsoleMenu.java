package app;

import model.ExchangeRate;
import service.ExchangeRateService;
import util.DateFormats;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
  private final Scanner in = new Scanner(System.in);
  private final ExchangeRateService rates;

  public ConsoleMenu(ExchangeRateService rates) {
    this.rates = rates;
  }

  public void loop() {
    while (true) {
      System.out.print(
          "\n--- МЕНЮ ---\n1. Удалить курс\n2. Добавить курс\n3. Показать все курсы\n0. Выход\n> ");
      String sel = in.nextLine().trim();
      switch (sel) {
        case "0" -> {
          return;
        }
        case "1" -> handleDelete();
        case "2" -> handleAdd();
        case "3" -> printAll();
        default -> System.out.println("Неверный выбор!");
      }
    }
  }

  private void printAll() {
    List<ExchangeRate> all = rates.list();
    System.out.println("\n--- ВСЕ КУРСЫ ВАЛЮТ (" + all.size() + ") ---");
    for (int i = 0; i < all.size(); i++) System.out.println((i + 1) + ". " + all.get(i));
  }

  private void handleDelete() {
    if (rates.list().isEmpty()) {
      System.out.println("Список пуст!");
      return;
    }
    printAll();
    System.out.print("Номер курса для удаления: ");
    try {
      int idx = Integer.parseInt(in.nextLine().trim());
      ExchangeRate removed = rates.deleteByUiIndex(idx);
      System.out.println("Удалён: " + removed);
    } catch (NumberFormatException e) {
      System.out.println("Ошибка: введите целое число!");
    } catch (IllegalArgumentException e) {
      System.out.println("Ошибка: " + e.getMessage());
    }
  }

  private void handleAdd() {
    try {
      System.out.print("Код валюты 1 (USD): ");
      String c1 = in.nextLine().trim().toUpperCase();
      System.out.print("Код валюты 2 (EUR): ");
      String c2 = in.nextLine().trim().toUpperCase();
      System.out.print("Курс: ");
      double rate = Double.parseDouble(in.nextLine().trim());
      System.out.print("Дата (yyyy.MM.dd): ");
      Date d = DateFormats.DF.parse(in.nextLine().trim());
      ExchangeRate r = new ExchangeRate(c1, c2, rate, d);
      rates.add(r);
      System.out.println("Добавлен: " + r);
    } catch (NumberFormatException e) {
      System.out.println("Ошибка: курс должен быть числом");
    } catch (ParseException e) {
      System.out.println("Ошибка: дата должна быть в формате yyyy.MM.dd");
    } catch (IllegalArgumentException e) {
      System.out.println("Ошибка: " + e.getMessage());
    }
  }
}
