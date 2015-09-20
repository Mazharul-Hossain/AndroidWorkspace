package org.icepdf.core.util;

import java.util.Map;
import org.icepdf.core.pobjects.Name;

/**
 * Custom version of the Hashtable
 *
 * @author Joerg Jahnke (joergjahnke@users.sourceforge.net)
 */
public final class Hashtable<K, V> extends java.util.Hashtable<K, V> {

    public Hashtable() {
        super();
    }

    public Hashtable(final int initialCapacity) {
        super(initialCapacity);
    }

    public Hashtable(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public Hashtable(final Map<? extends K, ? extends V> t) {
        super();
        putAll(t);
    }

    @Override
    public V put(final K key, final V value) {
        if (key instanceof Name) {
            super.put((K)key.toString(), value);
        }
        return super.put(key, value);
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> t) {
        super.putAll(t);
        for (final K o : t.keySet()) {
            if (o instanceof Name) {
                super.put((K)o.toString(), t.get(o));
            }
        }
    }
}
