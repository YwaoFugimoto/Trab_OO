package com.example.demo1;

public record Cart(String nome, Double precoUnd, int qntd, Double total){
    public Cart(String nome, Double precoUnd, int qntd){
        this(nome, precoUnd, qntd, precoUnd*qntd);
    }

    @Override
    public String toString(){
        return nome + "\tR$" + precoUnd + "\tQuantidade" + qntd + "\tTotal:" + total + "\tR$";
    }
}
