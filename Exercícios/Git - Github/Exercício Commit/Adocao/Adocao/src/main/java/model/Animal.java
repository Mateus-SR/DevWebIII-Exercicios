/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Animal {
    private String nome;
    private String especie;
    private int idade;
    private String status;

    public Animal(String nome, String especie, int idade, String status) {
        this.nome = nome;
        this.especie = especie;
        this.idade = idade;
        this.status = status;
    }

    public String getNome() { return nome; }
    public String getEspecie() { return especie; }
    public int getIdade() { return idade; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}

