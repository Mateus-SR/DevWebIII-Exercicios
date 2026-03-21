/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import model.Animal;
import controller.Persistencia;

public class TelaListagemAnimais extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton botaoAdicionar;
    private JButton botaoRemover;
    private JButton botaoAtualizar;
    private JButton botaoSair;

    public TelaListagemAnimais() {
        setTitle("Lista de Animais");
        
        // tamanhos da janela
        int largura = 600;
        int altura = 440;
        setSize(largura, altura);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        modelo = new DefaultTableModel(new Object[]{"Nome", "Espécie", "Idade (em anos)", "Status"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        
        tabela.setAutoCreateRowSorter(true);
        
        int tabelaLargura = (int) (largura * 0.90);
        int tabelaAltura = (int) (altura * 0.70);
        scroll.setBounds(((largura - tabelaLargura) / 2) -8, 20, tabelaLargura, tabelaAltura);
        
        add(scroll);

        int botaoLargura = 120;
        int botaoEspacamento = 20;
        botaoAdicionar = new JButton("Adicionar");
        botaoAdicionar.setBounds(botaoEspacamento, (int) (altura * 0.80), botaoLargura, 30);
        add(botaoAdicionar);
        
        botaoRemover = new JButton("Remover");
        botaoRemover.setBounds(botaoEspacamento * 2 + botaoLargura, (int) (altura * 0.80), botaoLargura, 30);
        add(botaoRemover);
        
        botaoAtualizar = new JButton("Alternar Status");
        botaoAtualizar.setBounds(botaoEspacamento * 9 + botaoLargura, (int) (altura * 0.80), botaoLargura, 30);
        add(botaoAtualizar);
        
        botaoSair = new JButton("Sair");
        botaoSair.setBounds(botaoEspacamento * 16 + botaoLargura, (int) (altura * 0.80), botaoLargura, 30);
        add(botaoSair);

        carregarTabela();

        botaoAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int linha = tabela.getSelectedRow();
                if (tabela.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Não há animais na tabela!");
                } else if (linha >= 0) {
                    ArrayList<Animal> animais = Persistencia.carregarAnimais();
                    Animal animal = animais.get(linha);
                    animal.setStatus(animal.getStatus().equals("Disponível") ? "Adotado" : "Disponível");
                    Persistencia.salvarAnimais(animais);
                    carregarTabela();
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um animal!");
                }
            }
        });
        
        botaoAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaCadastroAnimal().setVisible(true);
                dispose();
            }
        });
        
        botaoRemover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int linha = tabela.getSelectedRow();
                
                if (linha >= 0) {
                    int resposta = JOptionPane.showOptionDialog(new JFrame(), "Você tem certeza?\nEssa ação não pode ser desfeita.", "Remover um animal", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[] { "Yes", "No" }, null);
                    
                    if (resposta == JOptionPane.YES_OPTION) {
                        Persistencia.removerAnimais(linha);
                        carregarTabela();
                    }
                } else {
                        JOptionPane.showMessageDialog(null, "Selecione um animal!");
                    }
            }
        });    
        
        botaoSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaLogin().setVisible(true);
            }
        });
    }

    
    
    private void carregarTabela() {
        modelo.setRowCount(0);
        ArrayList<Animal> animais = Persistencia.carregarAnimais();
        for (Animal a : animais) {
            modelo.addRow(new Object[]{a.getNome(), a.getEspecie(), a.getIdade(), a.getStatus()});
        }
    }
}

