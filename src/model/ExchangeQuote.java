package model;

import java.util.Date;

public class ExchangeQuote extends ExchangeRate {
  private final double commission;

  public ExchangeQuote(
      String currency1, String currency2, double rate, Date date, double commission) {
    super(currency1, currency2, rate, date);
    this.commission = commission;
  }


  public double getCommission() {
    return commission;
  }

  public double rateWithCommission() {
    return getRate() * (1.0 + commission / 100.0);
  }

  @Override
  public String toString() {
    return super.toString()
            + String.format(java.util.Locale.US, " | комиссия=%.3f", commission) + "%"
            + String.format(java.util.Locale.US, " | курс c комиссией=%.6f", rateWithCommission());
  }
}
