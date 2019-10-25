package com.example.exemplocrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class MainActivity extends AppCompatActivity {

    private EditText et_nome;
    private EditText et_cpf;
    private EditText et_telefone;
    private Button bt_salvar;
    private Button bt_listar;
    private Button bt_sair;
    private AlunoDAO dao;
    private Aluno aluno = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configurarBotoes(); carregarBotoes();

        dao = new AlunoDAO(this);

        Intent intent = getIntent();
        if (intent.hasExtra("aluno")){
            aluno = (Aluno) intent.getSerializableExtra("aluno");
            et_nome.setText(aluno.getNome());
            et_cpf.setText(aluno.getCpf());
            et_telefone.setText(aluno.getTelefone());
        }
    }

    private void configurarBotoes(){
        et_nome = findViewById(R.id.et_nome);
        et_cpf = findViewById(R.id.et_cpf);
        et_telefone = findViewById(R.id.et_telefone);
        bt_salvar = findViewById(R.id.bt_salvar);
        bt_listar = findViewById(R.id.bt_listar);
        bt_sair = findViewById(R.id.bt_sair);

        mascara();
    }

    private void carregarBotoes(){

        bt_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aluno == null) {
                    aluno = new Aluno();
                    aluno.setNome(et_nome.getText().toString());
                    aluno.setCpf(et_cpf.getText().toString());
                    aluno.setTelefone(et_telefone.getText().toString());

                    long id = dao.inserir(aluno);
                    Toast.makeText(MainActivity.this, "Aluno inserido com id: " + id, Toast.LENGTH_SHORT).show();
                } else{
                    aluno.setNome(et_nome.getText().toString());
                    aluno.setCpf(et_cpf.getText().toString());
                    aluno.setTelefone(et_telefone.getText().toString());
                    dao.atualizar(aluno);
                    Toast.makeText(MainActivity.this, "Aluno atualizado", Toast.LENGTH_SHORT).show();

                }
            }
        });

        bt_listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarActivity();
            }
        });

        bt_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void carregarActivity(){
        startActivity(new Intent(this,Main2Activity.class));
    }

    private void mascara(){

        //MÁSCARA PARA CAMPO TELEFONE
        SimpleMaskFormatter smf_telefone = new SimpleMaskFormatter("(NN)N NNNN-NNNN");
        MaskTextWatcher mtw_telefone = new MaskTextWatcher(et_telefone,smf_telefone);
        et_telefone.addTextChangedListener(mtw_telefone);

        //MÁSCARA PARA CAMPO CPF
        SimpleMaskFormatter smf_cpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw_cpf = new MaskTextWatcher(et_cpf,smf_cpf);
        et_cpf.addTextChangedListener(mtw_cpf);

    }
}
