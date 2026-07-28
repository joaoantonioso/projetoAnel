package projetoAnel;
import java.util.*;

public class Anel {
	private List<Integer> conjunto;
	
	public boolean ehAssociativo() {
		for (int a: conjunto) {
			for (int b: conjunto) {
				for (int c: conjunto) {
					int ladoDireito = operacao(operacao(a, b), c);
					int ladoEsquerdo = operacao(a, operacao(b, c));
					
					if (ladoDireito != ladoEsquerdo) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public boolean verificarFechamento() {
		for (int a: conjunto) {
			for (int b: conjunto) {
				int soma = operacao(a, b);
				if (!conjunto.contains(soma)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean ehComutativo() {
		for (int a: conjunto) {
			for (int b: conjunto) {
				int op1 = operacao(a, b);
				int op2 = operacao(b, a);
				
				if (op1 != op2) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean temInverso() {
		for (int a: conjunto) {
			boolean temInverso = false;
			
			for (int b: conjunto) {
				if (operacao(a, b) == 0) {
					temInverso = true;
					break;
				}
			}
			if (!temInverso) {
				return false;
			}
		}
		return true;
	}
	
	private int operacao(int a, int b) {
		return a + b;
	}
	
	public boolean verificarElementosNeutros() {
		if (conjunto.contains(0) && conjunto.contains(1)) {
			return true;
		}
		return false;
	}
}
