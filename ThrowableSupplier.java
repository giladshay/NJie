/**
 * Interface for overriding Supplier and allowing it to throw errors.
 */
@FunctionalInterface
public interface ThrowableSupplier<T, E extends Error> {
    public T get() throws E;
}
