package com.cwagnello.calculator.parser;

public class Token {

    private Type type;
    private String value;

    Token(Type type, String value) {
        this.type = type;
        this.value = value;
        this.type.setRole(type);
    }

    enum Type {
        L_PAREN,
        R_PAREN,
        ADD,
        SUB,
        MULT,
        DIV,
        LET,
        INTEGER,
        VARIABLE;

        public Role role;
        public void setRole(Type type) {
            if (type == L_PAREN || type == R_PAREN) {
                this.role = Role.STRUCTURE;
            }
            else if (type == ADD || type == SUB || type == MULT || type == DIV) {
                this.role = Role.BINARY;
            }
            else if (type == LET) {
                this.role = Role.TRINARY;
            }
            else if (type == INTEGER || type == VARIABLE) {
                this.role = Role.IDENTIFIER;
            }
        }
    }
    enum Role {
        STRUCTURE,
        BINARY,
        TRINARY,
        IDENTIFIER;
    }

    @Override
    public String toString() {
        return "Token: [type: " + this.type() + ", value: " + this.value() + "]";
    }

    public Role role() {
        return this.type.role;
    }
    public Type type() {
        return this.type;
    }
    public String value() {
        return this.value;
    }
 }
