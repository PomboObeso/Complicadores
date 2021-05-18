package sintatico;

import lexico.EnumTokens;
import lexico.Lexicon;
import lexico.Token;

public class Sintatico {
    private Lexicon lexico;
    private Token token;
    private int scopeCounter = 0;
    private String epsilon = "e";

    public Sintatico(String a) {
        lexico = new Lexicon(a);
        setNextToken ();
        FS();
    }

    public void setNextToken() {
        token = lexico.nextToken();
        if(token.categoria == EnumTokens.EOF) {
            return;
        }
    }

    public boolean checkCategory(EnumTokens... categories) {
        for (EnumTokens category: categories) {
            if (token.categoria == category) {
                return true;
            }
        }
        return false;
    }

    public void printProduction(String left, String right) {
        String format = "%10s%s = %s";
        System.out.println(String.format(format, "", left, right));
    }

    public void FS() {
        if (checkCategory(EnumTokens.PR_INTEIRO, EnumTokens.PR_FLUTUANTE, EnumTokens.PR_BOOLEANO, EnumTokens.PR_CARACTER, EnumTokens.PR_CONJUNTODEPALAVRAS)) {
            printProduction("S", "DeclId S");
            fDeclId();
            FS();
        } else if (checkCategory(EnumTokens.PR_FUNCAO)) {
            printProduction("S", "FunDecl S");
            fFunDecl();
            FS();
        }  else {
            printProduction("S", epsilon);
        }
    }

    public void fDeclId() {
        if (checkCategory(EnumTokens.PR_INTEIRO, EnumTokens.PR_FLUTUANTE, EnumTokens.PR_BOOLEANO, EnumTokens.PR_CARACTER, EnumTokens.PR_CONJUNTODEPALAVRAS)) {
            printProduction("DeclId", "Type LId ';'");
            fType();
            fLId();
        }
    }

