package io;

import model.*;
import util.DateFormats;
import exceptions.InvalidDataException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class ExchangeParser {

  public List<ExchangeRate> parseExchangeRatesFromFile(String file) throws IOException {
    List<String> lines = Files.readAllLines(Path.of(file));
    List<ExchangeRate> out = new ArrayList<>();
    List<String> bad = new ArrayList<>();

    for (String line : lines) {
      try {
        String[] p = line.trim().split("\\s+");
        LineValidator.requireParts(p, 4, line);
        out.add(new ExchangeRate(p[0], p[1], Double.parseDouble(p[2]), DateFormats.DF.parse(p[3])));
      } catch (IllegalArgumentException | ParseException e) {
        bad.add(line + " -> " + e.getMessage());
      }
    }
    if (!bad.isEmpty()) {
      throw new InvalidDataException("Ошибки в файле курсов:\n" + String.join("\n", bad));
    }
    return out;
  }

  public List<Bank> parseBanksFromFile(String file) throws IOException {
    List<String> lines = Files.readAllLines(Path.of(file));
    List<Bank> out = new ArrayList<>();
    List<String> bad = new ArrayList<>();
    for (String line : lines) {
      try {
        String[] p = line.trim().split("\\s+");
        LineValidator.requireParts(p, 2, line);
        out.add(new Bank(p[0], Long.parseLong(p[1])));
      } catch (Exception e) {
        bad.add(line + " -> " + e.getMessage());
      }
    }
    if (!bad.isEmpty()) {
      throw new InvalidDataException("Ошибки в файле банков:\n" + String.join("\n", bad));
    }
    return out;
  }

  public List<ExchangeQuote> parseQuotesFromFile(String file, List<Bank> banks) throws IOException {
    Map<String, Bank> byName =
        banks.stream().collect(Collectors.toMap(b -> b.getName().toLowerCase(Locale.ROOT), b -> b));

    List<String> lines = Files.readAllLines(Path.of(file));
    List<ExchangeQuote> out = new ArrayList<>();
    List<String> bad = new ArrayList<>();
    for (String line : lines) {
      try {
        String[] p = line.trim().split("\\s+");
        LineValidator.requireParts(p, 6, line);
        Bank bank = byName.getOrDefault(p[4].toLowerCase(Locale.ROOT), new Bank(p[4], 0));
        out.add(
            new ExchangeQuote(
                p[0],
                p[1],
                Double.parseDouble(p[2]),
                DateFormats.DF.parse(p[3]),
                bank,
                Double.parseDouble(p[5])));
      } catch (Exception e) {
        bad.add(line + " -> " + e.getMessage());
      }
    }
    if (!bad.isEmpty()) {
      throw new InvalidDataException("Ошибки в файле котировок:\n" + String.join("\n", bad));
    }
    return out;
  }
}
