package shiroi.stockengine.api.service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.num.DecimalNum;
import org.ta4j.core.num.Num;
import shiroi.stockengine.api.model.request.BarDto;

@Service
public class IndicatorService {

    public Map<String, Object> calculate(String timeframe, List<BarDto> bars) {
        BarSeries series = toSeries(timeframe, bars);

        ClosePriceIndicator close = new ClosePriceIndicator(series);
        HighPriceIndicator high = new HighPriceIndicator(series);
        LowPriceIndicator low = new LowPriceIndicator(series);
        VolumeIndicator vol = new VolumeIndicator(series);

        // Moving averages (custom impl to avoid version differences)
        double sma20 = sma(close, series.getEndIndex(), 20);
        double ema50 = ema(close, series.getEndIndex(), 50);
        double ema200 = ema(close, series.getEndIndex(), 200);

        // Oscillators
        RSIIndicator rsi14 = new RSIIndicator(close, 14);
        StochasticOscillatorKIndicator stochK14 = new StochasticOscillatorKIndicator(series, 14);

        // MACD
        MACDIndicator macd = new MACDIndicator(close, 12, 26);
        double macdSignal = ema(macd, series.getEndIndex(), 9);

        int last = series.getEndIndex();

        Map<String, Object> out = new HashMap<>();
        out.put("sma20", valueOf(sma20, 20));
        out.put("ema50", valueOf(ema50, 50));
        out.put("ema200", valueOf(ema200, 200));
        out.put("rsi14", valueAt(rsi14, last));
        out.put("stochK14", valueAt(stochK14, last));
        out.put("macd", valueAt(macd, last));
        out.put("macdSignal", valueOf(macdSignal, 9));

        // Support/Resistance rough proxies
        out.put("recentHigh20", valueAt(high, last, 20, true));
        out.put("recentLow20", valueAt(low, last, 20, false));
        out.put("avgVolume20", valueAt(vol, last, 20));

        return out;
    }

    private Map<String, Object> valueAt(VolumeIndicator indicator, int last, int period) {
        double sum = 0d;
        int from = Math.max(0, last - period + 1);
        for (int i = from; i <= last; i++) {
            sum += indicator.getValue(i).doubleValue();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("value", sum / (last - from + 1));
        map.put("period", last - from + 1);
        return map;
    }

    private Map<String, Object> valueAt(org.ta4j.core.Indicator<Num> indicator, int index) {
        Map<String, Object> map = new HashMap<>();
        map.put("value", indicator.getValue(index).doubleValue());
        map.put("index", index);
        return map;
    }

    private Map<String, Object> valueOf(double value, int period) {
        Map<String, Object> map = new HashMap<>();
        map.put("value", value);
        map.put("period", period);
        return map;
    }

    private Map<String, Object> valueAt(org.ta4j.core.Indicator<Num> indicator, int last, int period, boolean isHigh) {
        double extremum = isHigh ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        int from = Math.max(0, last - period + 1);
        for (int i = from; i <= last; i++) {
            double v = indicator.getValue(i).doubleValue();
            extremum = isHigh ? Math.max(extremum, v) : Math.min(extremum, v);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("value", extremum);
        map.put("lookback", last - from + 1);
        return map;
    }

    private BarSeries toSeries(String timeframe, List<BarDto> bars) {
        BarSeries series = new BaseBarSeriesBuilder()
                .withName("series")
                .build();
        Duration barDur = parseTimeframe(timeframe);
        ZoneId zone = ZoneId.systemDefault();
        for (BarDto b : bars) {
            Instant end = Instant.ofEpochMilli(b.epochMillis());
            Instant begin = end.minus(barDur);
            Bar bar = new BaseBar(
                    barDur,
                    begin,
                    end,
                    DecimalNum.valueOf(b.open()),
                    DecimalNum.valueOf(b.high()),
                    DecimalNum.valueOf(b.low()),
                    DecimalNum.valueOf(b.close()),
                    DecimalNum.valueOf(b.volume()),
                    DecimalNum.valueOf(0),
                    0L
            );
            series.addBar(bar);
        }
        return series;
    }

    private Duration parseTimeframe(String tf) {
        if (tf == null || tf.isBlank()) return Duration.ofMinutes(1);
        try {
            String s = tf.trim().toLowerCase();
            char unit = s.charAt(s.length() - 1);
            long n = Long.parseLong(s.substring(0, s.length() - 1));
            return switch (unit) {
                case 's' -> Duration.ofSeconds(n);
                case 'm' -> Duration.ofMinutes(n);
                case 'h' -> Duration.ofHours(n);
                case 'd' -> Duration.ofDays(n);
                case 'w' -> Duration.ofDays(7 * n);
                default -> Duration.ofMinutes(Long.parseLong(s));
            };
        } catch (Exception e) {
            return Duration.ofMinutes(1);
        }
    }

    private double sma(ClosePriceIndicator close, int index, int period) {
        if (index < 0) return Double.NaN;
        int end = Math.min(index, close.getBarSeries().getEndIndex());
        if (end < 0) return Double.NaN;
        int from = Math.max(0, end - period + 1);
        double sum = 0d;
        for (int i = from; i <= end; i++) sum += close.getValue(i).doubleValue();
        return sum / (end - from + 1);
    }

    private double ema(ClosePriceIndicator close, int index, int period) {
        if (index < 0) return Double.NaN;
        int end = Math.min(index, close.getBarSeries().getEndIndex());
        if (end < 0) return Double.NaN;
        double k = 2.0 / (period + 1);
        int available = end + 1;
        int seedLen = Math.min(period, available);
        int seedStart = available - seedLen;
        double sum = 0d;
        for (int i = seedStart; i < seedStart + seedLen; i++) {
            sum += close.getValue(i).doubleValue();
        }
        double ema = sum / seedLen;
        for (int i = seedStart + 1; i <= end; i++) {
            double price = close.getValue(i).doubleValue();
            ema = price * k + ema * (1 - k);
        }
        return ema;
    }

    private double ema(org.ta4j.core.Indicator<Num> indicator, int index, int period) {
        if (index < 0) return Double.NaN;
        int end = Math.min(index, indicator.getBarSeries().getEndIndex());
        if (end < 0) return Double.NaN;
        double k = 2.0 / (period + 1);
        int available = end + 1;
        int seedLen = Math.min(period, available);
        int seedStart = available - seedLen;
        double sum = 0d;
        for (int i = seedStart; i < seedStart + seedLen; i++) {
            sum += indicator.getValue(i).doubleValue();
        }
        double ema = sum / seedLen;
        for (int i = seedStart + 1; i <= end; i++) {
            double v = indicator.getValue(i).doubleValue();
            ema = v * k + ema * (1 - k);
        }
        return ema;
    }
}
