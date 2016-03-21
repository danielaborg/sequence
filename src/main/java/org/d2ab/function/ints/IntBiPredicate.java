/*
 * Copyright 2016 Daniel Skogquist Åborg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.d2ab.function.ints;

import java.util.function.BiPredicate;

/**
 * A specialization of {@link BiPredicate} for {@code int} values.
 */
@FunctionalInterface
public interface IntBiPredicate {
	/**
	 * Test this predicate against the given two {@code int}s.
	 */
	boolean test(int c1, int c2);

	/**
	 * Negate this predicate, returning a predicate that always returns the opposite values.
	 */
	default IntBiPredicate negate() {
		return (x1, x2) -> !test(x1, x2);
	}

	/**
	 * Combine this predicate with another predicate using "{@code and}" boolean logic.
	 */
	default IntBiPredicate and(IntBiPredicate predicate) {
		return (x1, x2) -> test(x1, x2) && predicate.test(x1, x2);
	}

	/**
	 * Combine this predicate with another predicate using "{@code or}" boolean logic.
	 */
	default IntBiPredicate or(IntBiPredicate predicate) {
		return (x1, x2) -> test(x1, x2) || predicate.test(x1, x2);
	}
}
