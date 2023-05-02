import java.util.Arrays;

public class StringCalculatorImpl implements StringCalculator {

    @Override
    public int add(String input) {
        return input.isEmpty() ? 0 : Arrays
                .stream(input.split(","))
                .mapToInt(Integer::parseInt)
                .sum();
    }
}
