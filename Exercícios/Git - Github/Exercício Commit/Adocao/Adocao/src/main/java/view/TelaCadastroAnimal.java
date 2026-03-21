/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import model.Animal;
import controller.Persistencia;
import java.net.URL; // Import para conseguir pegar a URL das imagens

public class TelaCadastroAnimal extends JFrame {
    private JTextField campoNome;
    private JSpinner campoIdade;
    private JButton botaoCadastrar, botaoVoltar;
    String[] status = { "Adotado", "Disponível" };
    JComboBox<String> campoStatus = new JComboBox<>(status);
    
    String[] especie = { "Cachorro", "Gato", "Outro" };
    JComboBox<String> campoEspecie = new JComboBox<>(especie);
    
    public TelaCadastroAnimal() {
        setTitle("Cadastro de Animal");
        
        // tamanhos da janela
        int largura = 600;
        int altura = 440;
        setSize(largura, altura);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        URL catdogIMG = getClass().getResource("/view/images/pet_catdog.png"); // Busca a imagem
        // E "cria" ela no codigo
        ImageIcon icone = new ImageIcon(catdogIMG);
        JLabel labelImagem = new JLabel(icone);
        labelImagem.setBounds(-15, 0, icone.getIconWidth(), icone.getIconHeight());
        add(labelImagem);
        
        int offsetXIMG = (int) (icone.getIconWidth() * 0.75);
        
                JLabel labelTextoCadastro = new JLabel("Preencha com os dados do pet:");
        labelTextoCadastro.setBounds(100 + offsetXIMG, 80, 200, 25);
        add(labelTextoCadastro);
        
        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(35 + offsetXIMG, 110, 80, 25);
        add(labelNome);

        campoNome = new JTextField();
        campoNome.setBounds(100 + offsetXIMG, 110, 200, 25);
        add(campoNome);

        JLabel labelEspecie = new JLabel("Espécie:");
        labelEspecie.setBounds(35 + offsetXIMG, 150, 80, 25);
        add(labelEspecie);

        campoEspecie.setBounds(100 + offsetXIMG, 150, 200, 25);
        campoEspecie.setSelectedIndex(2);
        add(campoEspecie);

        JLabel labelIdade = new JLabel("Idade:");
        labelIdade.setBounds(35 + offsetXIMG, 190, 80, 25);
        add(labelIdade);

                SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 20, 1);
        campoIdade = new JSpinner();
        campoIdade.setBounds(100 + offsetXIMG, 190, 200, 25);
        add(campoIdade);

        JLabel labelStatus = new JLabel("Status:");
        labelStatus.setBounds(35 + offsetXIMG, 230, 80, 25);
        add(labelStatus);

        campoStatus.setBounds(100 + offsetXIMG, 230, 200, 25);
        campoStatus.setSelectedIndex(1);
        add(campoStatus);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(100 + offsetXIMG, 270, 80, 30);
        add(botaoVoltar);
        
        botaoCadastrar = new JButton("Salvar");
        botaoCadastrar.setBounds(190 + offsetXIMG, 270, 110, 30);
        add(botaoCadastrar);
        
        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaListagemAnimais().setVisible(true);
            
            }
        });
        
        
        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Animal> animais = Persistencia.carregarAnimais();
                
                String nome = campoNome.getText().trim();
                String especie = (String) campoEspecie.getSelectedItem();
                int idade = (int) campoIdade.getValue();
                String status = (String) campoStatus.getSelectedItem();
                
                if(nome.equals("")){
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
                } else {
                animais.add(new Animal(nome, especie, idade, status));
                Persistencia.salvarAnimais(animais);
                JOptionPane.showMessageDialog(null, "Animal cadastrado com sucesso!");
                new TelaListagemAnimais().setVisible(true);
                dispose();
                }
            }
        });
    }
}

