package de.thm.mni.compilerbau.absyn;

/**
 * This class is the abstract superclass of every statement in SPL.
 * <p>
 * There exist many different statements present in SPL, which may all occur in the body of a procedure.
 */
public abstract class Statement extends Node {
    public Statement(Position position) {
        super(position);
    }
}
