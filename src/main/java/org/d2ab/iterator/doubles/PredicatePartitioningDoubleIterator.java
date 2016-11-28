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

package org.d2ab.iterator.doubles;

import org.d2ab.collection.doubles.DoubleList;
import org.d2ab.function.DoubleBiPredicate;
import org.d2ab.iterator.DelegatingTransformingIterator;
import org.d2ab.iterator.chars.CharIterator;
import org.d2ab.sequence.DoubleSequence;

import java.util.NoSuchElementException;

/**
 * A {@link CharIterator} that can batch up another iterator by comparing two items in sequence and deciding whether
 * to split up in a batch on those items.
 */
public class PredicatePartitioningDoubleIterator extends
                                                 DelegatingTransformingIterator<Double, DoubleIterator, DoubleSequence> {
	private final DoubleBiPredicate predicate;
	private double next;
	private boolean hasNext;

	public PredicatePartitioningDoubleIterator(DoubleIterator iterator, DoubleBiPredicate predicate) {
		super(iterator);
		this.predicate = predicate;
	}

	@Override
	public boolean hasNext() {
		if (!hasNext && super.hasNext()) {
			next = iterator.nextDouble();
			hasNext = true;
		}
		return hasNext;
	}

	@Override
	public DoubleSequence next() {
		if (!hasNext())
			throw new NoSuchElementException();

		DoubleList buffer = DoubleList.create();
		do {
			buffer.addDouble(next);

			hasNext = iterator.hasNext();
			if (!hasNext)
				break;

			double following = iterator.nextDouble();
			boolean split = predicate.test(next, following);
			next = following;
			if (split)
				break;
		} while (hasNext);

		return DoubleSequence.from(buffer);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
