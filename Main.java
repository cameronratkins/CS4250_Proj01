/*******************************************************
 Cameron Atkins
 CompSci 4250
 Project 01, Due: Friday February 10, 2023 by 11:59 PM


 In order for this program to work, copy
 and paste the source code into onlinegdb (Java) upload a file named
 front.in.
 *********************************************************/

import java.io.*;

public class Main {

    // Global declarations
    static int charClass;
    static StringBuffer lexeme = new StringBuffer();
    static char nextChar;
    static int lexLen;
    static int token;
    static int nextToken;
    static BufferedReader in;

    // Character classes
    static final int LETTER = 0;
    static final int DIGIT = 1;
    static final int KEYWORD = 2;
    static final int UNKNOWN = 99;

    // Token codes
    static final int INT_LIT = 10;
    static final int IDENT = 11;
    static final int ASSIGN_OP = 20;
    static final int ADD_OP = 21;
    static final int SUB_OP = 22;
    static final int MULT_OP = 23;
    static final int DIV_OP = 24;
    static final int LEFT_PAREN = 25;
    static final int RIGHT_PAREN = 26;
    static final int COMMA = 27;
    static final int SEMI_COLON = 28;
    static final int INT_KW = 29;
    static final int EOF = -1;



    public static void main(String[] args) throws IOException {
        /* Open the input data file and process its contents */
        try {
            in = new BufferedReader(new FileReader("front.in"));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR - cannot open front.in");
            return;
        }

        getChar();
        do {
            lex();
        } while (nextToken != EOF);
    }

    // lookup - a function to lookup operators and parentheses
    // and return the token
    static int lookup(char ch) {
        switch (ch) {
            case '(':
                addChar();
                nextToken = LEFT_PAREN;
                break;
            case ')':
                addChar();
                nextToken = RIGHT_PAREN;
                break;
            case '=':
                addChar();
                nextToken = ASSIGN_OP;
                break;
            case '+':
                addChar();
                nextToken = ADD_OP;
                break;
            case '-':
                addChar();
                nextToken = SUB_OP;
                break;
            case '*':
                addChar();
                nextToken = MULT_OP;
                break;
            case '/':
                addChar();
                nextToken = DIV_OP;
                break;
            case ',':
                addChar();
                nextToken = COMMA;
                break;
            case ';':
                addChar();
                nextToken = SEMI_COLON;
                break;
            default:
                addChar();
                nextToken = EOF;
                break;
        }
        return nextToken;
    }

    // addChar - a function to add nextChar to lexeme
    static void addChar() {
        lexeme.append(nextChar);
    }

    // getChar - a function to get the next character of
    // input and determine its character class
    static void getChar() throws IOException {
        int c = in.read();
        Character intkw = 'i';
        if (c == -1) {
            charClass = EOF;
        } else {
            nextChar = (char) c;
            if (Character.isLetter(nextChar)) {
                charClass = LETTER;
            } else if (Character.isDigit(nextChar)) {
                charClass = DIGIT;
            }
            else {
                charClass = UNKNOWN;
            }
        }
    }

    // getNonBlank - a function to call getChar until it
    // returns a non-whitespace character
    static void getNonBlank() throws IOException {
        while (Character.isWhitespace(nextChar))
            getChar();
    }

    static int lex() throws IOException{
        lexeme = new StringBuffer();
        getNonBlank();
        switch(charClass){
            //Parse Identifiers
            case LETTER:
                addChar();
                getChar();
                while  (charClass == LETTER || charClass == DIGIT) {
                    addChar();
                    getChar();
                }

                nextToken = IDENT;
                break;

            //Parse integer literals
            case DIGIT:
                addChar();
                getChar();
                while(charClass == DIGIT){
                    addChar();
                    getChar();
                }
                nextToken = INT_LIT;
                break;

            case KEYWORD:
                addChar();
                getChar();

                while(charClass == KEYWORD){
                    addChar();
                    getChar();
                }
                if(lexeme.equals("int"))
                    System.out.println("test");
                nextToken = INT_KW;
                break;

            //Parentheses and operators
            case UNKNOWN:
                lookup(nextChar);
                getChar();
                break;

            //EOF
            case EOF:
                nextToken = EOF;
                lexeme.append("EOF");
                break;

        }//end of switch
        System.out.printf("Next token is: %d, Next lexeme is %s\n",
                nextToken, lexeme);
        return nextToken;

    }//End of function lex
}//End of Main.java
