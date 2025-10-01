package util;

import java.text.SimpleDateFormat;
import java.util.Locale;

public final class DateFormats {
  public static final SimpleDateFormat DF = new SimpleDateFormat("yyyy.MM.dd", Locale.US);

  private DateFormats() {}
}
