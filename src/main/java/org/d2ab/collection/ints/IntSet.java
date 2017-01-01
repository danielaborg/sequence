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

import org.d2ab.collection.Collectionz;
import org.d2ab.collection.chars.CharSet;
import org.d2ab.iterator.chars.CharIterator;
import org.d2ab.iterator.ints.IntIterator;

import java.util.Collection;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;

/**
 * A primitive specialization of {@link Set} for {@code int} values.
 */
public interface IntSet extends Set<Integer>, IntCollection {
	// TODO: Enable Strict checking

	@Override
	default boolean isEmpty() {
		return size() == 0;
	}

	@Override
	default void clear() {
		iterator().removeAll();
	}

	@Override
	default boolean add(Integer x) {
		return addInt(x);
	}

	@Override
	default boolean contains(Object o) {
		return o instanceof Integer && containsInt((int) o);
	}

	@Override
	default boolean remove(Object o) {
		return o instanceof Integer && removeInt((int) o);
	}

	@Override
	default Integer[] toArray() {
		return toArray(new Integer[size()]);
	}

	@Override
	default <T> T[] toArray(T[] a) {
		return Collectionz.toArray(this, a);
	}

	@Override
	default boolean addAll(Collection<? extends Integer> c) {
		return Collectionz.addAll(this, c);
	}

	@Override
	default boolean containsAll(Collection<?> c) {
		return Collectionz.containsAll(this, c);
	}

	@Override
	default boolean removeAll(Collection<?> c) {
		return Collectionz.removeAll(this, c);
	}

	@Override
	default boolean retainAll(Collection<?> c) {
		return Collectionz.retainAll(this, c);
	}

	@Override
	default Spliterator.OfInt spliterator() {
		return Spliterators.spliterator(iterator(), size(), Spliterator.DISTINCT);
	}

	@Override
	default CharSet asChars() {
		return new CharSet() {
			@Override
			public CharIterator iterator() {
				return CharIterator.from(IntSet.this.iterator());
			}

			@Override
			public int size() {
				return IntSet.this.size();
			}
		};
	}

	/**
	 * Base class for {@link IntSet} implementations.
	 */
	abstract class Base extends IntCollection.Base implements IntSet {
		public static IntSet create(int... ints) {
			return create(IntSortedSet.create(ints));
		}

		public static IntSet create(final IntSet set) {
			return new IntSet.Base() {
				@Override
				public IntIterator iterator() {
					return set.iterator();
				}

				@Override
				public int size() {
					return set.size();
				}

				@Override
				public boolean addInt(int x) {
					return set.addInt(x);
				}
			};
		}

		public boolean equals(Object o) {
			if (o == this)
				return true;
			if (!(o instanceof Set))
				return false;

			if (o instanceof IntSet) {
				IntSet that = (IntSet) o;
				return size() == that.size() && containsAllInts(that);
			} else {
				Set<?> that = (Set<?>) o;
				return size() == that.size() && containsAll(that);
			}
		}

		public int hashCode() {
			int hashCode = 0;
			for (IntIterator iterator = iterator(); iterator.hasNext(); )
				hashCode += Integer.hashCode(iterator.nextInt());
			return hashCode;
		}
	}
}
