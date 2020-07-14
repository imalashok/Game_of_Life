class Predicate {

    @FunctionalInterface
    public interface TernaryIntPredicate {
        abstract boolean test(int x, int y, int z);
    }

    public static final TernaryIntPredicate allValuesAreDifferentPredicate = (x, y, z) -> x != y && x != z && y != z;

}