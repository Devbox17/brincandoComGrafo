/*
    Sistemas de Informação
    Algoritmos em Grafos
    Primeiro Trabalho Prático - 10 pontos
    Finalizar a implementação do programa abaixo com as opções do menu
    que estão faltando.
    Grupos de até 3 alunos.
    Data de Entrega: 10/10/2020
*/

import java.io.*;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;

class Main {
    static Scanner entrada = new Scanner(System.in); // objeto para entrada de dados
    static String nomeArquivo = "grafo.txt"; // arquivo texto que contém o grafo

    // exibe menu e lê opção do usuário
    static int menu() {
        int op;

        System.out.println("\nMenu de opções");
        System.out.println("1 - Inserir vértice");
        System.out.println("2 - Remover vértice");
        System.out.println("3 - Inserir aresta");
        System.out.println("4 - Remover aresta");
        System.out.println("5 - Verificar adjacências");
        System.out.println("6 - Graus dos vértices");
        System.out.println("7 - Exibir grafo");
        System.out.println("8 - Verificar euleriano");
        System.out.println("9 - Verificar completo");
        System.out.println("10 - Verificar totalmente desconexo");
        System.out.println("11 - Grafo complementar");
        System.out.println("12 - Carregar grafo");
        System.out.println("13 - Alterar peso aresta");
        System.out.println("14 - Salvar grafo");
        System.out.println("15 - Sair");
        System.out.print("\nOpção: ");

        // leitura de um valor inteiro que é a opção do usuário no menu
        op = Integer.parseInt(entrada.nextLine());

        return (op);
    }

    // carrega o grafo do arquivo para a matriz
    static int carregaGrafo(int[][] G, String[] nomes) throws IOException {
        File arq = new File(nomeArquivo);
        BufferedReader bufLeitura;

        int numVertices, numArestas, i;
        String aresta;
        String[] tokens;

        if (!arq.exists()) // arquivo não existe
        {
            System.out.println("Arquivo com o grafo inexistente.");
            return (0);
        } else // arquivo existe
        {
            bufLeitura = new BufferedReader(new FileReader(nomeArquivo));

            numVertices = Integer.parseInt(bufLeitura.readLine());
            for (i = 0; i < numVertices; i++)
                nomes[i] = bufLeitura.readLine();

            numArestas = Integer.parseInt(bufLeitura.readLine());
            for (i = 0; i < numArestas; i++) {
                aresta = bufLeitura.readLine();

                tokens = aresta.split(" ");

                G[Integer.parseInt(tokens[0]) - 1][Integer.parseInt(tokens[1]) - 1] = Integer.parseInt(tokens[2]);
                G[Integer.parseInt(tokens[1]) - 1][Integer.parseInt(tokens[0]) - 1] = Integer.parseInt(tokens[2]);
            }

            bufLeitura.close();
            return (numVertices);
        }
    }

    // salva o grafo da matriz para o arquivo
    static void salvaGrafo(int[][] G, String[] nomes, int numVertices) throws IOException {
        BufferedWriter bufEscrita = new BufferedWriter(new FileWriter(nomeArquivo, false));

        int i, j, cont = 0;

        bufEscrita.append(numVertices + "\n");

        for (i = 0; i < numVertices; i++)
            bufEscrita.append(nomes[i] + "\n");

        for (i = 0; i < numVertices; i++)
            for (j = 0; j < numVertices; j++)
                if (G[i][j] != 0)
                    cont++;

        bufEscrita.append((cont / 2) + "\n");

        for (i = 0; i < numVertices; i++)
            for (j = i + 1; j < numVertices; j++)
                if (G[i][j] != 0)
                    bufEscrita.append((i + 1) + " " + (j + 1) + " " + G[i][j] + "\n");

        bufEscrita.close();
    }

