package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitor;

/**
 * This class represents a type expression, for the type of a fixed-size array of another type.
 * Example: array [ 10 ] of int
 * <p>
 * In this example, the base type of this expression, is the {@link NamedTypeExpression} with "int" as identifier.
 * The size of this array is defined by the literal 10.
 */
public class ArrayTypeExpression extends TypeExpression {
    public final TypeExpression baseType;
    public final int arraySize;

    /**
     * Creates a new node representing a type expression for the type of a fixed-size array.
     *
     * @param position  The position of the type expression in the source code.
     * @param baseType  The type expression of the elements type.
     * @param arraySize The amount of elements an array of this type can hold.
     */
    public ArrayTypeExpression(Position position, TypeExpression baseType, int arraySize) {
        super(position);
        this.baseType = baseType;
        this.arraySize = arraySize;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return formatAst("ArrayTypeExpression", baseType, arraySize);
    }
}
