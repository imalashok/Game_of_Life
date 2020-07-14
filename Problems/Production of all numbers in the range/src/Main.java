import java.util.function.*;


class Operator {

    public static LongBinaryOperator binaryOperator = (x, y) -> {
        for (long i = x + 1; i <= y; i++) {
            x *= i;
        }
        return x;
    };
}