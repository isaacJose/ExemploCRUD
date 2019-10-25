package com.example.exemplocrud;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AlunoAdapter extends BaseAdapter {

    private List<Aluno> alunos;
    private Activity activity;

    public AlunoAdapter(Activity activity, List<Aluno> alunos ) {
        this.activity = activity;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.item, parent, false);
        TextView tv_nome = view.findViewById(R.id.tv_nome);
        TextView tv_cpf = view.findViewById(R.id.tv_cpf);
        TextView tv_telefone = view.findViewById(R.id.tv_telefone);

        Aluno aluno = alunos.get(position);

        tv_nome.setText(aluno.getNome());
        tv_cpf.setText(aluno.getCpf());
        tv_telefone.setText(aluno.getTelefone());

        return view;

    }
}
