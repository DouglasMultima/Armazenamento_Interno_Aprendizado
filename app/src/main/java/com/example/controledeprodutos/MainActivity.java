package com.example.controledeprodutos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterProduto.OnClick {

    private AdapterProduto adapterProduto;
    private List<Produto> produtoList = new ArrayList<>();
    private SwipeableRecyclerView rvProdutos;

    private ImageButton ibAdd;
    private ImageButton ibVerMais;
    private TextView text_info;

    private ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        produtoDAO = new ProdutoDAO(this);
        produtoList = produtoDAO.getListProdutos();


        ibAdd = findViewById(R.id.ib_add);
        ibVerMais = findViewById(R.id.ib_ver_mais);
        rvProdutos = findViewById(R.id.rvProdutos);
        text_info = findViewById(R.id.text_info);



        configRecyclerView();

        ouvinteCliques();

    }

    @Override
    protected void onStart() {
        super.onStart();

        configRecyclerView();


    }

    private void ouvinteCliques(){
        ibAdd.setOnClickListener(view->{

            startActivity(new Intent(this,FormProdutoActivity.class));


        });

        ibVerMais.setOnClickListener(view ->{

            PopupMenu popupMenu = new PopupMenu(this,ibVerMais);
            popupMenu.getMenuInflater().inflate(R.menu.menu_toolbar,popupMenu.getMenu());


            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if(menuItem.getItemId() == R.id.menu_sobre){
                    Toast.makeText(this,"Sobre",Toast.LENGTH_SHORT).show();
                }

                return true;

            });

            popupMenu.show();


        });

    }




    private void configRecyclerView(){



        produtoList.clear();
        produtoList = produtoDAO.getListProdutos();


        verificaQtdLista();



        rvProdutos.setLayoutManager(new LinearLayoutManager(this));
        rvProdutos.setHasFixedSize(true);
        adapterProduto = new AdapterProduto(produtoList, this);
        rvProdutos.setAdapter(adapterProduto);

        rvProdutos.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {


            }

            @Override
            public void onSwipedRight(int position) {

                Produto produto = produtoList.get(position);

                produtoDAO.deleteProduto(produto);
                produtoList.remove(produto);
                adapterProduto.notifyItemRemoved(position);

                verificaQtdLista();


            }
        });


    }

    private void verificaQtdLista(){

        if(produtoList.size() == 0){
            text_info.setVisibility(View.VISIBLE);

        }else{
            text_info.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClickListener(Produto produto) {
        Intent intent = new Intent(this,FormProdutoActivity.class);
        intent.putExtra("produto",produto);
        startActivity(intent);
    }




}