    public void fType() {
        if (checkCategory(EnumTokens.PR_INTEIRO)) {
            printProduction("Type", "'Integer'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.PR_FLUTUANTE)) {
            printProduction("Type", "'Float'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.PR_BOOLEANO)) {
            printProduction("Type", "'Bool'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.PR_CARACTER)) {
            printProduction("Type", "'Character'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.PR_CONJUNTODEPALAVRAS)) {
            printProduction("Type", "'CharacterArray'");
            System.out.println(token);
            setNextToken();
        }
    }

    public void fLId() {
        if (checkCategory(EnumTokens.ID)) {
            printProduction("LId", "Id AttrOpt LIdr");
            fId();
            fAttrOpt();
            fLIdr();
        }
    }

    public void fLIdr() {
        if (checkCategory(EnumTokens.DELIM)) {
            printProduction("LIdr", "',' Id AttrOpt LIdr");
            System.out.println(token);
            setNextToken();
            fId();
            fAttrOpt();
            fLIdr();
        } else {
            printProduction("LIdr", epsilon);
        }
    }

    public void fId() {
        if (checkCategory(EnumTokens.ID)) {
            printProduction("Id", "'id' ArrayOpt");
            System.out.println(token);
            setNextToken();
            fArrayOpt();
        }
    }

    public void fAttrOpt() {
        if (checkCategory(EnumTokens.OP_ATR)) {
            printProduction("AttrOpt", "'=' Ec");
            System.out.println(token);
            setNextToken();
            fEc();
        } else {
            printProduction("AttrOpt", epsilon);
        }
    }

    public void fFunDecl() {
        if (checkCategory(EnumTokens.PR_FUNCAO)) {
            printProduction("FunDecl", "'fun' Type FunName '(' LParamDecl ')' Body");
            System.out.println(token);
            setNextToken();
            fType();
            fFunName();
            if (checkCategory(EnumTokens.AB_PAR)) {
                fLParamDecl();
                System.out.println(token);
                setNextToken();

                if (checkCategory(EnumTokens.FC_PAR)) {
                    System.out.println(token);
                    setNextToken();
                    fBody();
                }
            }
        }
    }

    public void fFunName() {
        if (checkCategory(EnumTokens.ID)) {
            printProduction("FunName", "'id'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.PR_PRINCIPAL)) {
            printProduction("FunName", "'main'");
            System.out.println(token);
            setNextToken();
        }
    }

    public void fLParamCall() {
        if (checkCategory(EnumTokens.ID, EnumTokens.AB_PAR, EnumTokens.OP_SUB, EnumTokens.CTE_BOOL, EnumTokens.CTE_CHR, EnumTokens.CTE_FLT, EnumTokens.CTE_INT, EnumTokens.CTE_CDP)) {
            printProduction("LParamCall", "Ec LParamCallr");
            fEc();
            fLParamCallr();
        } else {
            printProduction("LParamCall", epsilon);
        }
    }

    public void fLParamCallr() {
        if (checkCategory(EnumTokens.DELIM)) {
            printProduction("LParamCallr", "',' Ec LParamCallr");
            System.out.println(token);
            setNextToken();
            fEc();
            fLParamCallr();
        } else {
            printProduction("LParamCallr", epsilon);
        }
    }

    public void fLParamDecl() {
        if (checkCategory(EnumTokens.PR_BOOLEANO, EnumTokens.PR_CARACTER, EnumTokens.PR_FLUTUANTE, EnumTokens.PR_INTEIRO, EnumTokens.PR_CONJUNTODEPALAVRAS)) {
            printProduction("LParamDecl", "Type 'id' ArrayOpt LParamDeclr");
            fType();
            if (checkCategory(EnumTokens.ID)) {
                System.out.println(token);
                setNextToken();
                fArrayOpt();
                fLParamDeclr();
            }
        }else {
            printProduction("LParamDecl", epsilon);
        }
    }

    public void fLParamDeclr() {
        if (checkCategory(EnumTokens.DELIM)) {
            printProduction("LParamDeclr", "',' Type 'id' ArrayOpt LParamDeclr");
            System.out.println(token);
            setNextToken();
            fType();
            if (checkCategory(EnumTokens.ID)) {
                System.out.println(token);
                setNextToken();
                fArrayOpt();
                fLParamDeclr();
            }
        }
    }

    public void fArrayOpt() {
        if (checkCategory(EnumTokens.AB_COL)) {
            printProduction("ArrayOpt", "'[' Ea ']'");
            System.out.println(token);
            setNextToken();
            fEa();
            if (!checkCategory(EnumTokens.FC_COL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else {
            printProduction("ArrayOpt", epsilon);
        }
    }

    public void fBody() {
        if (checkCategory(EnumTokens.PR_INICIO)) {
            ++scopeCounter;
            printProduction("Body", "'{' BodyPart '}'");
            System.out.println(token);
            setNextToken();
            fBodyPart();
            if (!checkCategory(EnumTokens.PR_FIM)) {
            } else {
                System.out.println(token);
                setNextToken();
                --scopeCounter;
            }
        }
    }

    public void fBodyPart() {
        if (checkCategory(EnumTokens.PR_INTEIRO, EnumTokens.PR_FLUTUANTE, EnumTokens.PR_BOOLEANO, EnumTokens.PR_CARACTER, EnumTokens.PR_CONJUNTODEPALAVRAS)) {
            printProduction("BodyPart", "DeclId BodyPart");
            fDeclId();
            fBodyPart();
        } else if (checkCategory(EnumTokens.PR_IMPRIMIR,EnumTokens.PR_IMPRIMIRNL,EnumTokens.PR_ENTRADA, EnumTokens.PR_ENQUANTO, EnumTokens.PR_REPITA, EnumTokens.PR_SE)) {
            printProduction("BodyPart", "Command BodyPart");
            fCommand();
            fBodyPart();
        } else if (checkCategory(EnumTokens.ID)) {
            printProduction("BodyPart", "BodyPartr ';' BodyPart");
            fBodyPartr();
            if (!checkCategory(EnumTokens.TERMINAL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
            fBodyPart();
        } else if (checkCategory(EnumTokens.PR_DEVOLVE)) {
            printProduction("BodyPart", "'return' Return ';'");
            System.out.println(token);
            setNextToken();
            fReturn();
            if (!checkCategory(EnumTokens.TERMINAL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else {
            printProduction("BodyPart", epsilon);
        }
    }

    public void fBodyPartr() {
        if (checkCategory(EnumTokens.ID)) {
            printProduction("BodyPartr", "'id' ParamAttr");
            System.out.println(token);
            setNextToken();
            fParamAttr();
        }
    }

    public void fParamAttr() {
        if (checkCategory(EnumTokens.AB_PAR)) {
            printProduction("ParamAttrib", "'(' LParamCall ')'");
            System.out.println(token);
            setNextToken();
            fLParamCall();
            if (!checkCategory(EnumTokens.FC_PAR)) {
          } else {
                System.out.println(token);
                setNextToken();
            }
        } else if (checkCategory(EnumTokens.OP_ATR)) {
            printProduction("ParamAttrib", "'=' Ec LAttr");
            System.out.println(token);
            setNextToken();
            fEc();
            fLAttr();
        } else if (checkCategory(EnumTokens.AB_COL)) {
            printProduction("ParamAttrib", "'[' Ea ']' '=' Ec LAttr");
            System.out.println(token);
            setNextToken();
            fEa();
            if (checkCategory(EnumTokens.FC_COL)) {
                System.out.println(token);
                setNextToken();
                if (checkCategory(EnumTokens.OP_ATR)) {
                    System.out.println(token);
                    setNextToken();
                    fEc();
                    fLAttr();
                }
            }
        }
    }

    public void fLAttr() {
        if (checkCategory(EnumTokens.DELIM)) {
            printProduction("LAttr", "',' Id '=' Ec LAttr");
            System.out.println(token);
            setNextToken();
            fId();
            if (checkCategory(EnumTokens.OP_ATR)) {
                System.out.println(token);
                setNextToken();
                fEc();
                fLAttr();
            }
        } else {
            printProduction("LAttr", epsilon);
        }
    }

    public void fReturn() {
        if (checkCategory(EnumTokens.AB_PAR, EnumTokens.OP_SUB, EnumTokens.CTE_INT, EnumTokens.CTE_BOOL, EnumTokens.CTE_CHR, EnumTokens.CTE_FLT, EnumTokens.CTE_CDP, EnumTokens.ID)) {
            printProduction("Return", "Ec");
            fEc();
        } else {
            printProduction("Return", epsilon);
        }
    }

    public void fCommand() {
        if (checkCategory(EnumTokens.PR_IMPRIMIR)) {
            printProduction("Command", "'print' '(' 'constStr' PrintLParam ')' ';'");
            System.out.println(token);
            setNextToken();
            if (checkCategory(EnumTokens.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                //colocar o char antes
                if (checkCategory(EnumTokens.CTE_CDP)) {
                    System.out.println(token);
                    setNextToken();
                    fPrintLParam();
                    if (checkCategory(EnumTokens.FC_PAR)) {
                        System.out.println(token);
                        setNextToken();
                        if (!checkCategory(EnumTokens.TERMINAL)) {
                        } else {
                            System.out.println(token);
                            setNextToken();
                        }
                    }
                }
            }
        } else if (checkCategory(EnumTokens.PR_ENTRADA)) {
            printProduction("Command", "'scan' '(' ScanLParam ')' ';'");
            System.out.println(token);
            setNextToken();
            if (checkCategory(EnumTokens.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                fScanLParam();
                if (checkCategory(EnumTokens.FC_PAR)) {
                    System.out.println(token);
                    setNextToken();
                    if (!checkCategory(EnumTokens.TERMINAL)) {
                    } else {
                        System.out.println(token);
                        setNextToken();
                    }
                }
            }
        } else if (checkCategory(EnumTokens.PR_ENQUANTO)) {
            printProduction("Command", "'whileLoop' '(' Eb ')' Body");
            System.out.println(token);
            setNextToken();
            if (checkCategory(EnumTokens.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                fEb();
                if (checkCategory(EnumTokens.AB_PAR)) {
                    System.out.println(token);
                    setNextToken();
                    fBody();
                }
            }
        } else if (checkCategory(EnumTokens.PR_REPITA)) {
            printProduction("Command", "'forLoop' ForParams");
            System.out.println(token);
            setNextToken();
            fForParams();
        } else if (checkCategory(EnumTokens.PR_SE)) {
            printProduction("Command", "'condIf' '(' Eb ')' Body Ifr");
            System.out.println(token);
            setNextToken();
            if (checkCategory(EnumTokens.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                fEb();
                if (checkCategory(EnumTokens.AB_PAR)) {
                    System.out.println(token);
                    setNextToken();
                    fBody();
                    fIfr();
                }
            }
        }
    }

    public void fPrintLParam() {
        if (checkCategory(EnumTokens.DELIM)) {
            printProduction("PrintLParam", "',' Ec PrintLParam");
            System.out.println(token);
            setNextToken();
            fEc();
            fPrintLParam();
        } else {
            printProduction("PrintLParam", epsilon);
        }
    }

    public void fScanLParam() {
        if (checkCategory(EnumTokens.ID)) {
            printProduction("ScanLParam", "'id' ArrayOpt ScanLParamr");
            System.out.println(token);
            setNextToken();
            fArrayOpt();
            fScanLParamr();
        }
    }

    public void fScanLParamr() {
        if (checkCategory(EnumTokens.DELIM)) {
            printProduction("ScanLParamr", "',' 'id' ArrayOpt ScanLParamr");
            System.out.println(token);
            setNextToken();
            if (checkCategory(EnumTokens.ID)) {
                System.out.println(token);
                setNextToken();
                fArrayOpt();
                fScanLParamr();
            }
        } else {
            printProduction("ScanLParamr", epsilon);
        }
    }

    public void fForParams() {
        if (checkCategory(EnumTokens.AB_PAR)) {
            printProduction("ForParams", "'(' 'typeInt' 'id' '=' '(' Ea ',' Ea ForStep ')' ')' Body");
            System.out.println(token);
            setNextToken();
            if (checkCategory(EnumTokens.PR_INTEIRO)) {
                System.out.println(token);
                setNextToken();
                if (checkCategory(EnumTokens.ID)) {
                    System.out.println(token);
                    setNextToken();
                    if (checkCategory(EnumTokens.OP_ATR)) {
                        System.out.println(token);
                        setNextToken();
                        if (checkCategory(EnumTokens.AB_PAR)) {
                            System.out.println(token);
                            setNextToken();
                            fEa();
                            if (checkCategory(EnumTokens.DELIM)) {
                                System.out.println(token);
                                setNextToken();
                                fEa();
                                fForStep();
                                if (checkCategory(EnumTokens.FC_PAR)) {
                                    System.out.println(token);
                                    setNextToken();
                                    if (checkCategory(EnumTokens.FC_PAR)) {
                                        System.out.println(token);
                                        setNextToken();
                                        fBody();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void fForStep() {
        if (checkCategory(EnumTokens.DELIM)) {
            printProduction("ForStep", "',' Ea");
            System.out.println(token);
            setNextToken();
            fEa();
        } else {
            printProduction("ForStep", epsilon);
        }
    }

    public void fIfr() {
        if (checkCategory(EnumTokens.PR_POREM)) {
            printProduction("Ifr", "'condElse' Body");
            System.out.println(token);
            setNextToken();
            fBody();
        } else {
            printProduction("Ifr", epsilon);
        }
    }

    public void fEc() {
        printProduction("Ec", "Fc Ecr");
        fEb();
        fEcr();
    }

    public void fEcr() {
        if (checkCategory(EnumTokens.OP_CONC)) {
            printProduction("Ecr", "'opConcat' Fc Ecr");
            System.out.println(token);
            setNextToken();
            fEb();
            fEcr();
        } else {
            printProduction("Ecr", epsilon);
        }
    }

    public void fEb() {
        printProduction("Eb", "Tb Ebr");
        fTb();
        fEbr();
    }

    public void fEbr() {
        if (checkCategory(EnumTokens.PR_OU)) {
            printProduction("Ebr", "'opOr' Tb Ebr");
            System.out.println(token);
            setNextToken();
            fTb();
            fEbr();
        } else {
            printProduction("Ebr", epsilon);
        }
    }

    public void fTb() {
        printProduction("Tb", "Fb Tbr");
        fFb();
        fTbr();
    }

    public void fTbr() {
        if (checkCategory(EnumTokens.PR_E)) {
            printProduction("Tbr", "'opAnd' Fb Tbr");
            System.out.println(token);
            setNextToken();
            fFb();
            fTbr();
        } else {
            printProduction("Tbr", epsilon);
        }
    }

    public void fFb() {
        if (checkCategory(EnumTokens.OP_NEG)) {
            printProduction("Fb", "'opNot' Fb");
            System.out.println(token);
            setNextToken();
            fFb();
        } else if (checkCategory(EnumTokens.AB_PAR, EnumTokens.OP_SUB, EnumTokens.CTE_INT, EnumTokens.CTE_BOOL, EnumTokens.CTE_CHR, EnumTokens.CTE_FLT, EnumTokens.CTE_CDP, EnumTokens.ID)) {
            printProduction("Fb", "Ra Fbr");
            fRa();
            fFbr();
        }
    }

    public void fFbr() {
        if (checkCategory(EnumTokens.OP_MAIOR)) {
            printProduction("Fbr", "'opGreater' Ra Fbr");
            System.out.println(token);
            setNextToken();
            fRa();
            fFbr();
        } else if (checkCategory(EnumTokens.OP_MENOR)) {
            printProduction("Fbr", "'opLesser' Ra Fbr");
            System.out.println(token);
            setNextToken();
            fRa();
            fFbr();
        } else if (checkCategory(EnumTokens.OP_MAIORIG)) {
            printProduction("Fbr", "'opGreq' Ra Fbr");
            System.out.println(token);
            setNextToken();
            fRa();
            fFbr();
        } else if (checkCategory(EnumTokens.OP_MENORIG)) {
            printProduction("Fbr", "'opLeq' Ra Fbr");
            System.out.println(token);
            setNextToken();
            fRa();
            fFbr();
        } else {
            printProduction("Fbr", epsilon);
        }
    }

    public void fRa() {
        printProduction("Ra", "Ea Rar");
        fEa();
        fRar();
    }

    public void fRar() {
        if (checkCategory(EnumTokens.OP_REL)) {
            printProduction("Rar", "'opEquals' Ea Rar");
            System.out.println(token);
            setNextToken();
            fEa();
            fRar();
        } else if (checkCategory(EnumTokens.OP_NEG)) {
            printProduction("Rar", "'opNotEqual' Ea Rar");
            System.out.println(token);
            setNextToken();
            fEa();
            fRar();
        } else {
            printProduction("Rar", epsilon);
        }
    }

    public void fEa() {
        printProduction("Ea", "Ta Ear");
        fTa();
        fEar();
    }

    public void fEar() {
        if (checkCategory(EnumTokens.OP_ADI)) {
            printProduction("Ear", "'opAdd' Ta Ear");
            System.out.println(token);
            setNextToken();
            fTa();
            fEar();
        } else if (checkCategory(EnumTokens.OP_SUB)) {
            printProduction("Ear", "'opSub' Ta Ear");
            System.out.println(token);
            setNextToken();
            fTa();
            fEar();
        } else {
            printProduction("Ear", epsilon);
        }
    }

    public void fTa() {
        printProduction("Ta", "Pa Tar");
        fPa();
        fTar();
    }

    public void fTar() {
        if (checkCategory(EnumTokens.OP_MULT)) {
            printProduction("Tar", "'opMult' Pa Tar");
            System.out.println(token);
            setNextToken();
            fPa();
            fTar();
        } else if (checkCategory(EnumTokens.OP_DIV)) {
            printProduction("Tar", "'opDiv' Pa Tar");
            System.out.println(token);
            setNextToken();
            fPa();
            fTar();
        } else {
            printProduction("Tar", epsilon);
        }
    }

    public void fPa() {
        printProduction("Pa", "Fa Par");
        fFa();
        fPar();
    }

    public void fPar() {
        if (checkCategory(EnumTokens.OP_RES)) {
            printProduction("Par", "'opPow' Fa Par");
            System.out.println(token);
            setNextToken();
            fFa();
            fPar();
        } else {
            printProduction("Par", epsilon);
        }
    }

    public void fFa() {
        if (checkCategory(EnumTokens.AB_PAR)) {
            printProduction("Fa", "'(' Ec ')'");
            System.out.println(token);
            setNextToken();
            fEc();
            if (!checkCategory(EnumTokens.AB_PAR)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else if (checkCategory(EnumTokens.OP_SUB)) {
            printProduction("Fa", "'opSub' Fa");
            System.out.println(token);
            setNextToken();
            fFa();
        } else if (checkCategory(EnumTokens.ID)) {
            fIdOrFunCall();
        } else if (checkCategory(EnumTokens.CTE_BOOL)) {
            printProduction("Fa", "'constBool'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.CTE_CHR)) {
            printProduction("Fa", "'constChar'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.CTE_FLT)) {
            printProduction("Fa", "'constFloat'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.CTE_INT)) {
            printProduction("Fa", "'constInt'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.CTE_CDP)) {
            printProduction("Fa", "'constStr'");
            System.out.println(token);
            setNextToken();
        }
    }

    public void fIdOrFunCall() {
        if (checkCategory(EnumTokens.ID)) {
            printProduction("IdOrFunCall", "'id' IdOrFunCallr");
            System.out.println(token);
            setNextToken();
            fIdOrFunCallr();
        }
    }

    public void fIdOrFunCallr() {
        if (checkCategory(EnumTokens.AB_PAR)) {
            printProduction("IdOrFunCallr", "'(' LParamCall ')'");
            System.out.println(token);
            setNextToken();
            fLParamCall();
            if (!checkCategory(EnumTokens.FC_PAR)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else if (checkCategory(EnumTokens.AB_PAR)) {
            printProduction("IdOrFunCallr", "'[' Ea ']'");
            System.out.println(token);
            setNextToken();
            fEa();
            if (!checkCategory(EnumTokens.FC_PAR)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else {
            printProduction("IdOrFunCallr", epsilon);
        }
    }
}
