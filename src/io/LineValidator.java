package io;

final class LineValidator {
  static void requireParts(String[] parts, int expected, String line) {
    if (parts.length < expected) {
      throw new IllegalArgumentException(
          "Недостаточно полей (" + parts.length + " < " + expected + ") в строке: " + line);
    }
  }

  private LineValidator() {}
}
