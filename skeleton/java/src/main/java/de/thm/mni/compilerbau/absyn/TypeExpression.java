package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.types.Type;

/**
 * This class is the abstract superclass of all type expressions in SPL.
 * <p>
 * A type expression is either a {@link NamedTypeExpression} or an {@link ArrayTypeExpression}.
 * They behave like a formula representing a concrete semantic {@link Type} which has to be calculated
 * during phase 4.
 */
public abstract class TypeExpression extends Node {
    public Type dataType = null;

    public TypeExpression(Position position) {
        super(position);
    }
}
