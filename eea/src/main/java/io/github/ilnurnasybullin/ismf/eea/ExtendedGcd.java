package io.github.ilnurnasybullin.ismf.eea;

public record ExtendedGcd<T extends Number>(T gcd, BezoutCoefficients<T> bezoutCoefficients) {
}
