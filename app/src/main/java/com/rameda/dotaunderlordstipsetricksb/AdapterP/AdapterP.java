package com.rameda.dotaunderlordstipsetricksb.AdapterP;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rameda.dotaunderlordstipsetricksb.Modelo.PostModelo;
import com.rameda.dotaunderlordstipsetricksb.R;
import com.rameda.dotaunderlordstipsetricksb.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AdapterP extends RecyclerView.Adapter<AdapterP.MyViewHolder> {

    private PostModelo postModelo=new PostModelo();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DatabaseReference myRef = database.getReference("message");
    public static List<PostModelo> listpostModelo = new ArrayList<PostModelo>();
    private int tamanho = 0;

    public AdapterP() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycle_view,parent,false );
        return new MyViewHolder(itemLista);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        db.collection("Texto")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String texto = String.valueOf(document.getData().get("Texto"));
                                String titulo = String.valueOf(document.getData().get("Titulo"));
                                    postModelo.setCorpoTexto(texto);
                                    postModelo.setTitulo(titulo);
                                    listpostModelo.add(postModelo);

                                Log.d(TAG, document.getId() + " => " + document.getData().get("Titulo"));
                                Log.d(TAG, document.getId() + " => " + document.getData().get("Texto"));
                                tamanho = document.getData().size();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        myViewHolder.titulo.setText(postModelo.getTitulo());
        myViewHolder.corpoTexto.setText(postModelo.getCorpoTexto());
      //  myViewHolder.imagem.setText("ação");
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titulo,corpoTexto,imagem;
        //guarda formato do viewHolder
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tv_tituloTexto);
            corpoTexto = itemView.findViewById(R.id.tv_CorpoTexto);
           // imagem = itemView.findViewById(R.id.iv_imagemPost);

        }
    }

}