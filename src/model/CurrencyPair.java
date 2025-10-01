package model;

import java.util.Objects;

public final class CurrencyPair {
  private final String base;
  private final String quote;

  public CurrencyPair(String base, String quote) {
    this.base = base.toUpperCase();
    this.quote = quote.toUpperCase();
    if (this.base.equals(this.quote)) {
      throw new IllegalArgumentException("Коды валют должны различаться");
    }
  }

  public String base() {
    return base;
  }

  public String quote() {
    return quote;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CurrencyPair that)) return false;
    return base.equals(that.base) && quote.equals(that.quote);
  }

  @Override
  public int hashCode() {
    return Objects.hash(base, quote);
  }

  @Override
  public String toString() {
    return base + "/" + quote;
  }
}
