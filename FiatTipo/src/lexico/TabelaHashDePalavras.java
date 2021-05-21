package lexico;

import java.util.Hashtable;

public class TabelaHashDePalavras {
	Hashtable<String, EnumTokens> palavraReservada = new Hashtable<>();
	
	public TabelaHashDePalavras() {
		palavraReservada.put("Inicio", EnumTokens.PR_INICIO);
		palavraReservada.put("Fim", EnumTokens.PR_FIM);
		palavraReservada.put("Principal", EnumTokens.PR_PRINCIPAL);
		palavraReservada.put("E",EnumTokens.PR_E);
		palavraReservada.put("Funcao",EnumTokens.PR_FUNCAO);
		palavraReservada.put("Ou",EnumTokens.PR_OU);
		palavraReservada.put("Devolve",EnumTokens.PR_DEVOLVE);
		palavraReservada.put("Vazio",EnumTokens.PR_VAZIO);
		palavraReservada.put("Se",EnumTokens.PR_SE);
		palavraReservada.put("Porem",EnumTokens.PR_POREM);
		palavraReservada.put("Enquanto",EnumTokens.PR_ENQUANTO);
		palavraReservada.put("Repita",EnumTokens.PR_REPITA);
		palavraReservada.put("Flutuante",EnumTokens.PR_FLUTUANTE);
		palavraReservada.put("Inteiro",EnumTokens.PR_INTEIRO);
		palavraReservada.put("Caracter",EnumTokens.PR_CARACTER);
		palavraReservada.put("ConjuntoDePalavras",EnumTokens.PR_CONJUNTODEPALAVRAS);
		palavraReservada.put("Booleano",EnumTokens.PR_BOOLEANO);
		palavraReservada.put("Entrada",EnumTokens.PR_ENTRADA);
		palavraReservada.put("Imprimir",EnumTokens.PR_IMPRIMIR);
		palavraReservada.put("Imprimirnl",EnumTokens.PR_IMPRIMIRNL);
		palavraReservada.put("Nada",EnumTokens.PR_NADA);
		palavraReservada.put("Verdade",EnumTokens.PR_VERDADE);
		palavraReservada.put("Mentira",EnumTokens.PR_MENTIRA);
	}
}
