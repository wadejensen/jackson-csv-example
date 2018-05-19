package com.wadejensen;

import java.util.function.Function;

/**
 * Unfortunately the best way to subvert the checked exceptions system in Java.
 * Wrap method references (which throws checked exceptions) with ThrowingFunction.unchecked
 * to enable those methods to be called as lambdas.
 *
 * https://github.com/pivovarit/articles/tree/master/sneaky-throws-lambda
 * http://4comprehension.com/sneakily-throwing-exceptions-in-lambda-expressions-in-java/
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 */
public interface ThrowingFunction<T, R> {
    R apply(T t) throws Exception;

    @SuppressWarnings("unchecked")
    static <T extends Exception, R> R sneakyThrow(Exception t) throws T {
        throw (T) t;
    }

    static <T, R> Function<T, R> unchecked(ThrowingFunction<T, R> f) {
        return t -> {
            try {
                return f.apply(t);
            } catch (Exception ex) {
                return ThrowingFunction.sneakyThrow(ex);
            }
        };
    }
}
