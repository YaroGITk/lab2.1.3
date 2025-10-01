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
        String[] parts = line.trim().split("\\s+");
        LineValidator.requireParts(parts, 4, line);
        out.add(new ExchangeRate(parts[0], parts[1], Double.parseDouble(parts[2]), DateFormats.DF.parse(parts[3])));
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
        String[] parts = line.trim().split("\\s+");
        LineValidator.requireParts(parts, 2, line);
        out.add(new Bank(parts[0], Long.parseLong(parts[1])));
      } catch (Exception e) {
        bad.add(line + " -> " + e.getMessage());
      }
    }
    if (!bad.isEmpty()) {
      throw new InvalidDataException("Ошибки в файле банков:\n" + String.join("\n", bad));
    }
    return out;
  }

  public List<ExchangeQuote> parseQuotesFromFile(String file) throws IOException {
    List<String> lines = Files.readAllLines(Path.of(file));
    List<ExchangeQuote> out = new ArrayList<>();
    List<String> bad = new ArrayList<>();
    for (String line : lines) {
      try {
        String[] parts = line.trim().split("\\s+");
        LineValidator.requireParts(parts, 6, line);
        out.add(
            new ExchangeQuote(
                parts[0],
                parts[1],
                Double.parseDouble(parts[2]),
                DateFormats.DF.parse(parts[3]),
                Double.parseDouble(parts[5])));
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
