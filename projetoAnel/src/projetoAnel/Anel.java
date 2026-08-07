package projetoAnel;
import java.util.*;

/**
 * Essa classe analisa vários fatores, a partir de um conjunto e suas tabelas
 * de adição e multiplicação, para definir se o conjunto é um corpo, um anel 
 * comutativo com identidade, um anel comutativo, um anel com identidade, um
 * anel, ou não é um anel.
 */
public class Anel {
	private int[][] adicao;
	private int[][] adicaoI;
	private int[][] multiplicacao;
	private int[][] multiplicacaoI;
	private LinkedHashSet<Integer> conjunto;
	private List<Integer> elementos;
	private Map<Integer, Integer> valorParaIndice;
	
	/**
	 * Constrói uma instância de Anel.
	 * 
	 * @param adicao tabela de adição do conjunto
	 * @param multiplicacao tabela de multiplicação do conjunto
	 * @param conjunto elementos do conjunto
	 */
	public Anel(int[][] adicao, int[][]multiplicacao, LinkedHashSet<Integer> conjunto) {
		this.adicao = adicao;
		this.multiplicacao = multiplicacao;
		
		this.elementos = new ArrayList<>(conjunto);
	    this.valorParaIndice = new HashMap<>();
		
		for (int i = 0; i < this.elementos.size(); i++) {
	        this.valorParaIndice.put(this.elementos.get(i), i);
	    }
		this.adicaoI = converterParaIndices(adicao);
		this.multiplicacaoI = converterParaIndices(multiplicacao);
		this.conjunto = conjunto;
	} 
	
	
	private int[][] converterParaIndices(int[][] tabelaOriginal) {
		int n = tabelaOriginal.length;
	    int[][] tabelaIndices = new int[n][n];

	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            int valorReal = tabelaOriginal[i][j];
	            
	            Integer indiceMapped = this.valorParaIndice.get(valorReal);
	            
	            if (indiceMapped == null) {
	                throw new IllegalArgumentException("Não é fechado, logo não é Anel");
	            }
	            
	            tabelaIndices[i][j] = indiceMapped;
	        }
	    }
	    return tabelaIndices;
    }
	
	/**
	 * Checa e retorna tipo do conjunto, se é um anel, o tipo e se é um corpo. 
	 * Analisa se é um anel, se é comutativo na multiplicação, se possui elemento
	 * identidade na multiplicação e se possui os inversos dos elementos não nulos
	 * da multiplicação.
	 * 
	 * @return tipo do conjunto
	 */
	private String checarTipo() {
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
	
	/**
	 * Função para definir se é um anel. Analisa se é um grupo abeliano na soma,
	 * se é fechado na multiplicação, se é associativo na multiplicação e se é
	 * distributivo. 
	 * 
	 * @return true se for um anel; false se não for um anel
	 */
	private boolean ehAnel() {
		if (ehGrupoAbelianoAdicao() && ehFechado(this.multiplicacao) && ehAssociativo(this.multiplicacaoI) && ehDistributivo()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Função para definir se é um grupo abeliano na adição. Analisa alguns
	 * fatores da tabela de adição, como se é fechado, se é associativo, se 
	 * é comutativo, se possui elemento identidade e se possui os inversos
	 * dos elementos.
	 * 
	 * @return true se for um grupo abeliano; false se não for um grupo abeliano
	 */
	private boolean ehGrupoAbelianoAdicao() {
		if (!(ehFechado(this.adicao) && ehAssociativo(this.adicaoI) && ehComutativo(this.adicao)
				&& possuiElementoIdentidadeAdicao() && possuiElementoInversoAdicao())) {
			return false;
		}
		return true;
	}
	
	/**
	 * Analisa se o conjunto é fechado na operação do parâmetro.
	 * 
	 * @param operacao tabela da operacao de adição ou multiplicação
	 * @return true se for fechado; false se não for fechado 
	 */
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
	
	/**
	 * Analisa se o conjunto é associativo na operação do parâmetro.
	 * 
	 * @param operacao tabela da operacao de adição ou multiplicação
	 * @return true se for associativo; false se não for associativo
	 */
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
	
	/**
	 * Analisa se o conjunto é comutativo na operação do parâmetro.
	 * 
	 * @param operacao tabela da operacao de adição ou multiplicação
	 * @return true se for comutativo; false se não for comutativo
	 */
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
	
	/**
	 * Procura no conjunto se ele possui o elemento identidade da adição.
	 * 
	 * @return o elemento identidade, caso possua o elemento identidade da adição,
	 *  -1 caso não possua o elemento identidade da adição
	 */
	private int elementoIdentidadeAdicao() {
	    List<Integer> elem = new ArrayList<>(this.conjunto);
	    for (int e : elem) {
	        int idx = elem.indexOf(e);
	        boolean ehZero = true;
	        for (int i = 0; i < this.adicao.length; i++) {
	            if (this.adicao[i][idx] != elem.get(i) || this.adicao[idx][i] != elem.get(i)) {
	                ehZero = false;
	                break;
	            }
	        }
	        if (ehZero) return e;
	    }
	    return -1;
	}
	
	/**
	 * Analisa se o conjunto possui o elemento identidade da adição de
	 * inteiros, o elemento 0.
	 * 
	 * @return true se possuir o elemento identidade da adição;
	 *  false se não possuir o elemento identidade da adição
	 */
	private boolean possuiElementoIdentidadeAdicao() {
		if(elementoIdentidadeAdicao() != -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Procura no conjunto se ele possui o elemento identidade da multiplicação.
	 * 
	 * @return o elemento identidade, caso possua o elemento identidade da multiplicação, -1 caso não possua o elemento identidade da multiplicação
	 */
	private int elementoIdentidadeMultiplicacao() {
	    List<Integer> elem = new ArrayList<>(this.conjunto);
	    for (int e : elem) {
	        int idx = elem.indexOf(e);
	        boolean ehUm = true;
	        for (int i = 0; i < this.multiplicacao.length; i++) {
	            if (this.multiplicacao[i][idx] != elem.get(i) || this.multiplicacao[idx][i] != elem.get(i)) {
	                ehUm = false;
	                break;
	            }
	        }
	        if (ehUm) return e;
	    }
	    return -1;
	}
	
	/**
	 * Analisa se o conjunto possui o elemento identidade da multiplicação de
	 * inteiros, o elemento 1.
	 * 
	 * @return true se possuir o elemento identidade da multiplicação;
	 *  false se não possuir o elemento identidade da multiplicação
	 */
	private boolean possuiElementoIdentidadeMultiplicacao() {
		if(elementoIdentidadeMultiplicacao() != -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Analisa se o conjunto possui os inversos de todos os elementos
	 * na adição.
	 * 
	 * @return true se possuir os inversos de todos os elementos da adição;
	 *  false se não possuir os inversos de todos os elementos da adição
	 */
	private boolean possuiElementoInversoAdicao() {
		int e = elementoIdentidadeAdicao();
		for (int a=0; a < this.adicao.length; a++) {
			boolean temInverso = false;
			for (int b=0; b < this.adicao.length; b++) {
				if (this.adicao[a][b] == e) {
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
	
	/**
	 * Analisa se o conjunto possui os inversos dos elementos não nulos da
	 * multiplicação
	 * 
	 * @return true se possuir os inversos dos elementos não nulos da multiplicação;
	 *  false se não possuir os inversos dos elementos não nulos da multiplicação
	 */
	private boolean possuiElementoInversoMultiplicacaoNaoNulos() {
		Map<Integer, Integer> inversos = encontrarInversosMultiplicacaoNaoNulos();
	    
	    
	    if (elementoIdentidadeAdicao() == -1 || elementoIdentidadeMultiplicacao() == -1) {
	        return false;
	    }

	    int tamanhoConjuntoSemZero = conjunto.size() - 1;
	    
	    if (tamanhoConjuntoSemZero <= 0) {
	        return false;
	    }
	    
	    return inversos.size() == tamanhoConjuntoSemZero;
	}
	
	/**
	 * Procura quais elementos não nulos do conjunto possuem um inverso multiplicativo.
	 * 
	 * @return Lista com todos os elementos que possuem inverso.
	 */
	private Map<Integer, Integer> encontrarInversosMultiplicacaoNaoNulos() {
		List<Integer> elementos = new ArrayList<>(this.conjunto);
	    Map<Integer, Integer> inversos = new LinkedHashMap<>();
	    
	    Integer eA = elementoIdentidadeAdicao();
	    Integer eM = elementoIdentidadeMultiplicacao();

	    if (eA == -1 || eM == -1 || eM != 1) {
	        return inversos;
	    }

	    int indiceEA = elementos.indexOf(eA);
	    
	    for (int a = 0; a < this.multiplicacao.length; a++) {
	        if (a == indiceEA) {
	            continue; 
	        }
	        for (int b = 0; b < this.multiplicacao.length; b++) {
	            if (b == indiceEA) {
	                continue;
	            }

	            if (this.multiplicacao[a][b] == eM && this.multiplicacao[b][a] == eM) {
	                inversos.put(elementos.get(a), elementos.get(b));
	                break;
	            }
	        }
	    }
	    return inversos;
	}
	
	/**
	 * Analisa se o conjunto é distributivo.
	 * 
	 * @return true se for distributivo; false se não for distributivo
	 */
	private boolean ehDistributivo() {
		for (int a=0; a < this.multiplicacao.length; a++) {
			for (int b=0; b < this.multiplicacao.length; b++) {
				for (int c = 0; c < this.multiplicacao.length; c++) {
					int ladoEsquerdo1 = this.multiplicacaoI[a][this.adicaoI[b][c]]; 
					int ladoDireito1 = this.adicaoI[this.multiplicacaoI[a][b]][this.multiplicacaoI[a][c]]; 
					int ladoEsquerdo2 = this.multiplicacaoI[this.adicaoI[a][b]][c]; 
					int ladoDireito2 =  this.adicaoI[this.multiplicacaoI[a][c]][this.multiplicacaoI[b][c]]; 
					
					if(ladoEsquerdo1 != ladoDireito1 || ladoEsquerdo2 != ladoDireito2) {
						return false;
					}
					
					
				}
			}
		}
		return true;
	}
	
	/**
	 * Encontra e retorna os divisores de zero do conjunto Zn. Um elemento
	 * é divisor de zero se não for nulo e o mdc entre ele e o módulo for
	 * maior que 1.
	 * 
	 * @return lista com os divisores de zero do conjunto
	 */
	private List<Integer> encontrarDivisoresZero() {
		List<Integer> elementos = new ArrayList<>(this.conjunto);
	    List<Integer> divisoresZero = new ArrayList<>();
	    
	    
	    int e = elementoIdentidadeAdicao(); 
	    int indiceNeutro = elementos.indexOf(e);

	    
	    for (int a = 0; a < this.multiplicacao.length; a++) {
	        if (a == indiceNeutro) continue; 

	        for (int b = 0; b < this.multiplicacao.length; b++) {
	            if (b == indiceNeutro) continue; 

	            
	            if (this.multiplicacao[a][b] == e) {
	                int valorA = elementos.get(a);
	                if (!divisoresZero.contains(valorA)) {
	                    divisoresZero.add(valorA);
	                }
	                break;
	            }
	        }
	    }
	    return divisoresZero;
	}
	
	public String exibirInformacoes() {
		StringBuilder sb = new StringBuilder();
		sb.append("=".repeat(30) + "\n");
		sb.append("ANÁLISE COMPLETA DO CONJUNTO\n");
		sb.append("=".repeat(30) + "\n");
		sb.append("\n");
		
		sb.append("PROPRIEDADES DA ADIÇÃO:\n");
		sb.append(String.format("--> Fechado? %b\n", this.ehFechado(adicao)));
		sb.append(String.format("--> Associativo? %b\n", this.ehAssociativo(adicaoI)));
		sb.append(String.format("--> Possui o elemento identidade? %b\n", this.possuiElementoIdentidadeAdicao()));
		sb.append(String.format("--> Possui elemento inverso para cada elemento do conjunto? %b\n", this.possuiElementoInversoAdicao()));
		sb.append(String.format("--> Comutativo? %b\n", this.ehComutativo(adicao)));
		sb.append(String.format("--> Forma um grupo abeliano? %b\n", this.ehGrupoAbelianoAdicao()));
		sb.append("\n");
		
		sb.append("PROPRIEDADES DA MULTIPLICAÇÃO:\n");
		sb.append(String.format("--> Fechado? %b\n", this.ehFechado(multiplicacao)));
		sb.append(String.format("--> Associativo? %b\n", this.ehAssociativo(multiplicacaoI)));
		sb.append(String.format("--> Distributivo? %b\n", this.ehDistributivo()));
		sb.append(String.format("--> Comutativo? %b\n", this.ehComutativo(multiplicacao)));
		sb.append(String.format("--> Possui o elemento identidade? %b\n", this.possuiElementoIdentidadeMultiplicacao()));
		sb.append("--> Quais elementos não nulos possuem um inverso?\n");
		
		Map<Integer, Integer> inversos = this.encontrarInversosMultiplicacaoNaoNulos();
		if (inversos.size() == 0) {
			sb.append("Não possui nenhum elemento com inverso para multiplicação\n");
		} else {
			for (Map.Entry<Integer, Integer> entry : inversos.entrySet()) {
				sb.append(String.format("%d - %d\n", entry.getKey(), entry.getValue()));
			}
		}
		
		sb.append(String.format("--> Possui um elemento inverso para cada elemento do conjunto não nulo? %b\n", this.possuiElementoInversoMultiplicacaoNaoNulos()));
		sb.append("\n");
		
		sb.append("--> Quais elementos são divisores de Zero?\n");
		
		List<Integer> divisoresZero = this.encontrarDivisoresZero();
		if(divisoresZero.size() == 0) {
			sb.append("Não possui elemento divisor de zero\n");
		} else {
			sb.append(divisoresZero + "\n");
		}
		sb.append("\n");
		
		sb.append(String.format("Conclusão: %s", this.checarTipo()));
		
		return sb.toString();
	}
}
