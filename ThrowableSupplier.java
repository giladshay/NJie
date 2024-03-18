@FunctionalInterface
public interface ThrowableSupplier<T, E extends Error> {
    public T get() throws E;
}
