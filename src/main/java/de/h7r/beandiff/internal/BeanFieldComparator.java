package de.h7r.beandiff.internal;

import com.google.common.base.Preconditions;

import de.h7r.beandiff.logger.DiffLogger;

/**
 *
 */
public abstract class BeanFieldComparator {

    protected DiffLogger logger = DiffLogger.NOOP_LOGGER;

    public abstract boolean compare (final Object left,
                                     final Object right);

    public DiffLogger getLogger () {
        return logger;
    }

    public void setLogger (final DiffLogger logger) {
        Preconditions.checkNotNull (logger, "Logger cannot be set to null");

        this.logger = logger;

    }

}
