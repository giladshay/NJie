/**
 * Interface for overriding BinaryOperator and allowing it to throw errors.
 * @author Gil-Ad Shay.
 */
@FunctionalInterface
public interface ThrowableBinaryOperator<T, E extends Error> {
    public T apply(T t1, T t2) throws E;
}