    // adiciona um vértice (sua linha e sua coluna da matriz)
    static int adicionaVertice(int[][] g, int numVertices) {
        numVertices += 1;

        for (int i = numVertices-1; i < numVertices-1; i++) {
            for (int j = 0; j < numVertices; j++) {
                g[i][j] = 0;
            }
        }

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                System.out.printf("%3d ", g[i][j]);
            }
            System.out.printf("\n");
        }

        return numVertices;
    }

    // remove um vértice (sua linha e sua coluna da matriz)
    static void removeVertice(int[][] G, int n) {
        int i, j;

        for (j = 0; j <= n - 1; j++) {
            for (i = n; i < G.length - 1; i++) {
                G[i][j] = G[i + 1][j];
            }
            G[i][j] = 0;
        }

        for (i = 0; i <= n - 1; i++) {
            for (j = n; j < G.length - 1; j++) {
                G[i][j] = G[i][j + 1];
            }
            G[i][j] = 0;
        }

        for (i = n; i < G.length - 1; i++) {
            for (j = n; j < G.length - 1; j++) {
                G[i][j] = G[i + 1][j + 1];
            }
        }

        for (i = n; i < G.length - 1; i++)
            G[i][G.length - 1] = 0;
        for (j = n; j < G.length - 1; j++)
            G[G.length - 1][j] = 0;
        G[G.length - 1][G.length - 1] = 0;

        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                System.out.printf("%3d ", G[i][j]);
            }
            System.out.printf("\n");
        }
    }

    // insere uma aresta no grafo
    static void insereAresta(int[][] G, int vi, int vf) {
        G[vi - 1][vf - 1] = 1;
        G[vf - 1][vi - 1] = 1;
    }

    // remove uma aresta no grafo
    static void removeAresta(int[][] G, int vi, int vf) {
        G[vi - 1][vf - 1] = 0;
        G[vf - 1][vi - 1] = 0;
    }

    // verificar se dois vértices adjacentes
    static boolean arestaExiste(int[][] G, int vi, int vf) {
        if (G[vi - 1][vf - 1] != 0)
            return (true);
        else
            return (false);
    }

    // exibe o grau de todos os vértices
    static void exibeGraus(int[][] G) {
        int i, j, cont;

        // percorre os vértices
        for (i = 1; i <= G.length; i++) {
            cont = 0;
            // percorre as arestas de cada vértice
            for (j = 1; j <= G.length; j++) {
                if (arestaExiste(G, i, j))
                    cont++;
            }
            if (cont != 0)
                System.out.printf("O grau do vértice %d é: %d\n", i, cont);
        }
    }

    // exibe o grafo
    static void exibeGrafo(int[][] G, int n) {
        int i, j;

        // percorre os vértices
        for (i = 1; i <= n; i++) {
            System.out.printf("Vértices adjacentes a %d: ", i);
            // percorre as arestas de cada vértice
            for (j = 1; j <= n; j++) {
                if (arestaExiste(G, i, j))
                    System.out.printf("%d (peso: %d), ", j, G[i - 1][j - 1]);
            }
            System.out.printf("\n");
        }
    }

    // Verifica se o grafo é Euleriano
    static void verificaEuleriano(int[][] g, int vertices) {
        int cont = 0;

        for (int i = 0; i < vertices; i++) {
            if (cont % 2 == 0) {
                for (int j = 0; j < vertices; j++) {
                    if (g[i][j] != 0)
                        cont++;
                }
            }
            break;
        }

        if (cont % 2 == 0)
            System.out.printf("Esse grafo é Euleriano!\n");
        else
            System.out.printf("Esse grafo não é Euleriano!\n");
    }

    // Verifica se o grafo é completo
    static void verificaCompleto(int[][] g, int vertices) {
        int existe = 0;

        for (int i = 0; i < vertices; i++) {
            for (int j = 1; j < vertices; j++) {
                if (g[i][j] != 0)
                    existe = 1;
                else {
                    existe = 0;
                }
            }
            break;
        }

        if (existe == 1)
            System.out.printf("Esse grafo é completo!\n");
        else
            System.out.printf("Esse grafo não é completo!\n");
    }

    // Verifica se o grafo é completo
    static void verificaTDesconexo(int[][] g, int vertices) {
        int existe = 0;

        for (int i = 0; i < vertices; i++) {
            for (int j = 1; j < vertices; j++) {
                if (g[i][j] != 0) {
                    existe = 1;
                    break;
                } else {
                    existe = 0;
                }
            }
            break;
        }

        if (existe == 1)
            System.out.printf("Esse grafo não é totalmente desconexo!\n");
        else
            System.out.printf("Esse grafo é totalmente desconexo!\n");
    }

    // Mostrar o grafo complementar
    static void mostrarComplementar(int[][] g, int vertices) {
        Random random = new Random();

        System.out.printf("--- Grafo ---\n");
        // percorre os vértices
        for (int i = 1; i <= vertices; i++) {
            System.out.printf("Vértices adjacentes a %d: ", i);
            // percorre as arestas de cada vértice
            for (int j = 1; j <= vertices; j++) {
                if (arestaExiste(g, i, j))
                    System.out.printf("%d (peso: %d), ", j, g[i - 1][j - 1]);
            }
            System.out.printf("\n");
        }

        System.out.printf("\n--- Grafo Complementar ---\n");
        // percorre os vértices
        for (int i = 1; i <= vertices; i++) {
            System.out.printf("Vértices adjacentes a %d: ", i);
            // percorre as arestas de cada vértice
            for (int j = 1; j <= vertices; j++) {
                if (i == j)
                    System.out.printf("");
                else if (!arestaExiste(g, i, j))
                    System.out.printf("%d (peso: %d), ", j, g[i - 1][j - 1]);
            }
            System.out.printf("\n");
        }
    }

    // Carrega um novo grafo
    static void novoGrafo(int[][] g, String[] nomes) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        File arq = new File(nomeArquivo);

        int numVertices, numArestas, i;
        String aresta;
        String[] tokens;

        BufferedReader bufLeitura = new BufferedReader(new FileReader(nomeArquivo));

        numVertices = Integer.parseInt(bufLeitura.readLine());
        for (i = 0; i < numVertices; i++)
            nomes[i] = bufLeitura.readLine();

        numArestas = Integer.parseInt(bufLeitura.readLine());
        for (i = 0; i < numArestas; i++) {
            aresta = bufLeitura.readLine();

            tokens = aresta.split(" ");

            g[Integer.parseInt(tokens[0]) - 1][Integer.parseInt(tokens[1]) - 1] = Integer.parseInt(tokens[2]);
            g[Integer.parseInt(tokens[1]) - 1][Integer.parseInt(tokens[0]) - 1] = Integer.parseInt(tokens[2]);
        }

        bufLeitura.close();
    }

    // Altera o peso de uma aresta
    static void alteracaoPeso(int[][] g) {
        int vi, vf, novoPeso;

        System.out.printf("Vertíce Inicial: ");
        vi = Integer.parseInt(entrada.nextLine());

        System.out.printf("Vertíce Final: ");
        vf = Integer.parseInt(entrada.nextLine());

        System.out.printf("Novo Peso: ");
        novoPeso = Integer.parseInt(entrada.nextLine());

        if (!arestaExiste(g, vi, vf)) {
            System.out.printf("\nAresta não existe, tente outra vez!\n");
            alteracaoPeso(g);
        } else {
            g[vi - 1][vf - 1] = novoPeso;
            g[vf - 1][vi - 1] = novoPeso;
        }
    }

    public static void main(String[] args) throws IOException {
        int op, // opção escolhida no menu
                numVertices = 0, // número de vértices no grafo
                vi, vf; // vértices inicial e final de uma aresta
            /*i, j, // variáveis de controle
            cont; // contador do número de aresta de cada vértice*/
        int[][] G = new int[10][10]; // matriz de adjacências que armazena o grafo // int[,] G;
        String[] vertices = new String[10];

        numVertices = carregaGrafo(G, vertices);

        do {
            op = menu();

            switch (op) {
                case 1: // inserir vértice
                    numVertices++;
                    break;

                case 2: // remover vértice
                    numVertices--;
                    break;

                case 3: // inserir aresta
                    System.out.print("Vértice inicial: ");
                    vi = Integer.parseInt(entrada.nextLine());
                    System.out.print("Vértice final: ");
                    vf = Integer.parseInt(entrada.nextLine());
                    if (arestaExiste(G, vi, vf))
                        System.out.printf("Os vértices %d e %d já são adjacentes.\n", vi, vf);
                    else
                        insereAresta(G, vi, vf);
                    break;

                case 4: // remover aresta
                    System.out.print("Vértice inicial: ");
                    vi = Integer.parseInt(entrada.nextLine());
                    System.out.print("Vértice final: ");
                    vf = Integer.parseInt(entrada.nextLine());
                    if (!arestaExiste(G, vi, vf))
                        System.out.printf("Os vértices %d e %d não são adjacentes.", vi, vf);
                    else
                        removeAresta(G, vi, vf);
                    break;

                case 5: // verificar adjacências
                    System.out.print("Vértice inicial: ");
                    vi = Integer.parseInt(entrada.nextLine());
                    System.out.print("Vértice final: ");
                    vf = Integer.parseInt(entrada.nextLine());
                    if (arestaExiste(G, vi, vf))
                        System.out.printf("Os vértices %d e %d são adjacentes.", vi, vf);
                    else
                        System.out.printf("Os vértices %d e %d não são adjacentes.", vi, vf);
                    break;

                case 6: // graus dos vértices
                    exibeGraus(G);
                    break;

                case 7: // exibir grafo
                    exibeGrafo(G, numVertices);
                    break;

                case 8: // verificar grafo euleriano
                    verificaEuleriano(G, numVertices);
                    break;

                case 9: // verificar grafo completo
                    verificaCompleto(G, numVertices);
                    break;

                case 10: // verificar grafo totalmente desconexo
                    verificaTDesconexo(G, numVertices);
                    break;

                case 11: // grafo complementar
                    mostrarComplementar(G, numVertices);
                    break;

                case 12: // carregar grafo
//                    novoGrafo(G, vertices);
                    carregaGrafo(G, vertices);
                    break;

                case 13: //a alterar peso aresta
                    alteracaoPeso(G);
                    break;

                case 14: // salvar grafo
                    salvaGrafo(G, vertices, numVertices);
                    break;
            }
        } while (op != 15);
    }


}
