package com.example.exemplocrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public AlunoDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Aluno aluno){
        ContentValues values = new ContentValues();
        values.put("nome",aluno.getNome());
        values.put("cpf",aluno.getCpf());
        values.put("telefone",aluno.getTelefone());
        values.put("email",aluno.getEmail());

        return banco.insert("aluno",null,values);
    }

    public List<Aluno> obterTodos(){
        List<Aluno> alunos = new ArrayList<>();
        Cursor cursor = banco.query("aluno",new String[]{"id","nome","cpf","telefone","email"},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(cursor.getInt(0));
            aluno.setNome(cursor.getString(1));
            aluno.setCpf(cursor.getString(2));
            aluno.setTelefone(cursor.getString(3));
            aluno.setEmail(cursor.getString(4));

            alunos.add(aluno);
        }

        return alunos;
    }

    public void atualizar(Aluno aluno){
        ContentValues values = new ContentValues();
        values.put("nome",aluno.getNome());
        values.put("cpf",aluno.getCpf());
        values.put("telefone",aluno.getTelefone());
        values.put("email",aluno.getEmail());

        banco.update("aluno",values,"id = ?",
                new String[]{aluno.getId().toString()});
    }

    public void excluir(Aluno aluno){
        banco.delete("aluno", "id = ?", new String[]{aluno.getId().toString()});
    }
}
