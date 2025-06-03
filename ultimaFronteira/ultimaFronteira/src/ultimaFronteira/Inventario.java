package ultimaFronteira;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Item> listaDeItens;
    private double pesoTotal;
    private double capacidadeMaxima;

    public Inventario() {
        this.listaDeItens = new ArrayList<>();
        this.pesoTotal = 0;
        this.capacidadeMaxima = 20.0; // Exemplo de capacidade máxima em kg
    }

    // Getters
    public List<Item> getItens() { return listaDeItens; }
    public double getPesoTotal() { return pesoTotal; }
    public double getCapacidadeMaxima() { return capacidadeMaxima; }

    // Métodos Principais
    public boolean adicionarItem(Item item) {
        if (pesoTotal + item.getPeso() <= capacidadeMaxima) {
            this.listaDeItens.add(item);
            this.pesoTotal += item.getPeso();
            System.out.println(item.getNome() + " (Peso: " + item.getPeso() + "kg) adicionado ao inventário.");
            return true;
        } else {
            System.out.println("Inventário cheio! Não é possível adicionar " + item.getNome() + " (Peso total: " + pesoTotal + "/" + capacidadeMaxima + ").");
            return false;
        }
    }

    public void removerItem(String nomeItem) {
        Item itemRemover = null;
        for (Item item : listaDeItens) {
            if (item.getNome().equalsIgnoreCase(nomeItem)) {
                itemRemover = item;
                break;
            }
        }
        if (itemRemover != null) {
            this.listaDeItens.remove(itemRemover);
            this.pesoTotal -= itemRemover.getPeso();
            System.out.println(itemRemover.getNome() + " removido do inventário.");
        } else {
            System.out.println("Item '" + nomeItem + "' não encontrado no inventário.");
        }
    }

    public Item getItem(String nomeItem) {
        for (Item item : listaDeItens) {
            if (item.getNome().equalsIgnoreCase(nomeItem)) {
                return item;
            }
        }
        return null;
    }

    public boolean contemItem(String nomeItem) {
        return getItem(nomeItem) != null;
    }

    public double getEspacoRestante() {
        return capacidadeMaxima - pesoTotal;
    }
}
