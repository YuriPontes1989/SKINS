package com.example.skins.dao;

import com.example.skins.model.Personagem;

import java.util.ArrayList;
import java.util.List;

public class PersonagemDAO {
    // Criando as variaveis
    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorDeIds = 1;
    
    // Usando metodo para salvar na classe do Personagem compartilhando o ip e todos os dados dos personagens
    public  void salvar(Personagem persoangemSalvo) {
        persoangemSalvo.setId(contadorDeIds);
        personagens.add(persoangemSalvo);
        atualizaId();
    }
    // adicionando mais um ao contador 
    private void atualizaId() {contadorDeIds++; }
    //usando o metodo para editar um personagem que ja foi criado  na classe personagem
    public void edita(Personagem personagem) {
        Personagem personagemEncontrado = buscaPersonagemId(personagem);
        if (personagemEncontrado != null) {
            int posicaoDoPersonagem = personagens.indexOf(personagemEncontrado);
            personagens.set(posicaoDoPersonagem, personagem);
        }
    }
    // irá buscar o personagem pelo id
    private Personagem buscaPersonagemId(Personagem personagem) {
        for (Personagem p :
                personagens) {
            if (p.getId() == personagem.getId()) {
                return p;
            }
        }
        return  null;
    }
    // cria uma nova lista da classe de personagem e retorna a array list
    public List<Personagem> todos() {return  new ArrayList<>(personagens);}
    // usando metodo para remover um personagem da classe personagem
    public void remove(Personagem personagem) {
        Personagem personagemDevolvido = buscaPersonagemId((personagem));
        if (personagemDevolvido != null) {
            personagens.remove(personagemDevolvido);
        }
    }
}
