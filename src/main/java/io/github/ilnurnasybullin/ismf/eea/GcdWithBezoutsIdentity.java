package io.github.ilnurnasybullin.ismf.eea;

public record GcdWithBezoutsIdentity<T extends Number>(T gcd, BezoutsIdentity<T> bezoutsIdentity) {
}
