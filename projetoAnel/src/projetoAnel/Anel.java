package projetoAnel;
import java.util.*;

public class Anel {
	private int[][] adicao;
	private int[][] multiplicacao;
	private LinkedHashSet<Integer> conjunto;
	
	public Anel(int[][] adicao, int[][]multiplicacao, LinkedHashSet<Integer> conjunto) {
		this.adicao = adicao;
		this.multiplicacao = multiplicacao;
		this.conjunto = conjunto;
		
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
		if (ehGrupoAbelianoAdicao() && ehFechado(this.multiplicacao) && ehAssociativo(this.multiplicacao) && ehDistributivo()) {
			return true;
		}
		return false;
	}
	
	private boolean ehGrupoAbelianoAdicao() {
		if (!(ehFechado(this.adicao) && ehAssociativo(this.adicao) && ehComutativo(this.adicao)
				&& possuiElementoIdentidadeAdicao() && possuiElementoInversoAdicao())) {
			return false;
		}
		return true;
	}
	
	private boolean ehFechado(int[][] operacao) {
		for (int a=0; a < operacao.length; a++) {
			for (int b=0; b < operacao.length; b++) {
				if (!this.conjunto.contains(operacao[a][b])) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean ehAssociativo(int[][] operacao) {
		for (int a=0; a < operacao.length; a++) {
			for (int b=0; b < operacao.length; b++) {
				for (int c=0; c < operacao.length; c++) {
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
		for (int a=0; a < operacao.length; a++) {
			for (int b=0; b < operacao.length; b++) {
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
		for (int a=0; a < this.adicao.length; a++) {
			boolean temInverso = false;
			for (int b=0; b < this.adicao.length; b++) {
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
		Map<Integer, Integer> inversos = encontrarInversosMultiplicacaoNaoNulos();
		int tamanhoConjuntoSemZero = conjunto.size() - 1;
		
		if (inversos.size() == tamanhoConjuntoSemZero) {
			return true;
		}
		return false;
	}
	
	private Map<Integer, Integer> encontrarInversosMultiplicacaoNaoNulos() {
		List<Integer> elementos = new ArrayList<>(this.conjunto);
		Map<Integer, Integer> inversos = new LinkedHashMap<>();
		
		
		int indiceZero = elementos.indexOf(0);
		
		for (int a=0; a < this.multiplicacao.length; a++) {
			if (a == indiceZero) {
				continue;
			}
			for (int b=0; b < this.multiplicacao.length; b++) {
				if (this.multiplicacao[a][b] == 1) {
					inversos.put(a, b);
					break;
				}
			}
		}
		return inversos;
	}
	
	private boolean ehDistributivo() {
		for (int a=0; a < this.multiplicacao.length; a++) {
			for (int b=0; b < this.multiplicacao.length; b++) {
				for (int c = 0; c < this.multiplicacao.length; c++) {
					int ladoEsquerdo1 = this.multiplicacao[a][this.adicao[b][c]]; 
					int ladoDireito1 = this.adicao[this.multiplicacao[a][b]][this.multiplicacao[a][c]]; 
					int ladoEsquerdo2 = this.multiplicacao[this.adicao[a][b]][c]; 
					int ladoDireito2 =  this.adicao[this.multiplicacao[a][c]][this.multiplicacao[b][c]]; 
					if(!(ladoEsquerdo1 == ladoDireito1 || ladoEsquerdo2 == ladoDireito2)) {
						return false;
					}
					
				}
			}
		}
		return true;
	}
	
	public String exibirInformacoes() {
		StringBuilder sb = new StringBuilder();
		sb.append("=".repeat(30) + "\n");
		sb.append("ANÁLISE COMPLETA DO CONJUNTO\n");
		sb.append("=".repeat(30) + "\n");
		sb.append("\n");
		
		sb.append("PROPRIEDADES DA ADIÇÃO:\n");
		sb.append(String.format("--> Fechado? %b\n", this.ehFechado(adicao)));
		sb.append(String.format("--> Associativo? %b\n", this.ehAssociativo(adicao)));
		sb.append(String.format("--> Possui o elemento identidade (zero)? %b\n", this.possuiElementoIdentidadeAdicao()));
		sb.append(String.format("--> Possui elemento inverso para cada elemento do conjunto? %b\n", this.possuiElementoInversoAdicao()));
		sb.append(String.format("--> Comutativo? %b\n", this.ehComutativo(adicao)));
		sb.append(String.format("--> Forma um grupo abeliano? %b\n", this.ehGrupoAbelianoAdicao()));
		sb.append("\n");
		
		sb.append("PROPRIEDADES DA MULTIPLICAÇÃO:\n");
		sb.append(String.format("--> Fechado? %b\n", this.ehFechado(multiplicacao)));
		sb.append(String.format("--> Associativo? %b\n", this.ehAssociativo(multiplicacao)));
		sb.append(String.format("--> Distributivo? %b\n", this.ehDistributivo()));
		sb.append(String.format("--> Comutativo? %b\n", this.ehComutativo(multiplicacao)));
		sb.append(String.format("--> Possui o elemento identidade (um)? %b\n", this.possuiElementoIdentidadeMultiplicacao()));
		sb.append("--> Quais elementos não nulos possuem um inverso?\n");
		
		Map<Integer, Integer> inversos = this.encontrarInversosMultiplicacaoNaoNulos();
		if (inversos.size() == 0) {
			sb.append("Não possui nenhum elemento com inverso para multiplicação");
		} else {
			for (Map.Entry<Integer, Integer> entry : inversos.entrySet()) {
				sb.append(String.format("%d - %d\n", entry.getKey(), entry.getValue()));
			}
		}
		
		sb.append(String.format("Possui um elemento inverso para cada elemento do conjunto não nulo? %b\n", this.possuiElementoInversoMultiplicacaoNaoNulos()));
		sb.append("\n");
		
		sb.append(String.format("Conclusão: %s", this.checarTipo()));
		
		
		
		return sb.toString();
	}
}
