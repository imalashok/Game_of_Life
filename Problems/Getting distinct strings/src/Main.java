import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;


class Operator {

    public static UnaryOperator<List<String>> unaryOperator = list -> new ArrayList<>(new HashSet<>(list));
}