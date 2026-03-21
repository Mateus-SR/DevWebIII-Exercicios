/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import model.Usuario;
import controller.Persistencia;
import java.net.URL; // Import para conseguir pegar a URL das imagens

public class TelaCadastro extends JFrame {
    private JTextField campoNome, campoLogin;
    private JPasswordField campoSenha;
    private JButton botaoCadastrar, botaoVoltar;

    public TelaCadastro() {
        setTitle("Cadastro de Usuário");
        
        // tamanhos da janela
        int largura = 600;
        int altura = 440;
        setSize(largura, altura);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

                URL catIMG = getClass().getResource("/view/images/pet_cat_man.png"); // Busca a imagem
        // E "cria" ela no codigo
        ImageIcon icone = new ImageIcon(catIMG);
        JLabel labelImagem = new JLabel(icone);
        labelImagem.setBounds(-15, 0, icone.getIconWidth(), icone.getIconHeight());
        add(labelImagem);
        
        int offsetXIMG = (int) (icone.getIconWidth() * 0.75);
        
        JLabel labelTextoCadastro = new JLabel("Preencha com seus dados:");
        labelTextoCadastro.setBounds(100 + offsetXIMG, 80, 200, 25);
        add(labelTextoCadastro);
        
        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(35 + offsetXIMG, 110, 80, 25);
        add(labelNome);

        campoNome = new JTextField();
        campoNome.setBounds(100 + offsetXIMG, 110, 200, 25);
        add(campoNome);

        JLabel labelLogin = new JLabel("Login:");
        labelLogin.setBounds(35 + offsetXIMG, 150, 80, 25);
        add(labelLogin);

        campoLogin = new JTextField();
        campoLogin.setBounds(100 + offsetXIMG, 150, 200, 25);
        add(campoLogin);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(35 + offsetXIMG, 190, 80, 25);
        add(labelSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(100 + offsetXIMG, 190, 200, 25);
        add(campoSenha);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(100 + offsetXIMG, 230, 80, 30);
        add(botaoVoltar);
        
        botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBounds(190 + offsetXIMG, 230, 110, 30);
        add(botaoCadastrar);
        


        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TelaLogin().setVisible(true);
            
            }
        });
        
        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Usuario> usuarios = Persistencia.carregarUsuarios();
                
                String novoNome = campoNome.getText().trim();
                String novoLogin = campoLogin.getText().trim();
                String novaSenha = new String(campoSenha.getPassword()).trim();
                
                if(novoNome.equals("") || novoLogin.equals("") || novaSenha.equals("")){
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
                } else {
                    boolean usuarioExistente = false;
                    
                    for (Usuario u : usuarios) {
                        if (u.getLogin().equals(novoLogin)) {
                            usuarioExistente = true;
                            break;
                        }
                    }
                    
                    if (usuarioExistente == false) {
                        usuarios.add(new Usuario(novoNome, novoLogin, novaSenha));
                        Persistencia.salvarUsuarios(usuarios);
                        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                        new TelaLogin().setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Esse usuário já existe!", "Opa!", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
    }
}

