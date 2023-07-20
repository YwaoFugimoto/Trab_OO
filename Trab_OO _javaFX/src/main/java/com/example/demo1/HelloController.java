package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pdv.dominio.*;
import pdv.dominio.excecoes.DescricaoProdutoInexistente;
import pdv.dominio.pagamento.PagamentoDinheiro;

import java.util.List;

public class HelloController {
    public static final Endereco supermecado_end = new Endereco("Rua A", "Predio",100,"São Paulo", "Vila Mariana", "A1", "12000-00");
    private static final String Registradora = "R02";
    private final Loja loja = new Loja("Supermercado X", supermecado_end);
    private final pdv.dominio.Registradora registradora = loja.getRegistradora(Registradora);
    private final CatalogoProdutos catalogo = this.registradora.getCatalogo();



    @FXML private TextField total;
    @FXML private TextField id_prod;
    @FXML private TextField qntd;
    @FXML private ListView<DescricaoProduto> catalogo_disp;
    @FXML private ListView<Cart> cart;
    @FXML private Button add_button;
    @FXML private Button fechar_compra;
    @FXML private Label label_final;





    @FXML
    public void initialize() {
        listaDeItem();
        total.setEditable(true);
        cart.setDisable(true);
        catalogo_disp.setDisable(true);
        id_prod.setDisable(true);
        qntd.setDisable(true);
        fechar_compra.setDisable(true);
        add_button.setDisable(true);
        total.setDisable(true);
    }

    @FXML
    public void listaDeItem () {
        List<DescricaoProduto> descricaoProdutoList = catalogo.getAllProdutos();
            ObservableList<DescricaoProduto> observableList = FXCollections.observableArrayList(descricaoProdutoList);
            catalogo_disp.setItems(observableList);
    }

    public void criaVenda(){
        id_prod.setDisable(false);
        qntd.setDisable(false);
        fechar_compra.setDisable(false);
        add_button.setDisable(false);
        cart.setDisable(false);
        catalogo_disp.setDisable(false);
        total.setDisable(false);
        total.setEditable(false);
        registradora.criarNovaVenda();
        totalAPagar(registradora.getVendaCorrente().calcularTotalVenda());
    }

    public void addItem() {
        try {
            int quantidade = Integer.parseInt(qntd.getText());
            registradora.entrarItem(id_prod.getText(), quantidade);
            DescricaoProduto produto = catalogo.getDescricaoProduto(id_prod.getText());
            cart
                    .getItems()
                    .add(new Cart(produto.getDescricao(), produto.getPreco(), quantidade));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            totalAPagar(registradora.getVendaCorrente().calcularTotalVenda());
        }
    }

    private void totalAPagar(Double Vtotal){
        String padrao = "%.2f R$";
        total.setText(String.format(padrao, Vtotal));
    }

    public void fecharVenda(){
        add_button.setDisable(true);
        registradora.finalizarVenda();
        id_prod.setText(" ");
        qntd.setText(" ");
        id_prod.setDisable(true);
        qntd.setDisable(true);
        label_final.setText("Venda finalizada com sucesso!");
        total.setText(" ");
        cart.setItems(null);

    }

}