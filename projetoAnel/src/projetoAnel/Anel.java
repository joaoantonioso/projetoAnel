package projetoAnel;
import java.util.*;

public class Anel {
	private List<Integer> conjunto;
	
	public boolean ehAssociativo() {
		for (int a: conjunto) {
			for (int b: conjunto) {
				for (int c: conjunto) {
					int ladoDireito = adicao(adicao(a, b), c);
					int ladoEsquerdo = adicao(a, adicao(b, c));
					
					if (ladoDireito != ladoEsquerdo) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public boolean verificarFechamento(String op) {
		for (int a: conjunto) {
			for (int b: conjunto) {
				if (op.equals("adicao")) {
					int res = adicao(a,b);
					if (!conjunto.contains(res)) {
						return false;
					}
				} else if (op.equals("multiplicacao")) {
					int res = multiplicacao(a,b);
					if (!conjunto.contains(res)) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public boolean ehComutativo(String op) {
		for (int a: conjunto) {
			for (int b: conjunto) {
				int op1 = 0;
				int op2 = 0;
				
				if (op.equals("adicao")) {
					op1 = adicao(a, b);
					op2 = adicao(b, a);
				} else if (op.equals("multiplicacao")) {
					op1 = multiplicacao(a, b);
					op2 = multiplicacao(b, a);
					}
				
				if (op1 != op2) {
					return false;
				}
				
			}
		}
		return true;
	}
	
	public boolean temInverso(String op) {
		for (int a: conjunto) {
			boolean temInverso = false;
			
			for (int b: conjunto) {
				if (op.equals("adicao")) {
					if (adicao(a, b) == 0) {
						temInverso = true;
						break;
					}
				} else if (op.equals("multiplicacao")) {
					if (multiplicacao(a, b) == 1) {
						temInverso = true;
						break;
					}
				}
				if (!temInverso) {
					return false;
				}
			}
		}
		return true;
	}
	
	private int adicao(int a, int b) {
		return a + b;
	}
	
	private int multiplicacao(int a, int b) {
		return a * b;
	}
	
	public boolean verificarIdentidadeAdicao() {
		if (conjunto.contains(0)) {
			return true;
		}
		return false;
	}
	
	public boolean verificarIdentidadeMultiplicacao() {
		if (conjunto.contains(1)) {
			return true;
		}
		return false;
	}
}
