import java.util.Scanner;

public class JogoTesouro {

    public JogoTesouro() {
        Scanner leitura = new Scanner(System.in);
        int tamanhoIlha = 15;

        // VETORES PARALELOS
        String[] ilha = new String[tamanhoIlha];
        boolean[] explorado = new boolean[tamanhoIlha];

        // Variáveis locais
        int pontuacao = 0;
        int tentativas = 8;
        int tesourosEncontrados = 0;
        int armadilhasEncontradas = 0;
        int posicoesVaziasExploradas = 0;
        int totalDeTesouros = 0;

        // Inicialização
        inicializarIlha(ilha);
        inicializarControleExplorado(explorado);
        totalDeTesouros = contarTesourosNaIlha(ilha);

        System.out.println("Bem-vindo ao jogo Ilha dos Tesouros!");
        System.out.println();
        System.out.println("A ilha possui 15 posições, numeradas de 0 a 14.");
        System.out.println("Você possui 8 tentativas para encontrar os tesouros.");
        System.out.println();

        int opcao;
        do {
            opcao = lerOpcaoMenu(leitura);

            switch (opcao) {
                case 1:
                    exibirInstrucoes();
                    break;
                case 2:
                    mostrarMapaExplorado(explorado);
                    break;
                case 3:
                	if (tentativas <= 0) {
                	    System.out.println("Você não tem mais tentativas!");
                	    break;
                	}

                	int posicao = lerPosicao(leitura, explorado);

                	// --- LÓGICA DO JOGO ---

                	// 1. Marca como explorado
                	explorado[posicao] = true;

                	// 2. Pega o item
                	String item = ilha[posicao];

                	// 3. Calcula pontos
                	pontuacao += verificarJogada(item);

                	// 4. Atualiza contadores
                	if (eTesouro(item)) {
                	    tesourosEncontrados++;
                	} else if (eArmadilha(item)) {
                	    armadilhasEncontradas++;
                	} else {
                	    posicoesVaziasExploradas++;
                	}

                	// 5. Diminui tentativa
                	tentativas--;

                	// 6. Verifica fim de jogo
                	if (verificarFimDeJogo(tentativas, tesourosEncontrados, totalDeTesouros)) {

                	    mostrarResultadoFinal(
                	        pontuacao,
                	        tesourosEncontrados,
                	        armadilhasEncontradas,
                	        posicoesVaziasExploradas,
                	        tesourosEncontrados == totalDeTesouros
                	    );

                	    System.out.print("Deseja jogar novamente? (S/N): ");
                	    String resposta = leitura.next().toUpperCase();

                	    if (resposta.equals("S")) {

                	        reiniciarJogo(ilha, explorado);

                	        pontuacao = 0;
                	        tentativas = 8;
                	        tesourosEncontrados = 0;
                	        armadilhasEncontradas = 0;
                	        posicoesVaziasExploradas = 0;
                	        totalDeTesouros = contarTesourosNaIlha(ilha);

                	        System.out.println("Nova partida iniciada!");
                	        System.out.println();

                	    } else {
                	        System.out.println("Jogo encerrado.");
                	        return;
                	    }
                	}
                	break;
                case 4:
                    mostrarStatus(pontuacao, tentativas, tesourosEncontrados, armadilhasEncontradas, posicoesVaziasExploradas);
                    break;
                case 5:
                	System.out.println("Encerrando jogo.");
                	break;                    
            }
        } while (opcao != 5);
        
        leitura.close();
    }

    // --- MÉTODOS SOLICITADOS ---

    // Retorna os pontos ganhos/perdidos
    public int verificarJogada(String item) {
        if (item.equals("OURO")) {
            System.out.println("Você encontrou: OURO (+10 pontos)");
            return 10;
        } else if (item.equals("DIAMANTE")) {
            System.out.println("Você encontrou: DIAMANTE (+20 pontos)");
            return 20;
        } else if (item.equals("RUBI")) {
            System.out.println("Você encontrou: RUBI (+15 pontos)");
            return 15;
        } else if (item.equals("BURACO")) {
            System.out.println("Você encontrou: BURACO (-5 pontos)");
            return -5;
        } else if (item.equals("COBRA")) {
            System.out.println("Você encontrou: COBRA (-10 pontos)");
            return -10;
        } else if (item.equals("ESPINHOS")) {
            System.out.println("Você encontrou: ESPINHOS (-7 pontos)");
            return -7;
        } 
        
        System.out.println("Você encontrou: VAZIO. Boa sorte na próxima!");
        return 0;
    }

    // Métodos auxiliares booleanos
    public boolean eTesouro(String item) {
        return item.equals("OURO") || item.equals("DIAMANTE") || item.equals("RUBI");
    }

    public boolean eArmadilha(String item) {
        return item.equals("BURACO") || item.equals("COBRA") || item.equals("ESPINHOS");
    }
    
    public boolean posicaoValida(int posicao) {
        return posicao >= 0 && posicao < 15;
    }

