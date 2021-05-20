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
            return ;
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
 
            if (!checkCategory(EnumTokens.TERMINAL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        }
    }
    public void fType() {
        if (checkCategory(EnumTokens.PR_INTEIRO)) {
            printProduction("Type", "'Integer'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.PR_VAZIO)) {
            printProduction("Type", "'Void'");
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
                System.out.println(token);
                setNextToken();
                fLParamDecl();
 
 
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
            System.out.println(token.lexema);
            printProduction("BodyPart", "Command BodyPart");
            comando();
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
            PaLLamAttr();
        }
    }
 
    public void PaLLamAttr() {
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
 
    public void comando() {
        if (checkCategory(EnumTokens.PR_IMPRIMIR ,EnumTokens.PR_IMPRIMIRNL)) {
            printProduction("Comando", "'Imprimir' '(' 'CTE_CDP' ImprimirParam ')' ';'");
            System.out.println(token);
            setNextToken();
            if (checkCategory(EnumTokens.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                //colocar o char antes
                if (checkCategory(EnumTokens.CTE_CDP, EnumTokens.ID)) {
                    System.out.println(token);
                    setNextToken();
                    imprimirParam();
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
            printProduction("Comando", "'Entrada' '(' EntradaParam ')' ';'");
            System.out.println(token);
            setNextToken();
            if (checkCategory(EnumTokens.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                entradaParam();
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
            printProduction("Comando", "'Enquanto' '(' Eb ')' InternoDc");
            System.out.println(token);
            setNextToken();
            if (checkCategory(EnumTokens.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                fEb();
                if (checkCategory(EnumTokens.FC_PAR)) {
                    System.out.println(token);
                    setNextToken();
                    fBody();
                }
            }
        } else if (checkCategory(EnumTokens.PR_REPITA)) {
            printProduction("Comando", "'Repita' repitaParam");
            System.out.println(token);
            setNextToken();
            repita();
        } else if (checkCategory(EnumTokens.PR_SE)) {
            printProduction("Comando", "'Se' '(' Eb ')' InternoDc fSeLL");
            System.out.println(token);
            setNextToken();
            if (checkCategory(EnumTokens.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                fEb();
                if (checkCategory(EnumTokens.FC_PAR)) {
                    System.out.println(token);
                    setNextToken();
                    fBody();
                    fSeLL();
                }
            }
        }
    }
 
    public void imprimirParam() {
        if (checkCategory(EnumTokens.DELIM)) {
            printProduction("ImprimirParam", "',' Ec ImprimirParam");
            System.out.println(token);
            setNextToken();
            fEc();
            imprimirParam();
        } else {
            printProduction("ImprimirParam", epsilon);
        }
    }
 
    public void entradaParam() {
        if (checkCategory(EnumTokens.ID)) {
            printProduction("entradaParam", "'id' VetTipo entradaParamLL");
            System.out.println(token);
            setNextToken();
            fArrayOpt();
            entradaParamLL();
        }
    }
 
    public void entradaParamLL() {
        if (checkCategory(EnumTokens.DELIM)) {
            printProduction("entradaParamLL", "',' 'id' VetTipo entradaParamLL");
            System.out.println(token);
            setNextToken();
            if (checkCategory(EnumTokens.ID)) {
                System.out.println(token);
                setNextToken();
                fArrayOpt();
                entradaParamLL();
            }
        } else {
            printProduction("entradaParamLL", epsilon);
        }
    }
 
    public void repita() {
        if (checkCategory(EnumTokens.AB_PAR)) {
            printProduction("Repita", "'(' 'Inteiro' 'id' ':'  Ea ',' Ea RepitaPasso ')' InternoDc");
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
                        fEa();
                        if (checkCategory(EnumTokens.DELIM)) {
                            System.out.println(token);
                            setNextToken();
                            fEa();
                            repitaPasso();
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
 
    public void repitaPasso() {
        if (checkCategory(EnumTokens.DELIM)) {
            printProduction("repitaPasso", "',' Ea");
            System.out.println(token);
            setNextToken();
            fEa();
        } else {
            printProduction("ForStep", epsilon);
        }
    }
 
    public void fSeLL() {
        if (checkCategory(EnumTokens.PR_POREM)) {
            printProduction("fSeLL", "'PR_POREM' Body");
            System.out.println(token);
            setNextToken();
            fBody();
        } else {
            printProduction("fSeLL", epsilon);
        }
    }
 
    public void fEc() {
        printProduction("Ec", "Fc Ecr");
        fEb();
        EcLL();
    }
 
    public void EcLL() {
        if (checkCategory(EnumTokens.OP_CONC)) {
            printProduction("EcLL", "'OP_CONC' Fc EcLL");
            System.out.println(token);
            setNextToken();
            fEb();
            EcLL();
        } else {
            printProduction("EcLL", epsilon);
        }
    }
 
    public void fEb() {
        printProduction("Eb", "Tb Ebr");
        fTb();
        EbLL();
    }
 
    public void EbLL() {
        if (checkCategory(EnumTokens.PR_OU)) {
            printProduction("EbLL", "'OP_OU' Tb EbLL");
            System.out.println(token);
            setNextToken();
            fTb();
            EbLL();
        } else {
            printProduction("EbLL", epsilon);
        }
    }
 
    public void fTb() {
        printProduction("Tb", "Fb TbLL");
        fFb();
        TbLL();
    }
 
    public void TbLL() {
        if (checkCategory(EnumTokens.PR_E)) {
            printProduction("TbLL", "'OP_E' Fb TbLL");
            System.out.println(token);
            setNextToken();
            fFb();
            TbLL();
        } else {
            printProduction("TbLL", epsilon);
        }
    }
 
    public void fFb() {
        if (checkCategory(EnumTokens.OP_NEG)) {
            printProduction("Fb", "'OP_NEG' Fb");
            System.out.println(token);
            setNextToken();
            fFb();
        } else if (checkCategory(EnumTokens.AB_PAR, EnumTokens.OP_SUB, EnumTokens.CTE_INT, EnumTokens.CTE_BOOL, EnumTokens.CTE_CHR, EnumTokens.CTE_FLT, EnumTokens.CTE_CDP, EnumTokens.ID)) {
            printProduction("Fb", "Ra FbLL");
            fRa();
            FbLL();
        }
    }
 
    public void FbLL() {
        if (checkCategory(EnumTokens.OP_MAIOR)) {
            printProduction("FbLL", "'OP_MAIOR' Ra FbLL");
            System.out.println(token);
            setNextToken();
            fRa();
            FbLL();
        } else if (checkCategory(EnumTokens.OP_MENOR)) {
            printProduction("FbLL", "'OP_MENOR' Ra FbLL");
            System.out.println(token);
            setNextToken();
            fRa();
            FbLL();
        } else if (checkCategory(EnumTokens.OP_MAIORIG)) {
            printProduction("FbLL", "'OP_MAIORIG' Ra FbLL");
            System.out.println(token);
            setNextToken();
            fRa();
            FbLL();
        } else if (checkCategory(EnumTokens.OP_MENORIG)) {
            printProduction("FbLL", "'OP_MENORIG' Ra FbLL");
            System.out.println(token);
            setNextToken();
            fRa();
            FbLL();
        } else {
            printProduction("FbLL", epsilon);
        }
    }
 
    public void fRa() {
        printProduction("Ra", "Ea RaLL");
        fEa();
        RaLL();
    }
 
    public void RaLL() {
        if (checkCategory(EnumTokens.OP_REL)) {
            printProduction("RaLL", "'OP_REL' Ea RaLL");
            System.out.println(token);
            setNextToken();
            fEa();
            RaLL();
        } else if (checkCategory(EnumTokens.OP_NEG)) {
            printProduction("RaLL", "'OP_NEG' Ea RaLL");
            System.out.println(token);
            setNextToken();
            fEa();
            RaLL();
        } else {
            printProduction("RaLL", epsilon);
        }
    }
 
    public void fEa() {
        printProduction("Ea", "Ta EaLL");
        fTa();
        EaLL();
    }
 
    public void EaLL() {
        if (checkCategory(EnumTokens.OP_ADI)) {
            printProduction("EaLL", "'OP_ADI' Ta Ear");
            System.out.println(token);
            setNextToken();
            fTa();
            EaLL();
        } else if (checkCategory(EnumTokens.OP_SUB)) {
            printProduction("EaLL", "'OP_SUB' Ta EaLL");
            System.out.println(token);
            setNextToken();
            fTa();
            EaLL();
        } else {
            printProduction("EaLL", epsilon);
        }
    }
 
    public void fTa() {
        printProduction("Ta", "Pa TaLL");
        fPa();
        TaLL();
    }
 
    public void TaLL() {
        if (checkCategory(EnumTokens.OP_MULT)) {
            printProduction("TaLL", "'OP_MULT' Pa TaLL");
            System.out.println(token);
            setNextToken();
            fPa();
            TaLL();
        } else if (checkCategory(EnumTokens.OP_DIV)) {
            printProduction("TaLL", "'OP_DIV' Pa TaLL");
            System.out.println(token);
            setNextToken();
            fPa();
            TaLL();
        } else {
            printProduction("TaLL", epsilon);
        }
    }
 
    public void fPa() {
        printProduction("Pa", "Fa PaLL");
        fFa();
        PaLL();
    }
 
    public void PaLL() {
        if (checkCategory(EnumTokens.OP_RES)) {
            printProduction("PaLL", "'OP_RES' Fa Par");
            System.out.println(token);
            setNextToken();
            fFa();
            PaLL();
        } else {
            printProduction("PaLL", epsilon);
        }
    }
 
    public void fFa() {
        if (checkCategory(EnumTokens.AB_PAR)) {
            printProduction("Fa", "'(' Ec ')'");
            System.out.println(token);
            setNextToken();
            fEc();
            if (!checkCategory(EnumTokens.FC_PAR)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else if (checkCategory(EnumTokens.OP_SUB)) {
            printProduction("Fa", "'OP_SUB' Fa");
            System.out.println(token);
            setNextToken();
            fFa();
        } else if (checkCategory(EnumTokens.ID)) {
            IdFunCham();
        } else if (checkCategory(EnumTokens.CTE_BOOL)) {
            printProduction("Fa", "'CTE_BOOL'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.CTE_CHR)) {
            printProduction("Fa", "'CTE_CHR'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.CTE_FLT)) {
            printProduction("Fa", "'CTE_FLT'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.CTE_INT)) {
            printProduction("Fa", "'CTE_INT'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.CTE_CDP)) {
            printProduction("Fa", "'CTE_CDP'");
            System.out.println(token);
            setNextToken();
        }
    }
 
    public void IdFunCham() {
        if (checkCategory(EnumTokens.ID)) {
            printProduction("IdFunCham", "'id' IdFunCham_LL");
            System.out.println(token);
            setNextToken();
            IdFunCham_LL();
        }
    }
 
    public void IdFunCham_LL() {
        if (checkCategory(EnumTokens.AB_PAR)) {
            printProduction("IdFunCham_LL", "'(' ParamFun ')'");
            System.out.println(token);
            setNextToken();
            fLParamCall();
            if (!checkCategory(EnumTokens.FC_PAR)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else if (checkCategory(EnumTokens.AB_COL)) {
            printProduction("IdFunCham_LL", "'[' Ea ']'");
            System.out.println(token);
            setNextToken();
            fEa();
            if (!checkCategory(EnumTokens.FC_COL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else {
            printProduction("IdFunCham_LL", epsilon);
        }
    }
}