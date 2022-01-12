package de.thm.mni.compilerbau.absyn;

import de.thm.mni.compilerbau.absyn.visitor.Visitable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This abstract class is the root in the hierarchy of AST classes.
 * <p>
 * Every part of the AST has to extend this class.
 */
public abstract class Node implements Visitable {
    public final Position position;

    Node(Position position) {
        this.position = position;
    }

    private static String formatAst(String name, List<String> arguments) {
        final var indent = 2;

        if (arguments.size() == 0) {
            return String.format("%s()", name);
        } else {
            return String.format("%s(\n%s)",
                    name,
                    indent(String.join(",\n", arguments), indent).stripTrailing());
        }
    }

    static String formatAst(String name, Object... arguments) {
        return formatAst(name, Arrays.stream(arguments).map(o -> o == null ? "NULL" : o.toString()).collect(Collectors.toList()));
    }

    private static String indent(String str, int indentation) {
        final char[] indentationChars = new char[indentation];
        Arrays.fill(indentationChars, ' ');

        final String indentationPrefix = String.valueOf(indentationChars);

        return str.lines().map(s -> indentationPrefix + s).collect(Collectors.joining("\n"));
    }
}
