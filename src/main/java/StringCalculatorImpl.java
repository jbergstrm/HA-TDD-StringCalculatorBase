public class StringCalculatorImpl implements StringCalculator {

    @Override
    public int add(String input) {
        return input.isEmpty() ? 0 : Integer.parseInt(input);
    }
}
