package agendacontatos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bebel
 */
public class Agenda {

    private List<Contato> contatos = new ArrayList<>();

    public void adicionarContato(Contato contato, String NOME_ARQUIVO) {
        // Simplesmente adiciona o contato a lista Contatos
        contatos.add(contato);
        
        salvarContatos(NOME_ARQUIVO);
    }

    public void removerContato(String nome) {
        // Remove o contato da Lista com base no nome do contato
        contatos.removeIf(contato -> contato.getNome().equalsIgnoreCase(nome)); // O equalsIgnoreCase serve comparar ignorando as diferenças de Maiusculas e Minusculas
    }
    
    public void alterarContato(Contato contato, String nome, String telefone, String email, String NOME_ARQUIVO) {
    // Atualiza os dados do contato se os novos valores não forem nulos ou vazios
        if (nome != null && !nome.isEmpty()) {
            contato.setNome(nome);
        }
        if (telefone != null && !telefone.isEmpty()) {
            contato.setTelefone(telefone);
        }
        if (email != null && !email.isEmpty()) {
            contato.setEmail(email);
        }
        
        salvarContatos(NOME_ARQUIVO);
    }

    public List<Contato> listarContatos() {
        // Lista todos os contatos que tem salvo na lista
        return new ArrayList<>(contatos);
    }

    public Contato buscarContato(String nome) {
        // Busca os contatos com base no nome fornecido pelo usuario, utilizando da função equalsIgnoreCase tambem
        for (Contato contato : contatos) {
            if (contato.getNome().equalsIgnoreCase(nome)) {
                return contato;
            }
        }
        return null;
    }
    
    
    // MÉTODOS PARA SALVAR OS DADOS NO BLOCO DE NOTAS
        // Esse método salva os contatos no bloco de notas com o '\n' os separando
    public void salvarContatos(String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            for (Contato contato : contatos) {
                writer.println(contato.getNome());       // Nome do contato
                writer.println(contato.getTelefone());  // Telefone do contato
                writer.println(contato.getEmail());     // Email do contato
                writer.println();               }
        } catch (IOException e) {
            System.out.println("\n*************Erro ao salvar contatos: " + e.getMessage());
        }
    }

    public void carregarContatos(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);

        // Verifica se o arquivo já existe
        if (!arquivo.exists()) {
            try {
                // Cria o arquivo se ele não existir
                arquivo.createNewFile();
            } catch (IOException e) {
                System.out.println("\n*************Erro ao criar arquivo de contatos: " + e.getMessage());
                return;
            }
        }
        
        // Caso o arquivo exista, lê os contatos
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Lê o nome, telefone e email do contato (3 linhas por contato)
                String nome = linha;
                String telefone = reader.readLine();
                String email = reader.readLine();

                // Verifica se os 3 campos estão presentes
                if (nome != null && telefone != null && email != null) {
                    Contato contato = new Contato(nome, telefone, email);
                    contatos.add(contato);
                }

                // Lê a linha em branco entre os contatos (não é necessário armazená-la)
                reader.readLine(); // Esse readLine() pula a linha em branco
        }
        } catch (IOException e) {
            System.out.println("\n*************Erro ao carregar contatos: " + e.getMessage());
        }
    }

    List<Contato> getContatos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}