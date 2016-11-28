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

package org.d2ab.collection.ints;

import org.d2ab.iterator.ints.ChainingIntIterator;
import org.d2ab.iterator.ints.IntIterator;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.asList;

/**
 * An {@link org.d2ab.collection.ints.IntIterable} that can chain together several {@link org.d2ab.collection.ints.IntIterable}s into one unbroken sequence.
 */
public class ChainingIntIterable implements org.d2ab.collection.ints.IntIterable {
	private final Collection<org.d2ab.collection.ints.IntIterable> iterables = new ArrayList<>();

	public ChainingIntIterable(org.d2ab.collection.ints.IntIterable... iterables) {
		asList(iterables).forEach(this.iterables::add);
	}

	public ChainingIntIterable append(org.d2ab.collection.ints.IntIterable iterable) {
		iterables.add(iterable);
		return this;
	}

	@Override
	public IntIterator iterator() {
		return new ChainingIntIterator(iterables);
	}

	@Override
	public int hashCode() {
		return iterables.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if ((o == null) || (getClass() != o.getClass()))
			return false;

		ChainingIntIterable that = (ChainingIntIterable) o;

		return iterables.equals(that.iterables);
	}

	@Override
	public String toString() {
		return "ChainingIntIterable" + iterables;
	}
}