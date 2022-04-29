package com.example.skins.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.skins.R;
import com.example.skins.dao.PersonagemDAO;
import com.example.skins.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import  static com.example.skins.ui.activities.ConstatesActivities.CHAVE_PERSONAGEM;

public class ListaPersonagemActivity  extends AppCompatActivity {
        // Definindo o titulo de barra de aplicativo como uma constante e definindo os demais atributos privados
    public static final String TITULO_APPBAR = "Lista de Personagens";
    private final PersonagemDAO dao = new PersonagemDAO();
    private ArrayAdapter<Personagem> adapter;
    // sobrescrevendo o metodo oncreate,adicionado novos metodos ao metodo oncreate pai
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagem);
        configuraFabNovoPersonagem();
        configuraLista();
    }
    // definindo o metodo, que indica um botao para adicionar um novo personagem e adciona um clicklistener ao mesmo
    private void configuraFabNovoPersonagem() {
        FloatingActionButton botaoNovoPersoangem = findViewById(R.id.fab_add);
        botaoNovoPersoangem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    // define o metodo , que inicia a atividade de formulario atraves de uma intent
    private void abreFormulario() {
        startActivity(new Intent(this,FormularioPersonagemActivity.class));
    }
    // sobrescrevendo o metodo em questão,adicionando ao metodo on resume pai o metodo atulizapersonagem
    @Override
    protected void onResume() {
        super.onResume();
        atualizaPersonagem();
    }
    //define o metodo ataulizaPersonagem, que limpa o adapter e depois adiciona a eles todos os elemtentos da variavel dao, da classe do PersonagemDAO,uma constante
    private void atualizaPersonagem() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }
    // define o metodo remove, que remove o argumento da classe personagem da constate dao e do adaptador
    private void remove(Personagem personagem) {
        dao.remove(personagem);
        adapter.remove(personagem);
    }
    // Sobrepoe o método onCreateContextMenu da classe pai infla o menu.
    @Override
    public void onCreateContextMenu(ContextMenu menu,View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.add("Remover");
        getMenuInflater().inflate(R.menu.activity_lista_personagem_menu, menu);
    }
    // Sobrepoe o método onContextItemSelected da classe pai e mostra opções quando um item for selecionado, como deseja remover? e a escolha do usuário sim/não
    @Override
    public  boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.Activity_lista_personagem_menu_remover) {
            new AlertDialog.Builder(this)
                    .setTitle("Removendo Personagem")
                    .setMessage("Tem Certeza que quer remover?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            remove(personagemEscolhido);
                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        }
        return super.onContextItemSelected(item);
    }
        // usando o  método que configura a lista e o visual e suas funcionalidades.
    private void configuraLista() {
        ListView listaDePersonagens = findViewById(R.id.activity_main_lista_personagem);
        configuraAdapter(listaDePersonagens);
        configuraItemPorClique(listaDePersonagens);
        registerForContextMenu(listaDePersonagens);
    }
    // usando o método que possibilita clicar em um personagem e também editar ele
    private void configuraItemPorClique(ListView listaDePersonagens) {
        listaDePersonagens.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long l) {
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(posicao);
                abreFormularioEditar(personagemEscolhido);
            }
        }));
    }
    // método que ira abrir a edição do personagem.
    private void abreFormularioEditar(Personagem personagemEscolhido) {
        Intent vaiParaFormulario = new Intent(ListaPersonagemActivity.this,FormularioPersonagemActivity.class);
        vaiParaFormulario.putExtra(CHAVE_PERSONAGEM, personagemEscolhido);
        startActivity(vaiParaFormulario);
    }
    //Usando o método que configura a lista com os personagens
    private void configuraAdapter(ListView listaDePersoangem) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDePersoangem.setAdapter(adapter);
    }
}
