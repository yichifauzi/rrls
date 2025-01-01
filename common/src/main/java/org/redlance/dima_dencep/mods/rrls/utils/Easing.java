/*
 * Copyright 2023 - 2024 dima_dencep.
 *
 * Licensed under the Open Software License, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     https://spdx.org/licenses/OSL-3.0.txt
 */

package org.redlance.dima_dencep.mods.rrls.utils;

import java.util.function.Function;

public class Easing {
    public static float sine(float n) {
        return 1 - (float) Math.cos(n * Math.PI / 2F);
    }

    public static float cubic(float n) {
        return n * n * n;
    }

    public static float quadratic(float n) {
        return n * n;
    }

    public static Function<Float, Float> pow(float n) {
        return t -> (float) Math.pow(t, n);
    }

    public static float exp(float n) {
        return (float) Math.pow(2, 10 * (n - 1));
    }

    public static float circle(float n) {
        return 1 - (float) Math.sqrt(1 - n * n);
    }

    public static Function<Float, Float> back(Float n) {
        final float n2 = n == null ? 1.70158F : n * 1.70158F;

        return t -> t * t * ((n2 + 1) * t - n2);
    }

    public static Function<Float, Float> elastic(Float n) {
        float n2 = n == null ? 1 : n;

        return t -> (float) (1 - Math.pow(Math.cos(t * Math.PI / 2f), 3) * Math.cos(t * n2 * Math.PI));
    }

    public static Function<Float, Float> bounce(Float n) {
        final float n2 = n == null ? 0.5F : n;

        Function<Float, Float> one = x -> 121f / 16f * x * x;
        Function<Float, Float> two = x -> (float) (121f / 4f * n2 * Math.pow(x - 6f / 11f, 2) + 1 - n2);
        Function<Float, Float> three = x -> (float) (121 * n2 * n2 * Math.pow(x - 9f / 11f, 2) + 1 - n2 * n2);
        Function<Float, Float> four = x -> (float) (484 * n2 * n2 * n2 * Math.pow(x - 10.5f / 11f, 2) + 1 - n2 * n2 * n2);

        return t -> Math.min(Math.min(one.apply(t), two.apply(t)), Math.min(three.apply(t), four.apply(t)));
    }

    public static Function<Float, Float> step(Float n) {
        float n2 = n == null ? 2 : n;

        if (n2 < 2)
            throw new IllegalArgumentException("Steps must be >= 2, got: " + n2);

        final int steps = (int)n2;

        return t -> {
            float result = 0;

            if (t < 0)
                return result;

            float stepLength = (1 / (float)steps);

            if (t > (result = (steps - 1) * stepLength))
                return result;

            int testIndex;
            int leftBorderIndex = 0;
            int rightBorderIndex = steps - 1;

            while (rightBorderIndex - leftBorderIndex != 1) {
                testIndex = leftBorderIndex + (rightBorderIndex - leftBorderIndex) / 2;

                if (t >= testIndex * stepLength) {
                    leftBorderIndex = testIndex;
                }
                else {
                    rightBorderIndex = testIndex;
                }
            }

            return leftBorderIndex * stepLength;
        };
    }

    public static float catmullRom(float n) {
        return (0.5f * (2.0f * (n + 1) + ((n + 2) - n) * 1
                + (2.0f * n - 5.0f * (n + 1) + 4.0f * (n + 2) - (n + 3)) * 1
                + (3.0f * (n + 1) - n - 3.0f * (n + 2) + (n + 3)) * 1));
    }
}