    public boolean posicaoJaExplorada(int posicao, boolean[] explorado) {
        return explorado[posicao];
    }
    
    public boolean verificarFimDeJogo(int tentativas, int tesouros, int total) {
        return tentativas == 0 || tesouros == total;
    }
    
    public boolean opcaoValida(int opcao) {
    	return opcao > 0 && opcao <= 5;
    }

    // --- OUTROS MÉTODOS NECESSÁRIOS ---

    public void exibirMenu() {
        System.out.println("===== ILHA DOS TESOUROS =====");
        System.out.println("1 - Mostrar instruções");
        System.out.println("2 - Mostrar mapa");
        System.out.println("3 - Jogar");
        System.out.println("4 - Mostrar status");
        System.out.println("5 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    public int lerOpcaoMenu(Scanner leitura) {
    	int opcao;
    	boolean eOpcaoValida;
    	
    	do {
        	exibirMenu();
    		opcao = leitura.nextInt();
    		eOpcaoValida = opcaoValida(opcao);
    		
    		if (!eOpcaoValida) {    			
    			System.out.println("Opção inválida, por favor tente novamente.");
    		}
    	} while (!eOpcaoValida);
    	
        return opcao;
    }

    public void exibirInstrucoes() {
        System.out.println("...Instruções do jogo...");
        System.out.println("- A ilha tem 15 posições numeradas de 0 a 14.");
        System.out.println("- Você tem 8 tentativas.");
        System.out.println("- Tesouros: OURO (+10), DIAMANTE (+20), RUBI (+15).");
        System.out.println("- Armadilhas: BURACO (-5), COBRA (-10), ESPINHOS (-7).");
        System.out.println("- Posições vazias não somam pontos, mas gastam tentativas.");
        System.out.println("- Você não pode explorar a mesma posição duas vezes.");
        System.out.println();
    }

    public void inicializarIlha(String[] ilha) {
        for (int i = 0; i < ilha.length; i++) {
            ilha[i] = sortear();
        }
    }
    
	private String sortear() {
		String[] tipos = { "VAZIO", "OURO", "DIAMANTE", "RUBI", "BURACO", "COBRA", "ESPINHOS" };
		int posicao = (int) (Math.random() * 7);
		return tipos[posicao];
	}
    
    public void inicializarControleExplorado(boolean[] explorado) {
        for (int i = 0; i < explorado.length; i++) {
            explorado[i] = false;
        }
    }

    public void mostrarMapaExplorado(boolean[] explorado) {
        System.out.println("Mapa da ilha:");
        for (int i = 0; i < explorado.length; i++) {
            if (explorado[i]) {
                System.out.println("[" + i + "] EXPLORADO");
            } else {
                System.out.println("[" + i + "] ?");
            }
        }
        System.out.println();
    }

    public void mostrarStatus(int pontuacao, int tentativas, int tesouros, int armadilhas, int vazias) {
        System.out.println("Status atual:");
        System.out.println("Pontuação: " + pontuacao + " pontos");
        System.out.println("Tentativas restantes: " + tentativas);
        System.out.println("Tesouros encontrados: " + tesouros);
        System.out.println("Armadilhas encontradas: " + armadilhas);
        System.out.println("Posições vazias exploradas: " + vazias);
        System.out.println();
    }

    public int lerPosicao(Scanner leitura, boolean[] explorado) {
    	int posicao;
    	boolean encontrouPosicaoValida = false;
    
    	do  {
            System.out.print("Escolha uma posição de 0 a 14: ");
    		posicao = leitura.nextInt();    		
    		
    	    if (!posicaoValida(posicao)) {
    	        System.out.println("Posição inválida!");
    	    } else {
    	    	if(posicaoJaExplorada(posicao, explorado)) { 
    	    		System.out.println("Esta posição já foi explorada! Escolha outra.");
    	    	} else {	
    	    		encontrouPosicaoValida = true;
    	    	}
    	    }
    	} while (!encontrouPosicaoValida);
        
        return posicao;
    }

    public int contarTesourosNaIlha(String[] ilha) {
        int contador = 0;
        for (int i = 0; i < ilha.length; i++) {
            if (eTesouro(ilha[i])) {
                contador++;
            }
        }
        return contador;
    }

    public void reiniciarJogo(String[] ilha, boolean[] explorado) {
        inicializarControleExplorado(explorado);
        inicializarIlha(ilha);
    }

    public void mostrarResultadoFinal(int pontuacao, int tesouros, int armadilhas, int vazias, boolean venceu) {
        System.out.println();
        System.out.println("Fim de jogo!");
        System.out.println("Tesouros encontrados: " + tesouros);
        System.out.println("Armadilhas encontradas: " + armadilhas);
        System.out.println("Posições vazias exploradas: " + vazias);
        System.out.println("Pontuação final: " + pontuacao + " pontos");

        if (venceu) {
            System.out.println("Parabéns! Você venceu!");
        } else {
            System.out.println("Você perdeu. Tente novamente!");
        }
    }
}