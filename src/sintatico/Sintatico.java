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
            dcFun();
            FS();
        }  else {
            printProduction("S", epsilon);
        }
    }
 
 
    public void fDeclId() {
        if (checkCategory(EnumTokens.PR_INTEIRO, EnumTokens.PR_FLUTUANTE, EnumTokens.PR_BOOLEANO, EnumTokens.PR_CARACTER, EnumTokens.PR_CONJUNTODEPALAVRAS)) {
            printProduction("DeclId", "Tipo LId ';'");
            funTipo();
            fLId();
 
            if (!checkCategory(EnumTokens.TERMINAL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        }
    }
    public void funTipo() {
        if (checkCategory(EnumTokens.PR_INTEIRO)) {
            printProduction("Tipo", "'Inteiro'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.PR_VAZIO)) {
            printProduction("Tipo", "'Vazio'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.PR_FLUTUANTE)) {
            printProduction("Tipo", "'Flutuante'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.PR_BOOLEANO)) {
            printProduction("Tipo", "'Booleano'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.PR_CARACTER)) {
            printProduction("Tipo", "'Caracter'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.PR_CONJUNTODEPALAVRAS)) {
            printProduction("Tipo", "'ConjuntoDePalavras'");
            System.out.println(token);
            setNextToken();
        }
    }
 
    public void fLId() {
        if (checkCategory(EnumTokens.ID)) {
            printProduction("LId", "Id Atr Id_LL");
            fId();
            atr();
            id_LL();
        }
    }
 
    public void id_LL() {
        if (checkCategory(EnumTokens.DELIM)) {
            printProduction("Id_LL", "',' Id Atr Id_LL");
            System.out.println(token);
            setNextToken();
            fId();
            atr();
            id_LL();
        } else {
            printProduction("Id_LL", epsilon);
        }
    }
 
    public void fId() {
        if (checkCategory(EnumTokens.ID)) {
            printProduction("Id", "'id' ArrayOpt");
            System.out.println(token);
            setNextToken();
            vetTipo();
        }
    }
 
    public void atr() {
        if (checkCategory(EnumTokens.OP_ATR)) {
            printProduction("Atr", "'=' Ec");
            System.out.println(token);
            setNextToken();
            fEc();
        } else {
            printProduction("Atr", epsilon);
        }
    }
 
    public void dcFun() {
        if (checkCategory(EnumTokens.PR_FUNCAO)) {
            printProduction("DcFun", "'Funcao' Tipo NomeFun '(' ConstDc ')' InternoDc");
            System.out.println(token);
            setNextToken();
            funTipo();
            nomeFun();
            if (checkCategory(EnumTokens.AB_PAR)) {
                System.out.println(token);
                setNextToken();
                constDc();
 
 
                if (checkCategory(EnumTokens.FC_PAR)) {
                    System.out.println(token);
                    setNextToken();
                    internoDc();
                }
            }
        }
    }
 
    public void nomeFun() {
        if (checkCategory(EnumTokens.ID)) {
            printProduction("NomeFun", "'id'");
            System.out.println(token);
            setNextToken();
        } else if (checkCategory(EnumTokens.PR_PRINCIPAL)) {
            printProduction("NomeFun", "'Principal'");
            System.out.println(token);
            setNextToken();
        }
    }
 
    public void paramFun() {
        if (checkCategory(EnumTokens.ID, EnumTokens.AB_PAR, EnumTokens.OP_SUB, EnumTokens.CTE_BOOL, EnumTokens.CTE_CHR, EnumTokens.CTE_FLT, EnumTokens.CTE_INT, EnumTokens.CTE_CDP)) {
            printProduction("ParamFun", "Ec ParamFun_LL");
            fEc();
            paramFunLL();
        } else {
            printProduction("ParamFun", epsilon);
        }
    }
 
    public void paramFunLL() {
        if (checkCategory(EnumTokens.DELIM)) {
            printProduction("ParamFun_LL", "',' Ec ParamFun_LL");
            System.out.println(token);
            setNextToken();
            fEc();
            paramFunLL();
        } else {
            printProduction("ParamFun_LL", epsilon);
        }
    }
 
    public void constDc() {
        if (checkCategory(EnumTokens.PR_BOOLEANO, EnumTokens.PR_CARACTER, EnumTokens.PR_FLUTUANTE, EnumTokens.PR_INTEIRO, EnumTokens.PR_CONJUNTODEPALAVRAS)) {
            printProduction("ConstDc", "Tipo 'id' VetTipo ConstDc_LL");
            funTipo();
            if (checkCategory(EnumTokens.ID)) {
                System.out.println(token);
                setNextToken();
                vetTipo();
                constDcLL();
            }
        }else {
            printProduction("ConstDc", epsilon);
        }
    }
 
    public void constDcLL() {
        if (checkCategory(EnumTokens.DELIM)) {
            printProduction("ConstDc_LL", "',' Tipo 'id' VetTipo ConstDc_LL");
            System.out.println(token);
            setNextToken();
            funTipo();
            if (checkCategory(EnumTokens.ID)) {
                System.out.println(token);
                setNextToken();
                vetTipo();
                constDcLL();
            }
        }
    }
 
    public void vetTipo() {
        if (checkCategory(EnumTokens.AB_COL)) {
            printProduction("VetTipo", "'[' Ea ']'");
            System.out.println(token);
            setNextToken();
            fEa();
            if (!checkCategory(EnumTokens.FC_COL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else {
            printProduction("VetTipo", epsilon);
        }
    }
 
    public void internoDc() {
        if (checkCategory(EnumTokens.PR_INICIO)) {
            ++scopeCounter;
            printProduction("InternoDc", "'{' Instrucoes '}'");
            System.out.println(token);
            setNextToken();
            instrucoes();
            if (!checkCategory(EnumTokens.PR_FIM)) {
            } else {
                System.out.println(token);
                setNextToken();
                --scopeCounter;
            }
        }
    }
 
    public void instrucoes() {
        if (checkCategory(EnumTokens.PR_INTEIRO, EnumTokens.PR_FLUTUANTE, EnumTokens.PR_BOOLEANO, EnumTokens.PR_CARACTER, EnumTokens.PR_CONJUNTODEPALAVRAS)) {
            printProduction("Instrucoes", "DeclId Instrucoes");
            fDeclId();
            instrucoes();
        } else if (checkCategory(EnumTokens.PR_IMPRIMIR,EnumTokens.PR_IMPRIMIRNL,EnumTokens.PR_ENTRADA, EnumTokens.PR_ENQUANTO, EnumTokens.PR_REPITA, EnumTokens.PR_SE)) {
            System.out.println(token.lexema);
            printProduction("Instrucoes", "Comando Instrucoes");
            comando();
            instrucoes();
        } else if (checkCategory(EnumTokens.ID)) {
            printProduction("Instrucoes", "InstrucoesLL ';' Instrucoes");
            instrucoesLL();
            if (!checkCategory(EnumTokens.TERMINAL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
            instrucoes();
        } else if (checkCategory(EnumTokens.PR_DEVOLVE)) {
            printProduction("Instrucoes", "'Devolve' Devolve ';'");
            System.out.println(token);
            setNextToken();
            devolve();
            if (!checkCategory(EnumTokens.TERMINAL)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else {
            printProduction("Instrucoes", epsilon);
        }
    }
 
    public void instrucoesLL() {
        if (checkCategory(EnumTokens.ID)) {
            printProduction("instrucoesLL", "'id' ParamAtr");
            System.out.println(token);
            setNextToken();
            fParamAtr();
        }
    }
 
    public void fParamAtr() {
        if (checkCategory(EnumTokens.AB_PAR)) {
            printProduction("ParamAtr", "'(' ParamFun ')'");
            System.out.println(token);
            setNextToken();
            paramFun();
            if (!checkCategory(EnumTokens.FC_PAR)) {
            } else {
                System.out.println(token);
                setNextToken();
            }
        } else if (checkCategory(EnumTokens.OP_ATR)) {
            printProduction("ParamAtr", "'=' Ec lAtr");
            System.out.println(token);
            setNextToken();
            fEc();
            lAtr();
        } else if (checkCategory(EnumTokens.AB_COL)) {
            printProduction("ParamAtr", "'[' Ea ']' '=' Ec lAtr");
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
                    lAtr();
                }
            }
        }
    }
 
    public void lAtr() {
        if (checkCategory(EnumTokens.DELIM)) {
            printProduction("lAtr", "',' Id '=' Ec lAtr");
            System.out.println(token);
            setNextToken();
            fId();
            if (checkCategory(EnumTokens.OP_ATR)) {
                System.out.println(token);
                setNextToken();
                fEc();
                lAtr();
            }
        } else {
            printProduction("lAtr", epsilon);
        }
    }
 
    public void devolve() {
        if (checkCategory(EnumTokens.AB_PAR, EnumTokens.OP_SUB, EnumTokens.CTE_INT, EnumTokens.CTE_BOOL, EnumTokens.CTE_CHR, EnumTokens.CTE_FLT, EnumTokens.CTE_CDP, EnumTokens.ID)) {
            printProduction("Devolve", "Ec");
            fEc();
        } else {
            printProduction("Devolve", epsilon);
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
                    internoDc();
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
                    internoDc();
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
            vetTipo();
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
                vetTipo();
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
                                internoDc();
                            }
                        }
                    }
                }
            }
        }
    }
 
    public void repitaPasso() {
        if (checkCategory(EnumTokens.DELIM)) {
            printProduction("RepitaPasso", "',' Ea");
            System.out.println(token);
            setNextToken();
            fEa();
        } else {
            printProduction("RepitaPasso", epsilon);
        }
    }
 
    public void fSeLL() {
        if (checkCategory(EnumTokens.PR_POREM)) {
            printProduction("fSeLL", "'PR_POREM' Instrucao");
            System.out.println(token);
            setNextToken();
            internoDc();
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
            paramFun();
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