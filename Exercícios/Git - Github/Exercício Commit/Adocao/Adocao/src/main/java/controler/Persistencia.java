/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.*;
import java.util.ArrayList;
import model.Animal;
import model.Usuario;

public class Persistencia {
    private static final String ARQUIVO_USUARIOS = "usuarios.txt";
    private static final String ARQUIVO_ANIMAIS = "animais.txt";

    public static ArrayList<Usuario> carregarUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    lista.add(new Usuario(dados[0], dados[1], dados[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
        return lista;
    }

    public static void salvarUsuarios(ArrayList<Usuario> usuarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_USUARIOS))) {
            for (Usuario u : usuarios) {
                bw.write(u.getNome() + ";" + u.getLogin() + ";" + u.getSenha());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    public static ArrayList<Animal> carregarAnimais() {
        ArrayList<Animal> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_ANIMAIS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 4) {
                    lista.add(new Animal(dados[0], dados[1], Integer.parseInt(dados[2]), dados[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar animais: " + e.getMessage());
        }
        return lista;
    }

    public static void salvarAnimais(ArrayList<Animal> animais) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_ANIMAIS))) {
            for (Animal a : animais) {
                bw.write(a.getNome() + ";" + a.getEspecie() + ";" + a.getIdade() + ";" + a.getStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar animais: " + e.getMessage());
        }
    }
    
    public static void removerAnimais(int index) {
        ArrayList<Animal> animais = carregarAnimais();
        
        if (index >= 0 && index < animais.size()) {
            animais.remove(index);
        }
        
        salvarAnimais(animais);
    }
}
