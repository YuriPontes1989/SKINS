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
import  static com.example.skins.ui.activities.ConstatesActivities.CHAVE_PERSONAGEM;

public class FormularioPersonagemActivity extends AppCompatActivity {
        //criando as variaveis
        //variaveis em formato string para alterar  o texto do cabeçario, static para não poder ser editado pelo usuário.
        private static final String TITULO_APPBAR_EDITAR_PERSONAGEM = "Editar o Personagem";
        private static final String TITULO_APPBAR_NOVO_PERSONAGEM = "Novo Personagem";
        // Variáveis com campo de textos
        private EditText campoNome;
        private EditText campoNascimento;
        private EditText campoAltura;
        // Variaveis das classes javas ja então criadas elas são: PersonagemDAO e Personagem.
        private final PersonagemDAO dao = new PersonagemDAO();
        private Personagem personagem;
        
        @Override
        //Cria um menu no cabeçario, menu feito no xml.
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        // Sobrescreve o onOptionsItemSelected da classe pai, passa o id e retorna item.
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.activity_formulario_personagem_menu_salvar){
                finalizarFormulario();
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        // Sobrescreve o método onCreate, chama a primeira tela e alguns métodos nela.
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_formulario_personagem);
            inicializacaoCampos();
            //configuraBotaoSalvar();
            carregaPersonagem();
            // checa se tem permissão
        }
        // usando o metodo  que carrega os personagens ja criados 
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
        // usando metodo que  passará os dados prenchidos para classe personagem
        private void preencheCampos() {
            campoNome.setText(personagem.getNome());
            campoAltura.setText(personagem.getAltura());
            campoNascimento.setText(personagem.getNascimento());
        }
        //usando metodo que  finaliza e salva os dados
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
        // usando método que buscará os campos já criados no xml e atribuirá a eles as variáveis de campo de textos, formata os inputs de textos.

        private void inicializacaoCampos() {
            campoNome = findViewById(R.id.editText_nome);
            campoNascimento = findViewById(R.id.editText_nascimento);
            campoAltura = findViewById(R.id.editText_altura);

            SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN");
            MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura);
            campoAltura.addTextChangedListener(mtwAltura);

            SimpleMaskFormatter smfNascimento =new SimpleMaskFormatter("NN/NN/NNNN");
            MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoAltura, smfNascimento);
            campoNascimento.addTextChangedListener(mtwNascimento);
        }
         // usando o método que passa os dados inputados pelo usuário para a classe Personagem.
        private void preencherPersonagem() {
            String nome = campoNome.getText().toString();
            String nascimento = campoNascimento.getText().toString();
            String altura = campoAltura.getText().toString();

            personagem.setNome(nome);
            personagem.setAltura(altura);
            personagem.setNascimento(nascimento);
        }
    }

