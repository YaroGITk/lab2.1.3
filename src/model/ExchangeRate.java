package model;

import java.util.Date;
import java.util.Objects;

public class ExchangeRate extends Item {
  private final CurrencyPair pair;
  private final double rate;
  private final Date date;

  public ExchangeRate(String currency1, String currency2, double rate, Date date) {
    this(new CurrencyPair(currency1, currency2), rate, date);
  }

  public ExchangeRate(CurrencyPair pair, double rate, Date date) {
    if (rate <= 0) throw new IllegalArgumentException("Курс должен быть > 0");
    this.pair = pair;
    this.rate = rate;
    this.date = date;
  }

  public CurrencyPair getPair() {
    return pair;
  }

  public String getCurrency1() {
    return pair.base();
  }

  public String getCurrency2() {
    return pair.quote();
  }

  public double getRate() {
    return rate;
  }

  public Date getDate() {
    return date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ExchangeRate that)) return false;
    return Double.compare(that.rate, rate) == 0
        && Objects.equals(pair, that.pair)
        && Objects.equals(date, that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pair, rate, date);
  }

  @Override
  public String toString() {
    return date + ": курс " + pair + " = " + rate;
  }
}
