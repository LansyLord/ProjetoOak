import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Janela extends JFrame {
    private DefaultTableModel tableModel;
    private JTable tabelaProdutos;
    private List<Produto> produtos = new ArrayList<>();

    public Janela() {
        setTitle("Gerenciador de Produtos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocation(540, 260);

        String[] colunas = {"Nome", "Valor"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaProdutos = new JTable(tableModel);

        JButton cadastrarBotao = new JButton("Cadastrar Produto");
        cadastrarBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostraDialogoCadastroProduto();
            }
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);
        add(cadastrarBotao, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void mostraDialogoCadastroProduto() {
        JDialog dialogo = new JDialog(this, "Cadastro de Produto", true);
        dialogo.setLayout(new GridBagLayout());
        dialogo.setLocation(540, 260);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nomeLabel = new JLabel("Nome do Produto");
        JTextField nomeCampo = new JTextField();
        nomeCampo.setPreferredSize(new Dimension(250, 25));
        adicionarComponente(dialogo, nomeLabel, gbc, 0, 0);
        adicionarComponente(dialogo, nomeCampo, gbc, 1, 0);

        JLabel descricaoLabel = new JLabel("Descrição do Produto");
        JTextField descricaoCampo = new JTextField();
        descricaoCampo.setPreferredSize(new Dimension(250, 25));
        adicionarComponente(dialogo, descricaoLabel, gbc, 0, 1);
        adicionarComponente(dialogo, descricaoCampo, gbc, 1, 1);

        JLabel valorLabel = new JLabel("Valor do Produto");
        JTextField valorCampo = new JTextField();
        valorCampo.setPreferredSize(new Dimension(250, 25));
        adicionarComponente(dialogo, valorLabel, gbc, 0, 2);
        adicionarComponente(dialogo, valorCampo, gbc, 1, 2);

        JLabel vendaLabel = new JLabel("Disponível para Venda");
        JComboBox<String> vendaComboBox = new JComboBox<>(new String[]{"Sim", "Não"});
        adicionarComponente(dialogo, vendaLabel, gbc, 0, 3);
        adicionarComponente(dialogo, vendaComboBox, gbc, 1, 3);

        JButton cadastrarBotao = new JButton("Cadastrar");

        cadastrarBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeCampo.getText();
                String descricao = descricaoCampo.getText();
                double valor = Double.parseDouble(valorCampo.getText());
                String venda = (String) vendaComboBox.getSelectedItem();
                boolean disponivelParaVenda = false;
                if (venda.equalsIgnoreCase("sim")) {
                    disponivelParaVenda = true;
                } else {
                    disponivelParaVenda = false;
                }

                Produto p1 = new Produto(nome, descricao, valor, disponivelParaVenda);
                produtos.add(p1);

                produtos.sort(Comparator.comparingDouble(Produto::getValor));

                tableModel.setRowCount(0);
                for (Produto p : produtos) {
                    tableModel.addRow(new Object[]{
                            p.getNome(),
                            String.format("R$ %.2f", p.getValor())
                    });
                }

                dialogo.dispose();
            }
        });

        adicionarComponente(dialogo, cadastrarBotao, gbc, 0, 4);

        dialogo.setSize(800, 600);
        dialogo.setVisible(true);
    }

    private void adicionarComponente(Container container, Component componente, GridBagConstraints gbc, int coluna, int linha) {
        gbc.gridx = coluna;
        gbc.gridy = linha;
        container.add(componente, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Janela();
        });
    }
}