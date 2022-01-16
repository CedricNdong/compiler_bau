package de.thm.mni.compilerbau.phases._04a_tablebuild;

import de.thm.mni.compilerbau.absyn.*;
import de.thm.mni.compilerbau.absyn.visitor.DoNothingVisitor;
import de.thm.mni.compilerbau.absyn.visitor.Visitor;
import de.thm.mni.compilerbau.table.*;
import de.thm.mni.compilerbau.types.ArrayType;
import de.thm.mni.compilerbau.types.Type;
import de.thm.mni.compilerbau.utils.SplError;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to create and populate a {@link SymbolTable} containing entries for every symbol in the currently
 * compiled SPL program.
 * Every declaration of the SPL program needs its corresponding entry in the {@link SymbolTable}.
 * <p>
 * Calculated {@link Type}s can be stored in and read from the dataType field of the {@link Expression},
 * {@link TypeExpression} or {@link Variable} classes.
 */
public class TableBuilder {
    private final boolean showTables;

    public TableBuilder(boolean showTables) {
        this.showTables = showTables;
    }

    public SymbolTable buildSymbolTable(Program program) {
        SymbolTable table = TableInitializer.initializeGlobalTable();
        Visitor visitor = new VisiteA(table);
        program.accept(visitor);
        Entry mainEntry = table.lookup(new Identifier("main"));
        if((mainEntry) == null){
            throw SplError.MainIsMissing();
        }else {
            if(!(mainEntry instanceof  ProcedureEntry)){
                throw SplError.MainIsNotAProcedure();
            }
        }
        return table;
    }

    /**
     * Prints the local symbol table of a procedure together with a heading-line
     * NOTE: You have to call this after completing the local table to support '--tables'.
     *
     * @param name  The name of the procedure
     * @param entry The entry of the procedure to print
     */
    private static void printSymbolTableAtEndOfProcedure(Identifier name, ProcedureEntry entry) {
        System.out.format("Symbol table at end of procedure '%s':\n", name);
        System.out.println(entry.localTable.toString());
    }

    private class VisiteA extends DoNothingVisitor {
        SymbolTable upperLevelTable;
        public VisiteA(SymbolTable upperLevelTable){
            this.upperLevelTable = upperLevelTable;
        }



        @Override
        public void visit(ParameterDeclaration parameterDeclaration) {
            parameterDeclaration.typeExpression.accept(this);
            if(parameterDeclaration.typeExpression.dataType instanceof ArrayType){
                if (!(parameterDeclaration.isReference)){
                    throw SplError.MustBeAReferenceParameter(parameterDeclaration.position,parameterDeclaration.name);
                }
            }
            Entry varEntry = new VariableEntry(parameterDeclaration.typeExpression.dataType, parameterDeclaration.isReference);
            System.out.println("param dec visited");
            upperLevelTable.enter(parameterDeclaration.name,varEntry,SplError.RedeclarationAsParameter(parameterDeclaration.position,parameterDeclaration.name));
        }


        @Override
        public void visit(TypeDeclaration typeDeclaration) {
            typeDeclaration.typeExpression.accept(this);
            System.out.println(" type dec visited");
            Entry entry = new TypeEntry(typeDeclaration.typeExpression.dataType);
            upperLevelTable.enter(typeDeclaration.name,entry,SplError.RedeclarationAsType(typeDeclaration.position,typeDeclaration.name));

        }


        @Override
        public void visit(ArrayTypeExpression arrayTypeExpression){
            arrayTypeExpression.baseType.accept(this);
            arrayTypeExpression.dataType = new ArrayType(arrayTypeExpression.baseType.dataType,arrayTypeExpression.arraySize);

        }

        public void visit(NamedTypeExpression namedTypeExpression) {
            Entry entry = upperLevelTable.lookup(namedTypeExpression.name);
            if (entry == null) {
                throw SplError.UndefinedType(namedTypeExpression.position, namedTypeExpression.name);
            } else {
                if (entry instanceof TypeEntry) {
                    namedTypeExpression.dataType = ((TypeEntry) entry).type;
                } else {
                    throw SplError.NotAType(namedTypeExpression.position, namedTypeExpression.name);
                }
            }
        }

        @Override
        public void visit(ProcedureDeclaration procedureDeclaration) {
            System.out.println("procedure dec visited");
            if(procedureDeclaration.name.toString().equals("main")){
                if(!(procedureDeclaration.parameters).isEmpty()){
                    throw SplError.MainMustNotHaveParameters();
                }
            }
            SymbolTable procSymTable = new SymbolTable(upperLevelTable);
            VisiteA thisProcVisitor = new VisiteA(procSymTable);
            for (ParameterDeclaration pd : procedureDeclaration.parameters) {
                pd.accept(thisProcVisitor);
            }

            for(VariableDeclaration vd : procedureDeclaration.variables){
                vd.accept(thisProcVisitor);
            }
            List<ParameterType> parameterTypeList = new ArrayList<>();
            for(ParameterDeclaration pd: procedureDeclaration.parameters ){
                parameterTypeList.add(new ParameterType(pd.typeExpression.dataType, pd.isReference));
            }
            ProcedureEntry proEntry= new ProcedureEntry(procSymTable,parameterTypeList);
            upperLevelTable.enter(procedureDeclaration.name,proEntry,SplError.RedeclarationAsProcedure(procedureDeclaration.position,procedureDeclaration.name));

            if(showTables){
                printSymbolTableAtEndOfProcedure(
                        procedureDeclaration.name,proEntry
                );
            }

        }

        public void visit(VariableDeclaration variableDeclaration) {
            System.out.println("variable declaration");
            variableDeclaration.typeExpression.accept(this);
            Entry varEntry = new VariableEntry(variableDeclaration.typeExpression.dataType,false);
            upperLevelTable.enter(variableDeclaration.name,varEntry,SplError.RedeclarationAsVariable(variableDeclaration.position,variableDeclaration.name));
        }

        @Override
        public void visit(Program program) {
            System.out.println("program visited");
            for (GlobalDeclaration gb: program.declarations) {
                gb.accept(this);
            }
        }

    }
}