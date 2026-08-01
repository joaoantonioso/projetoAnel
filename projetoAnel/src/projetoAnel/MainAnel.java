package projetoAnel;

public class MainAnel {
public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("=".repeat(27));
		System.out.println("SISTEMA VERIFICADOR DE ANEL");
		System.out.println("=".repeat(27));
		System.out.println();
		
		System.out.println("Quantos elementos esse conjunto possui?: ");
		int quantElem = sc.nextInt();
		sc.nextLine();
		
		Set<Integer> conjunto = definirConjunto(quantElem, sc);
		System.out.println("Elementos do conjunto: " + conjunto);
		
		Map<Integer, int[]> tabelaAdicao = definirTabela("adição", quantElem, sc);
		System.out.println("Tabela de adição: ");
		exibirTabela(tabelaAdicao);
		System.out.println("");
		
		Map<Integer, int[]> tabelaMultiplicacao = definirTabela("multiplição", quantElem, sc);
		System.out.println("Tabela de multiplação: ");
		exibirTabela(tabelaMultiplicacao);
		System.out.println("");
		
	}
	
	public static Set<Integer> definirConjunto(int quantElem, Scanner sc) {
		Set<Integer> conjunto = new LinkedHashSet<>();
		
		System.out.println("=".repeat(32));
		System.out.println("DEFINIR OS ELEMENTOS DO CONJUNTO");
		System.out.println("=".repeat(32));
		
		System.out.println("Quais os elementos desse conjunto? (separado por espaço): ");
		for (int i = 0; i < quantElem; i++) {
			System.out.println(String.format("%d° elemento: ", i+1));
			int num = sc.nextInt();
			sc.nextLine();
			if (conjunto.contains(num)) {
				System.out.println("Esse número já está inserido no conjunto!");
				i--;
			}
			
			conjunto.add(num);
		}
		
		return conjunto;
	}
	
	public static Map<Integer, int[]> definirTabela(String operacao, int quantElem, Scanner sc) {
		Map<Integer, int[]> elementosPorLinha = new LinkedHashMap<>();
		
		System.out.println("=".repeat(32));
		System.out.println("DEFINIR A TABELA DE " + operacao);
		System.out.println("=".repeat(32));
		
		System.out.println(String.format("Quais os elementos da tabela de %s? (separado por espaço)", operacao));
		for (int i = 0; i < quantElem; i++) {
			System.out.println(String.format("%d° linha: ", i+1));
			int[] elementosLinha = new int[quantElem];
			for (int j = 0; j < elementosLinha.length; j++) {
				elementosLinha[j] = sc.nextInt();
			}
			sc.nextLine();
			
			elementosPorLinha.put(i, elementosLinha);
		}
		
		return elementosPorLinha;
	}

	public static void exibirTabela(Map<Integer, int[]> tabela) {
		for (Integer linha : tabela.keySet()) {
			int[] elemLinha = tabela.get(linha);
			for (int i = 0; i < elemLinha.length; i++) {
				System.out.print(elemLinha[i] + " ");
			}
			System.out.println("");
		}
	}
}
