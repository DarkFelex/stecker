package ru.meridor.tools.plugin.impl;

import ru.meridor.tools.plugin.VersionComparator;
import ru.meridor.tools.plugin.VersionRelation;

import java.util.Optional;

import static ru.meridor.tools.plugin.VersionRelation.*;

public class DefaultVersionComparator implements VersionComparator {

    @Override
    public VersionRelation compare(Optional<String> required, Optional<String> actual) {
        if (!required.isPresent() || required.get().isEmpty()) {
            return IN_RANGE;
        }
        if (!actual.isPresent() || actual.get().isEmpty()) {
            return NOT_EQUAL;
        }

        String requiredVersion = required.get();
        String actualVersion = actual.get();

        VersionRange range = new VersionRange(requiredVersion);
        if (range.isValid()) {
            return range.contains(actualVersion) ? IN_RANGE : NOT_IN_RANGE;
        } else {
            int comparisonResult = actualVersion.compareTo(requiredVersion);
            if (comparisonResult > 0) {
                return GREATER_THAN;
            } else if (comparisonResult < 0) {
                return LESS_THAN;
            }
            return EQUAL;
        }
    }

}
