package com.example.exemplocrud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

    protected void onRestart(){
        super.onRestart();
        et_nome.setText("");
        et_cpf.setText("");
        et_telefone.setText("");
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

                if(validaCampos()){
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Aviso")
                            .setMessage("Há campos inválidos ou em branco!")
                            .setNeutralButton("OK",null).create();
                    dialog.show();
                }

                else if(aluno == null) {
                    aluno = new Aluno();
                    aluno.setNome(et_nome.getText().toString());
                    aluno.setCpf(et_cpf.getText().toString());
                    aluno.setTelefone(et_telefone.getText().toString());

                    long id = dao.inserir(aluno);
                    Toast.makeText(MainActivity.this, "Aluno inserido com id: " + id, Toast.LENGTH_SHORT).show();
                }
                else{
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
              new  AlertDialog.Builder(MainActivity.this)
                        .setMessage("Deseja realmente sair?")
                        .setCancelable(false)
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton("NÃO",null)
                        .show();
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

    private boolean  validaCampos(){

        boolean res = false;

        String nome = et_nome.getText().toString();
        String cpf = et_cpf.getText().toString();
        String telefone = et_telefone.getText().toString();

        if (isCampoVazio(nome)){
            et_nome.requestFocus();
            res = true;
        }
        else{
            if (isCampoVazio(cpf)){
                et_cpf.requestFocus();
                res = true;
            }
            else {
                if (isCampoVazio(telefone)){
                    et_telefone.requestFocus();
                    res = true;
                }
            }
        }
        if (res){
            return true;
        }
        return false;
        }

    private boolean isCampoVazio(String valor){

        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());   //Trim, retira todos os espaços
        return  resultado;
    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setMessage("Deseja realmente sair?")
                .setCancelable(false)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("NÃO",null)
                .show();
    }
}
