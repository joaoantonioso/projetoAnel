package projetoAnel;
import java.util.*;

public class Anel {
	private int[][] adicao;
	private int[][] multiplicacao;
	private HashSet<Integer> conjunto;
	
	public Anel(int[][] adicao, int[][]multiplicacao) {
		this.adicao = adicao;
		this.multiplicacao = multiplicacao;
		this.conjunto = new HashSet<>();
		for(int i = 1; i < this.adicao.length; i++) {
			this.conjunto.add(adicao[0][i]);
		}
	}
	
	public String checarTipo() {
		if (ehAnel() && ehComutativo(this.multiplicacao) && possuiElementoIdentidadeMultiplicacao() && possuiElementoInversoMultiplicacaoNaoNulos()) {
			return "Esse conjunto é um corpo.";
		}
		if (ehAnel() && ehComutativo(this.multiplicacao) && possuiElementoIdentidadeMultiplicacao()) {
			return "Esse conjunto é um anel comutativo com identidade.";
		}
		if (ehAnel() && ehComutativo(this.multiplicacao)) {
			return "Esse conjunto é um anel comutativo.";
		}
		if (ehAnel() && possuiElementoIdentidadeMultiplicacao()) {
			return "Esse conjunto é um anel com identidade.";
		}
		if (ehAnel()) {
			return "Esse conjunto é um anel.";
		}
		return "Esse conjunto não é um anel.";
	}
	
	private boolean ehAnel() {
		if (!(ehGrupoAbelianoAdicao() && ehFechado(this.multiplicacao) && ehAssociativo(this.multiplicacao) && ehDistributivo())) {
			return false;
		}
		return true;
	}
	
	private boolean ehGrupoAbelianoAdicao() {
		if (!(ehFechado(this.adicao) && ehAssociativo(this.adicao) && ehComutativo(this.adicao)
				&& possuiElementoIdentidadeAdicao() && possuiElementoInversoAdicao())) {
			return false;
		}
		return true;
	}
	
	private boolean ehFechado(int[][] operacao) {
		for (int a=1; a < operacao.length; a++) {
			for (int b=1; b < operacao.length; b++) {
				if (!this.conjunto.contains(operacao[a][b])) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean ehAssociativo(int[][] operacao) {
		for (int a=1; a < operacao.length; a++) {
			for (int b=1; b < operacao.length; b++) {
				for (int c=1; c < operacao.length; c++) {
					int ladoEsquerdo = operacao[operacao[a][b]][c];
					int ladoDireito = operacao[a][operacao[b][c]];
					if (ladoEsquerdo != ladoDireito) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private boolean ehComutativo(int[][] operacao) {
		for (int a=1; a < operacao.length; a++) {
			for (int b=1; b < operacao.length; b++) {
				if (operacao[a][b] != operacao[b][a]) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean possuiElementoIdentidadeAdicao() {
		if(this.conjunto.contains(0)) {
			return true;
		}
		return false;
	}
		
	private boolean possuiElementoIdentidadeMultiplicacao() {
		if(this.conjunto.contains(1)) {
			return true;
		}
		return false;
	}
	
	private boolean possuiElementoInversoAdicao() {
		for (int a=1; a < this.adicao.length; a++) {
			boolean temInverso = false;
			for (int b=1; b < this.adicao.length; b++) {
				if (this.adicao[a][b] == 0) {
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
	
	private boolean possuiElementoInversoMultiplicacaoNaoNulos() {
		for (int a=2; a < this.multiplicacao.length; a++) {
			boolean temInverso = false;
			for (int b=2; b < this.multiplicacao.length; b++) {
				if (this.multiplicacao[a][b] == 1) {
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
	
	private boolean ehDistributivo() {
		for (int a=1; a < this.multiplicacao.length; a++) {
			for (int b=1; b < this.multiplicacao.length; b++) {
				for (int c = 1; c < this.multiplicacao.length; c++) {
					int ladoEsquerdo1 = this.multiplicacao[a][this.adicao[b][c]]; // a . (b + c) 
					int ladoDireito1 = this.adicao[this.multiplicacao[a][b]][this.multiplicacao[a][c]]; // a.b + a.c
					int ladoEsquerdo2 = this.multiplicacao[this.adicao[a][b]][c]; // (a + b) . c
					int ladoDireito2 =  this.adicao[this.multiplicacao[a][c]][this.multiplicacao[b][c]]; // a.c + b.c
					if(!(ladoEsquerdo1 == ladoDireito1 | ladoEsquerdo2 == ladoDireito2)) {
						return false;
					}
					
				}
			}
		}
		return false;
	}
}
