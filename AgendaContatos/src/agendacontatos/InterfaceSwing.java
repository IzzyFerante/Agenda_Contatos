package agendacontatos;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bebel
 */
public class InterfaceSwing extends JFrame{
    
    private String NOME_ARQUIVO = "contatos.txt";
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nomeField, telefoneField, emailField, buscaField;
    private JButton adicionarButton, alterarButton, removerButton, buscarButton;
    private Agenda agenda;

    public InterfaceSwing() {
        agenda = new Agenda();
        agenda.carregarContatos("contatos.txt");  // Carregar os contatos do arquivo

        // Configura a janela
        setTitle("Agenda de Contatos");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Configura a tabela
        tableModel = new DefaultTableModel() {
            // Sobrescreve o método isCellEditable para tornar todas as células não editáveis
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Desabilita a edição de qualquer célula
            }
        };
        tableModel.addColumn("Nome");
        tableModel.addColumn("Telefone");
        tableModel.addColumn("E-mail");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Cria um painel de entrada de dados e botões
        JPanel inputPanel = new JPanel(new GridLayout(1, 2));

        // Painel para os botões
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,15, 25));  
        adicionarButton = new JButton("Adicionar");
        alterarButton = new JButton("Alterar");
        removerButton = new JButton("Deletar");
        actionPanel.add(adicionarButton);
        actionPanel.add(alterarButton);
        actionPanel.add(removerButton);
        
        inputPanel.add(actionPanel);

        // Painel para buscar contatos
        JPanel buscaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 25));
        buscaField = new JTextField(15);
        buscaPanel.add(new JLabel("Buscar por Nome:"));
        buscaPanel.add(buscaField);
        buscarButton = new JButton("Buscar");
        buscaPanel.add(buscarButton);

        inputPanel.add(buscaPanel);

        add(inputPanel, BorderLayout.NORTH);

        // Ações para os botões
        adicionarButton.addActionListener((ActionEvent e) -> {
            abrirJanelaAdicionar();
        });

        alterarButton.addActionListener((ActionEvent e) -> {
            abrirJanelaAlterar();
        });

        removerButton.addActionListener((ActionEvent e) -> {
            removerContato();
        });

        buscarButton.addActionListener((ActionEvent e) -> {
            buscarContato();
        });

        // Preencher a tabela com os contatos
        atualizarTabela();
    }

    
    // Método para abrir a janela de adicionar
    private void abrirJanelaAdicionar() {
        // Janela para adicionar um novo contato
        JFrame adicionarFrame = new JFrame("Adicionar Contato");
        adicionarFrame.setSize(400, 300);
        adicionarFrame.setLayout(new GridLayout(4, 2));
        adicionarFrame.setLocationRelativeTo(null);

        nomeField = new JTextField();
        telefoneField = new JTextField();
        emailField = new JTextField();

        adicionarFrame.add(new JLabel("Nome:"));
        adicionarFrame.add(nomeField);
        adicionarFrame.add(new JLabel("Telefone:"));
        adicionarFrame.add(telefoneField);
        adicionarFrame.add(new JLabel("Email:"));
        adicionarFrame.add(emailField);

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener((ActionEvent e) -> {
            adicionarContato();
            adicionarFrame.dispose();
        });

        adicionarFrame.add(salvarButton);
        adicionarFrame.setVisible(true);
    }

    // Método para adicionar um novo contato
    private void adicionarContato() {
        // Pega os dados informados nos labels
        String nome = nomeField.getText();
        String telefone = telefoneField.getText();
        String email = emailField.getText();

        // Verifica se todos os dados foram informados
        if (!nome.isEmpty() && !telefone.isEmpty() && !email.isEmpty()) {
            // Salva contato e atualiza a tabela
            Contato contato = new Contato(nome, telefone, email);
            agenda.adicionarContato(contato, NOME_ARQUIVO);
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Contato adicionado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!");
        }
    }

    
    // Método para abrir a janela de alteração
    private void abrirJanelaAlterar() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Pega o contato selecionado na tabela
            String nomeOriginal = tableModel.getValueAt(selectedRow, 0).toString();
            Contato contato = agenda.buscarContato(nomeOriginal);

            // Configura a janela de alteração
            JFrame alterarFrame = new JFrame("Alterar Contato");
            alterarFrame.setSize(400, 300);
            alterarFrame.setLayout(new GridLayout(4, 2));
            alterarFrame.setLocationRelativeTo(null);

            nomeField = new JTextField(contato.getNome());
            telefoneField = new JTextField(contato.getTelefone());
            emailField = new JTextField(contato.getEmail());

            alterarFrame.add(new JLabel("Nome:"));
            alterarFrame.add(nomeField);
            alterarFrame.add(new JLabel("Telefone:"));
            alterarFrame.add(telefoneField);
            alterarFrame.add(new JLabel("Email:"));
            alterarFrame.add(emailField);

            JButton salvarButton = new JButton("Salvar Alterações");
            salvarButton.addActionListener((ActionEvent e) -> {
                alterarContato(contato);
                alterarFrame.dispose();
            });

            alterarFrame.add(salvarButton);
            alterarFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um contato para alterar!");
        }
    }

    // Método para alterar os dados de um contato
    private void alterarContato(Contato contato) {
        // Pega os dados informados nos labels
        String nome = nomeField.getText();
        String telefone = telefoneField.getText();
        String email = emailField.getText();

        // Salva as alterações e atualiza a tabela
        agenda.alterarContato(contato, nome, telefone, email, "contatos.txt");
        atualizarTabela();
        JOptionPane.showMessageDialog(this, "Contato alterado com sucesso!");
    }

    
    // Método para remover um contato
    private void removerContato() {
        // Pega o contato selecionado na tabela
        int selectedRow = table.getSelectedRow();
        // Verifica se o usuario selecionou algum contato na tabela
        if (selectedRow != -1) {
            // Pega o nome do contato e o exclui e atualiza a tabela
            String nome = tableModel.getValueAt(selectedRow, 0).toString();
            agenda.removerContato(nome);
            agenda.salvarContatos("contatos.txt");
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Contato removido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um contato para remover!");
        }
    }

    
    // Método para buscar um contato pelo nome
    private void buscarContato() {
        // Pega o nome informado no label de pesquisa
        String nomeBusca = buscaField.getText();
        // Garante que o usuario informou um nome 
        if (!nomeBusca.isEmpty()) {
            Contato contatoEncontrado = agenda.buscarContato(nomeBusca);
            if (contatoEncontrado != null) {
                // Caso o contato exista ele atualiza a tabela com os contatos encontrados
                List<Contato> contatosEncontrados = new ArrayList<>();
                contatosEncontrados.add(contatoEncontrado);
                atualizarTabela(contatosEncontrados);
            } else {
                JOptionPane.showMessageDialog(this, "Contato não encontrado!");
            }
        } else {
            atualizarTabela();  // Se o campo de busca estiver vazio, mostra todos os contatos
        }
    }

    
    // Método para atualizar a tabela com todos os contatos
    private void atualizarTabela() {
        List<Contato> contatos = agenda.listarContatos();
        atualizarTabela(contatos);
    }

    // Método para atualizar a tabela com a lista de contatos
    private void atualizarTabela(List<Contato> contatos) {
        tableModel.setRowCount(0);  // Limpar a tabela
        for (Contato contato : contatos) {
            tableModel.addRow(new Object[]{contato.getNome(), contato.getTelefone(), contato.getEmail()});
        }
    }
}