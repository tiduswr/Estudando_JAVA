package classes;

public class FabricaVolks implements FabricaDeCarro{

    @Override
    public Carro criarCarro() {
        return new Gol();
    }
    
}
