package com.example.skins.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.skins.R;
import com.example.skins.dao.PersonagemDAO;
import com.example.skins.model.Personagem;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class FormularioPersonagemActivity extends AppCompatActivity {
        private static final String TITULO_APPBAR_EDITAR_PERSONAGEM = "Editar o Personagem";
        private static final String TITULO_APPBAR_NOVO_PERSONAGEM = "Novo Personagem";
        private EditText campoNome;
        private EditText campoNascimento;
        private EditText campoAltura;
        private final PersonagemDAO dao = new PersonagemDAO();
        private Personagem personagem;

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.activity_formulario_personagem_menu_salvar){
                finalizarFormulario();
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_formulario_personagem);
            inicializacaoCampos();
            //configuraBotaoSalvar();
            carregaPersonagem();
        }

        private void carregaPersonagem(){
            Intent dados = getIntent();
            if(dados.hasExtra(CHAVE_PERSONAGEM)){
                setTitle(TITULO_APPBAR_EDITAR_PERSONAGEM);
                personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
                preencheCampos();
            }else {
                setTitle(TITULO_APPBAR_NOVO_PERSONAGEM);
                personagem = new Personagem();
            }
        }
        private void preencheCampos() {
            campoNome.setText(personagem.getNome());
            campoAltura.setText(personagem.getAltura());
            campoNascimento.setText(personagem.getNascimento());
        }

        private void finalizarFormulario() {
            preencherPersonagem();
            if(personagem.idValido())
            {
                dao.edita(personagem);
                finish();
            }else {
                dao.salvar(personagem);
            }
            finish();
        }
        private void inicializacaoCampos() {
            campoNome = findViewById(R.id.ediText_nome);
            campoNascimento = findViewById(R.id.editText_nascimento);
            campoAltura = findViewById(R.id.editText_altura);

            SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN");
            MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, amtAltura);
            campoAltura.addTextChangedListener(mtwAltura);

            SimpleMaskFormatter smfNascimento =new SimpleMaskFormatter("NN/NN/NNNN");
            MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoAltura, smtNascimento);
            campoNascimento.addTextChangedListener(mtwNascimento);
        }

        private void preencherPersonagem() {
            String nome = campoNome.getText().toString();
            String nascimento = campoNascimento.getText().toString();
            String altura = campoAltura.getText().toString();

            personagem.setNome(nome);
            personagem.setAltura(altura);
            personagem.setNascimento(nascimento);
        }
    }

