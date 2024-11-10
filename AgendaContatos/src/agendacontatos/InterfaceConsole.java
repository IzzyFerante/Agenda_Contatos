package agendacontatos;

import java.util.Scanner;

/**
 *
 * @author bebel
 */
public class InterfaceConsole {

    private Agenda agenda;
    private Scanner scanner;
    private static final String NOME_ARQUIVO = "contatos.txt";

    // Construtor
    public InterfaceConsole() {
        agenda = new Agenda();
        scanner = new Scanner(System.in);   // Inicia um scanner para utilizarmos
        agenda.carregarContatos(NOME_ARQUIVO); // Carrega os contatos salvos no bloco de notas ao iniciar
    }

    public void iniciar() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\nAgenda de Contatos:");
            System.out.println("1. Adicionar Contato");
            System.out.println("2. Listar Contatos");
            System.out.println("3. Buscar Contato");
            System.out.println("4. Alterar Contato");
            System.out.println("5. Remover Contato");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1 ->
                    adicionarContato();
                case 2 ->
                    listarContatos();
                case 3 ->
                    buscarContato();
                case 4 ->
                    alterarContato();
                case 5 ->
                    removerContato();
                case 0 -> 
                    System.out.println("Fechando agenda...");
                default ->  // Opção padrão para quando a entrada for inválida
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }

    // Quando o usuario selecionar ADICIONAR CONTATO
    private void adicionarContato() {
        System.out.print("\nNome: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        // Salva os dados em um novo Contato
        Contato contato = new Contato(nome, telefone, email);
        agenda.adicionarContato(contato, NOME_ARQUIVO);
        System.out.println("\nContato adicionado com sucesso!");
    }

    // Quando o usuario selecionar LISTAR CONTATOS
    private void listarContatos() {
        System.out.println("\n-----------------------------------");
        System.out.println("CONTATOS:\n");
        for (Contato contato : agenda.listarContatos()) {
            System.out.println("Nome: " + contato.getNome());
            System.out.println("Telefone: " + contato.getTelefone());
            System.out.println("E-mail: " + contato.getEmail());
            System.out.println("-----------------------------------");
        }
    }

    // Quando o usuario selecionar BUSCAR CONTATO    
    private void buscarContato() {
        System.out.print("\nDigite o nome do contato que deseja buscar: ");
        String nome = scanner.nextLine();
        Contato contato = agenda.buscarContato(nome);
        if (contato != null) {
            System.out.println("\nCONTATO ENCONTRADO:");
            System.out.println("Nome: " + contato.getNome());
            System.out.println("Telefone: " + contato.getTelefone());
            System.out.println("E-mail: " + contato.getEmail());
        } else {
            System.out.println("\nContato não encontrado.");
        }
    }
    
    // Quando o usuario selecionar ALTERAR CONTATO
    private void alterarContato() {
        System.out.print("\nDigite o nome do contato que deseja alterar: ");
        String nome = scanner.nextLine();
        Contato contato = agenda.buscarContato(nome);

        if (contato != null) {
            System.out.println("\nCONTATO ENCONTRADO:");
            System.out.println("Nome salvo: " + contato.getNome());
            System.out.println("Telefone salvo: " + contato.getTelefone());
            System.out.println("E-mail salvo: " + contato.getEmail());
            System.out.println("-------------------------------\n");
            
            System.out.print("Novo nome (deixe em branco para manter o atual): ");
            String novoNome = scanner.nextLine();
            System.out.print("Novo telefone (deixe em branco para manter o atual): ");
            String novoTelefone = scanner.nextLine();
            System.out.print("Novo e-mail (deixe em branco para manter o atual): ");
            String novoEmail = scanner.nextLine();

            agenda.alterarContato(contato, novoNome, novoTelefone, novoEmail, NOME_ARQUIVO);
            
            System.out.println("\nContato atualizado com sucesso!");
        } else {
            System.out.println("\nContato não encontrado.");
        }
    }

    // Quando o usuario selecionar REMOVER CONTATO
    private void removerContato() {
        System.out.print("\nDigite o nome do contato para remover: ");
        String nome = scanner.nextLine();
        
        agenda.removerContato(nome);
        System.out.println("\nContato removido com sucesso!");
    }
}
