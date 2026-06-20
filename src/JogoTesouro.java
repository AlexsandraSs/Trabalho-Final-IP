

import java.util.Scanner;

public class JogoTesouro { 
	Scanner leitura = new Scanner(System.in); 
	String[] ilha = new String[15]; 
	boolean[] exploracao = new boolean[15]; 
	int pontuacao = 0; 
	int tentativas = 8; 
	int tesourosEncontrados = 0; 
	int armadilhasEncontradas = 0; 
	int posicoesVaziasExploradas = 0; 
	int totalDeTesouros = 0; 

	public JogoTesouro() { 

		for (int i = 0; i < ilha.length; i++) { 
			ilha[i] = sortear();
		}

		for (int i = 0; i < ilha.length; i++) {
			if (ilha[i].equals("OURO")) { 
				totalDeTesouros++; 
			} else if (ilha[i].equals("DIAMANTE")) { 
				totalDeTesouros++;
			} else if (ilha[i].equals("RUBI")) {
				totalDeTesouros++; 
			}
		}

		inicia(); 
	}

	public void inicia() { 
		System.out.println("Bem-vindo ao jogo Ilha dos Tesouros!"); 
		System.out.println("A ilha possui 15 posições, numeradas de 0 a 14."); 
		System.out.println("Você possui 8 tentativas para encontrar os tesouros."); 
		int opcao = 0; 
		while (opcao != 5) { 
			System.out.printf("...ILHA DOS TESOUROS...%n1 - Mostrar instruçoes %n2 - Mostrar mapa %n3 - Jogar %n4 - Mostrar status %n5 - Sair %nJogador escolha sua opção: ");
			
			opcao = leitura.nextInt(); 
			System.out.println();

			switch (opcao) { 
			case 1:
				mostrarInstrucoes(); 
				break; 
			case 2:
				mostrarMapa(); 
				break; 
			case 3:
				jogar(); 
				break; 
			case 4:
				mostrarStatus(); 
				break; 
			case 5:
				System.out.println("Encerrando jogo"); 
				break; 
			default:
				System.out.println("Opção invalida, por favor tente novamente"); 
			}
		}
	}

	public void mostrarInstrucoes() {
		System.out.println("...Instruções do jogo...");
		System.out.println("A ilha tem 15 posições numeradas de 0 a 14");
		System.out.println("Você tem 8 tentativas");
		System.out.println("Os tesouros são: OURO(+10), DIAMANTE(+20), RUBI(+15)");
		System.out.println("As armadilhas são: BURACO(-5), COBRA(-10), ESPINHOS(-7)");
		System.out.println("Posições vazias não somam, nem diminuem pontos mas diminue suas tentativas!");
		System.out.println("Escolha uma posição para explorar, encontre tesouros e tente fazer a maior pontuação possivel");
		System.out.println();
		}

	public void mostrarMapa() {
		for (int i = 0; i < exploracao.length; i++) {

			if (exploracao[i] == true) {
				System.out.println("[" + i + "] EXPLORADO");
			} else {
				System.out.println("[" + i + "] ?");
			}
		}
		System.out.println();
	}

	private String sortear() {
		String[] tipos = { "VAZIO", "OURO", "DIAMANTE", "RUBI", "BURACO", "COBRA", "ESPINHOS" };
		int posicao = (int) (Math.random() * 7);
		return tipos[posicao];
	}

	public void jogar() {
		System.out.println("Olá vamos jogar! Informe uma posição de 0 a 14:");
		int posicao = leitura.nextInt();

		while (true) {
			if (posicao >= 0 && posicao <= 14) {
				if (!exploracao[posicao]) {
					break;
				}
				System.out.println("Esta posição ja foi explorada! Escolha outra.");
			} else {
				System.out.println("Posição Invalida, Informe uma posição de 0 a 14: ");
			}
			posicao = leitura.nextInt();
		}
		exploracao[posicao] = true;

		String item = ilha[posicao];

		if (item.equals("OURO")) {
			pontuacao += 10;
			tesourosEncontrados++;
			System.out.println("Você encontrou: OURO (+10 pontos)");
		} else if (item.equals("DIAMANTE")) {
			pontuacao += 20;
			tesourosEncontrados++;
			System.out.println("Você encontrou: DIAMANTE (+20 pontos)");
		} else if (item.equals("RUBI")) {
			pontuacao += 15;
			tesourosEncontrados++;
			System.out.println("Você encontrou: RUBI (+15 pontos)");
		} else if (item.equals("BURACO")) {
			pontuacao -= 5;
			armadilhasEncontradas++;
			System.out.println("Você encontrou: BURACO (- 5 pontos)");
		} else if (item.equals("COBRA")) {
			pontuacao -= 10;
			armadilhasEncontradas++;
			System.out.println("Você encontrou: COBRA (-10 pontos)");
		} else if (item.equals("ESPINHOS")) {
			pontuacao -= 7;
			armadilhasEncontradas++;
			System.out.println("Você encontrou: ESPINHOS (-7 pontos)");
		} else if (item.equals("VAZIO")) {
			posicoesVaziasExploradas++;
			System.out.println("Você encontrou: VAZIO Boa sorte na proxima");
		}
		tentativas--;

		if (tentativas == 0 || tesourosEncontrados == totalDeTesouros) {
			System.out.println("Fim de jogo!");
			System.out.println();
			System.out.println("Tesouros encontrados: " + tesourosEncontrados);
			System.out.println("Armadilhas encontradas: " + armadilhasEncontradas);
			System.out.println("Posições vazias exploradas: " + posicoesVaziasExploradas);
			System.out.println("Pontuação final:" + pontuacao + " pontos");

			if (tesourosEncontrados == totalDeTesouros) {
				System.out.println("Parabens você venceu!");
			}

			String resposta = "";
			while (!resposta.equals("S") && !resposta.equals("N")) {
				System.out.println("Deseja jogar novamente? (S/N):");
				resposta = leitura.next().toUpperCase();
			}
			if (resposta.equals("S")) {
				reiniciar();
			} else {
				System.out.println("Jogo encerrado.");
				leitura.close();
			}
		}
	}

	public void mostrarStatus() {
		System.out.println("Sua pontuação atual é: " + pontuacao + " pontos.");
		System.out.println("Suas tentativas restantes são: " + tentativas + " tentativas. ");
		System.out.println("Tesouros encontrados: " + tesourosEncontrados);
		System.out.println("Armadilhas Encontradas: " + armadilhasEncontradas);
		System.out.println("Posições vazias exploradas: " + posicoesVaziasExploradas);
		System.out.println();
	}

	private void reiniciar() {
		pontuacao = 0;
		tentativas = 8;
		tesourosEncontrados = 0;
		armadilhasEncontradas = 0;
		posicoesVaziasExploradas = 0;
		totalDeTesouros = 0;

		for (int i = 0; i < exploracao.length; i++) {
			exploracao[i] = false;
		}

		for (int i = 0; i < ilha.length; i++) {
			ilha[i] = sortear();
		}

		for (int i = 0; i < ilha.length; i++) {
			if (ilha[i].equals("OURO")) {
				totalDeTesouros++;
			} else if (ilha[i].equals("DIAMANTE")) {
				totalDeTesouros++;
			} else if (ilha[i].equals("RUBI")) {
				totalDeTesouros++;
			}
		}
	}
}
