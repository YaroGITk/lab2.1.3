package model;

import java.util.Objects;

public final class CurrencyPair {
  private final String currency1;
  private final String currency2;

  public CurrencyPair(String currency1, String currency2) {
    this.currency1 = currency1.toUpperCase();
    this.currency2 = currency2.toUpperCase();
    if (this.currency1.equals(this.currency2)) {
      throw new IllegalArgumentException("Коды валют должны различаться");
    }
  }

  public String base() {
    return currency1;
  }

  public String quote() {
    return currency2;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CurrencyPair that)) return false;
    return currency1.equals(that.currency1) && currency2.equals(that.currency2);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currency1, currency2);
  }

  @Override
  public String toString() {
    return currency1 + "/" + currency2;
  }
}
