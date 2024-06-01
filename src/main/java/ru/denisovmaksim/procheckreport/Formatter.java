package ru.denisovmaksim.procheckreport;

import ru.denisovmaksim.procheckreport.model.Check;

import java.util.List;
import java.util.OptionalInt;

public class Formatter {
    private static StringBuilder builder = new StringBuilder();

    public static String format(List<Check> checks) {
        int sizeName = checks.stream().mapToInt(check -> check.getName().length()).max().orElse(0);
        builder.append(printHeader(sizeName));
        checks.stream()
                .map(check -> printRow(check, sizeName))
                .forEach(str -> builder.append(str));
        builder.append(printFooter(sizeName));
        return builder.toString();
    }

    private static String printRow(Check check, int sizeName) {
        String name = check.getName() + " ".repeat(sizeName - check.getName().length());
        return String.format("|%s|%s|%s|\n",
                name,
                String.format("%10d", check.getQuantity()),
                String.format("%8.2f", check.getSum() / 100.0));
    }

    private static String printHeader(int sizeName) {
        return "_".repeat(sizeName + 10 * 2 + 4) + "\n";
    }

    private static String printFooter(int sizeName) {
        return "_".repeat(sizeName + 10 * 2 + 4) + "\n";
    }
}
