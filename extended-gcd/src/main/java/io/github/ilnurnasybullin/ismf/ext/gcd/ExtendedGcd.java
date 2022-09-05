package io.github.ilnurnasybullin.ismf.ext.gcd;

public record ExtendedGcd<T extends Number>(T gcd, BezoutCoefficients<T> bezoutCoefficients) {
}
