package com.example.exemplocrud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private ListView lv_alunos;
    private Button bt_voltar;
    private AlunoDAO dao;
    private List<Aluno> alunos;
    private List<Aluno> alunosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        lv_alunos = findViewById(R.id.lv_alunos);
        bt_voltar = findViewById(R.id.bt_voltar);

        carregarBotoes();

        dao = new AlunoDAO(this);
        alunos = dao.obterTodos();
        alunosFiltrados.addAll(alunos);
        //ArrayAdapter<Aluno> adaptador = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,alunosFiltrados);
        AlunoAdapter adaptador = new AlunoAdapter(this,alunosFiltrados);
        lv_alunos.setAdapter(adaptador);
        registerForContextMenu(lv_alunos);

    }

    private void carregarBotoes(){

        bt_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, MainActivity.class));
                Main2Activity.this.finish();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarAluno(newText);
                return false;
            }
        });

        return true;
    }

    public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(contextMenu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto, contextMenu);
    }

    public boolean onContextItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.id_excluir){
            AdapterView.AdapterContextMenuInfo menuInfo =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            final Aluno alunoExcluir = alunosFiltrados.get(menuInfo.position);

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Atenção")
                    .setMessage("Realmente deseja excluir o aluno?")
                    .setNegativeButton("NÃO",null)
                    .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alunosFiltrados.remove(alunoExcluir);
                            alunos.remove(alunoExcluir);
                            dao.excluir(alunoExcluir);
                            lv_alunos.invalidateViews();
                        }
                    }).create();
            dialog.show();
            return true;
        }
        else if (id == R.id.id_atualizar){
            AdapterView.AdapterContextMenuInfo menuInfo =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final Aluno alunoAtualizar = alunosFiltrados.get(menuInfo.position);

            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("aluno",alunoAtualizar);
            startActivity(intent);
            return true;
        }
        return onContextItemSelected(item);
    }

    public void cadastrar(MenuItem menuItem){
        /*Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);*/
        startActivity(new Intent(this,MainActivity.class));
        Main2Activity.this.finish();
    }

    public void buscarAluno(String nome){
        alunosFiltrados.clear();
        for(Aluno aluno: alunos){
            if(aluno.getNome().toLowerCase().contains(nome.toLowerCase())){
                alunosFiltrados.add(aluno);
            }
        }
        lv_alunos.invalidateViews();
    }

    @Override
    public void onResume(){
        super.onResume();
        alunos = dao.obterTodos();
        alunosFiltrados.clear();
        alunosFiltrados.addAll(alunos);
        lv_alunos.invalidateViews();
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(Main2Activity.this, MainActivity.class));
        Main2Activity.this.finish();
    }


}
