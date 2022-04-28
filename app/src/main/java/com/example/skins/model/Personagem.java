package com.example.skins.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Personagem implements Serializable {
    // criando as variaveis  dos dados do personagem
    private String nome;
    private String nascimento;
    private String altura;
    private int id = 0;
    // Método que irá areceber variáveis do mesmo tipo  já criadas para poder salva-las na classe
    public Personagem(String nome, String nascimento, String altura){
        this.nome = nome;
        this.nascimento = nascimento;
        this.altura = altura;
    }
    public Personagem(){

    }
    // Usando métodos que irá setar as variáveis que possam ser acessadas em outro código
    public void setNome(String nome) {this.nome = nome;}

    public void setNascimento(String nascimento) {this.nascimento = nascimento;}

    public  void setAltura(String altura) {this.altura = altura;}

    public String getNome () {return nome;}

    public String getNascimento() {return nascimento;}

    public String getAltura() {return altura;}

    @NonNull
    @Override
    public String toString() {return nome;}

    public void setId(int id) {this.id = id;}

    public  int getId() {return  id;}

    public boolean idValido(){return id > 0;}
}
