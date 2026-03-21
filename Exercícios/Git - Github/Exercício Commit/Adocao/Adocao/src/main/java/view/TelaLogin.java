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

public class TelaLogin extends JFrame {
    private JTextField campoLogin;
    private JPasswordField campoSenha;
    private JButton botaoEntrar, botaoCadastrar;

    public TelaLogin() {
        setTitle("Login - Amigos dos Animais");
        
        // tamanhos da janela
        int largura = 600;
        int altura = 440;
        setSize(largura, altura);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        
        URL dogIMG = getClass().getResource("/view/images/pet_dog_woman.png"); // Busca a imagem
        // E "cria" ela no codigo
        ImageIcon icone = new ImageIcon(dogIMG);
        JLabel labelImagem = new JLabel(icone);
        labelImagem.setBounds(0, 0, icone.getIconWidth(), icone.getIconHeight());
        add(labelImagem);
        
        int offsetXIMG = (int) (icone.getIconWidth() * 0.75);
        
        
        JLabel labelTextoLogin = new JLabel("Bem-vindo!");
        labelTextoLogin.setBounds(100 + offsetXIMG, 80, 80, 25);
        add(labelTextoLogin);
        
        JLabel labelLogin = new JLabel("Login:");
        labelLogin.setBounds(35 + offsetXIMG, 110, 80, 25);
        add(labelLogin);

        campoLogin = new JTextField();
        campoLogin.setBounds(100 + offsetXIMG, 110, 200, 25);
        add(campoLogin);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(35 + offsetXIMG, 150, 80, 25);
        add(labelSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(100 + offsetXIMG, 150, 200, 25);
        add(campoSenha);

        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBounds(100 + offsetXIMG, 190, 80, 30);
        add(botaoEntrar);

        botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBounds(190 + offsetXIMG, 190, 110, 30);
        add(botaoCadastrar);

        botaoEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String login = campoLogin.getText();
                String senha = new String(campoSenha.getPassword());
                ArrayList<Usuario> usuarios = Persistencia.carregarUsuarios();

                boolean encontrado = false;
                for (Usuario u : usuarios) {
                    if (u.getLogin().equals(login) && u.getSenha().equals(senha)) {
                        encontrado = true;
                        JOptionPane.showMessageDialog(null, "Bem-vindo " + u.getNome() + "!");
                        new TelaListagemAnimais().setVisible(true);
                        dispose();
                        break;
                    }
                }
                if (!encontrado) {
                    JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos!");
                }
            }
        });

        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaCadastro().setVisible(true);
                dispose();
            }
        });
    }
}
