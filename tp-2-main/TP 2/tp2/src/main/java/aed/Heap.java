package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap {
    public ArrayList<Handle> heapGanancia;
    private ArrayList<Handle> heapTiempo;
    private Comparator<Traslado> comparador;

    private int tamaño;
//    private Handle handle;
//    private String atributo;
//    private ArrayList<Handle> heap;

    public Heap(ArrayList<Traslado> array){
        this.heapGanancia = new ArrayList<>();  // Aquí no usamos copyOf
        this.heapTiempo = new ArrayList<>();

        for(int i=0; i < array.size(); i++){
            this.heapGanancia.add(new Handle(array.get(i), i, i));
            this.heapTiempo.add(new Handle(array.get(i),i, i));
        }
        tamaño = array.size();
        constructorHeap();
    }
 /*   public Heap(ArrayList<Traslado> array, String atributo) {
        this.heapGanancia = new ArrayList<>();  // Aquí no usamos copyOf
        this.heapTiempo = new ArrayList<>();
        this.atributo = atributo;
        

        if(atributo.equals("ganancia")){
            this.comparador = new TrasladosComparatorGanancia();
//            heap = heapGanancia;
        }else if(atributo.equals("tiempo")){
            this.comparador = new TrasladosComparatorTimestamp();
//            heap = heapTiempo;
        }
        
        for(int i=0; i < array.size(); i++){
            this.heapGanancia.add(new Handle(array.get(i), i, i));
            this.heapTiempo.add(new Handle(array.get(i),i, i));
        }

        tamaño = array.size();
        constructorHeap();
    }
 */
  // Construir el heapGanancia Max y el heapTimestamp Min
  private void constructorHeap() {
        for (int i = heapTiempo.size()/ 2 - 1; i >= 0; i--) {
            heapifyDown(i,heapTiempo, "tiempo");
        }
        for (int j = heapGanancia.size()-1; j>= 0 ; j--){
            heapifyUp(j, heapGanancia, "ganancia");
        }
  }
  /*  private void constructorHeap() {
        if (atributo.equals("ganancia")){
            for (int i = heapGanancia.size()/ 2 - 1; i >= 0; i--) {
              heapifyUp(i,heapGanancia,"ganancia");
            
              tamaño ++;
            }
        }else if(atributo.equals("tiempo")){
            for (int i = heapTiempo.size()/ 2 - 1; i >= 0; i--) {
                heapifyDown(i,heapTiempo, "tiempo");
              
//                tamaño ++;
        }      
        }
    }
*/
    // Mantener la propiedad de heapGanancia Max
    private void heapifyDown(int i, ArrayList<Handle> heap, String atributo) {
        if(atributo.equals("ganancia")){
            comparador = new TrasladosComparatorGanancia();
//            heap = heapGanancia;
        }else if(atributo.equals("tiempo")){
            comparador = new TrasladosComparatorTimestamp();
//            heap = heapTiempo;
        }

        int posicionIzq = 2 * i + 1;
        int posicionDer = 2 * i + 2;
        int posicionActual = i;
//        Handle mayorHandle = heapGanancia.get(i);
//        Traslado izq = heap.get(2 * i + 1).traslado;
//        Traslado der= heap.get(2 * i + 2).traslado;
        Traslado actual = heap.get(i).traslado;


        // Verificar si el hijo izquierdo es mayor que el padre
        if (posicionIzq < heap.size() && comparador.compare(heap.get(posicionIzq).traslado, actual) < 0) {
            posicionActual = posicionIzq;
        }

        // Verificar si el hijo derecho es mayor que el padre
        if (posicionDer < heap.size() && comparador.compare(heap.get(posicionDer).traslado, heap.get(posicionActual).traslado) < 0) {
            posicionActual = posicionDer;
            }  
        
       /* if (posicionActual == i && posicionIzq < heap.size() && posicionDer < heap.size() && comparador.compare(actual, MayorHijo(heap.get(posicionDer).traslado, heap.get(posicionIzq).traslado)) == 0){
            Traslado nuevo = MayorHijo(heap.get(posicionIzq).traslado, heap.get(posicionDer).traslado);
            int posNuevo = posMayor(heap.get(posicionIzq).traslado, heap.get(posicionDer).traslado, posicionActual);
            if (nuevo.id < actual.id){
                posicionActual = posNuevo;
            }
        }*/
        // Si el mayor no es el padre, intercambiar y continuar heapificando
        if (posicionActual != i) {
            swap(i, posicionActual, atributo);
            heapifyDown(posicionActual, heap, atributo);
        }
    }
    

    // Intercambiar elementos en el arreglo

    private void swap(int i, int j, String atributo) {

        if(atributo.equals("ganancia")){
            Handle t = heapGanancia.get(i);
            heapGanancia.set(i, heapGanancia.get(j));
            heapGanancia.set(j, t);

            heapGanancia.get(i).posicionG = i;
            heapGanancia.get(j).posicionG = j;
            
            heapTiempo.get(t.posicionT).posicionG = i;
            heapTiempo.get(heapGanancia.get(j).posicionT).posicionG = j;
        }
        else if(atributo.equals("tiempo")){
            Handle t = heapTiempo.get(i);
            heapTiempo.set(i, heapTiempo.get(j));
            heapTiempo.set(j, t);

            heapTiempo.get(i).posicionT = i;
            heapTiempo.get(j).posicionT = j;

            heapGanancia.get(t.posicionG).posicionT = i;
            heapGanancia.get(heapTiempo.get(j).posicionG).posicionT = j;
        }

    }

    private void heapifyUp(int i, ArrayList<Handle> heap, String atributo){
        if(atributo.equals("ganancia")){
            comparador = new TrasladosComparatorGanancia();
//            heap = heapGanancia;
        }else if(atributo.equals("tiempo")){
            comparador = new TrasladosComparatorTimestamp();
//            heap = heapTiempo;
        }

        int posPadre = (i-1)/2;
//        int posActual = i;

        if ( i > 0 && comparador.compare(heap.get(i).traslado, heap.get(posPadre).traslado) < 0) {
            //            swap(posicionIzq,posicionActual,atributo);
            swap(i, posPadre, atributo);
            heapifyUp(posPadre,heap,atributo);
        }
    }

    /*     
        if(atributo == "ganancia"){
            Traslado padre = heapGanancia.get(posPadre).traslado;
            Traslado actual = heapGanancia.get(i).traslado;
            
            if(i>0 && actual.gananciaNeta > padre.gananciaNeta){
                swap(posActual, posPadre, atributo);
            }
        else if(atributo == "timestamp"){
            Traslado padre2 = heapTiempo.get(posPadre).traslado;
            Traslado actual2 = heapTiempo.get(i).traslado;
            
            if(i>0 && actual2.timestamp > padre2.timestamp){
                swap(posActual, posPadre, atributo);
            }
        }


    }
}
*/




    public void agregar(Traslado tras, String atributo){
        Handle h = new Handle(tras, 0, 0);
        int posicion = tamaño;

        if(atributo.equals("ganancia")){
            h.posicionG = posicion;
            heapGanancia.add(h);
            heapifyUp(posicion,heapGanancia,"ganancia");

        }else if(atributo.equals("tiempo")){
            h.posicionT = posicion;
            heapTiempo.add(h);
            heapifyUp(posicion,heapTiempo,"tiempo");
        }
        
        tamaño++;
    }

        /*Handle padre = heapGanancia.get((tamaño-1) / 2);
        int posActual = tamaño;
        int posPadre = (tamaño-1) / 2;

        while (tras.gananciaNeta > padre.gananciaNeta){
            heapGanancia.set(posPadre, tras);
            heapGanancia.set(posActual, padre);

            posActual = posPadre;
            posPadre = (posActual-1)/2;
        }
        tamaño ++;

    }
*/
   /*  private Traslado MayorHijo(Traslado t1, Traslado t2){
        if(t1.gananciaNeta > t2.gananciaNeta){
            return t1;
        }
        else if(t2.gananciaNeta > t1.gananciaNeta){
            return t2;
        }
        else{
            if (t1.id < t2.id){
                return t1;
            }
        }
        return t2;
    }

    private int posMayor(Traslado izq, Traslado der, int i){
        if(MayorHijo(izq, der) == izq){
            return 2*i+1;
        }
        return 2*i+2;

    } */

    private void sincronizar(ArrayList<Handle> heapM, int pos, String atributo){
        if(pos >= 0 && pos < tamaño){
           heapM.set(pos,heapM.get(tamaño-1));
           heapM.remove(tamaño-1);
           heapifyDown(pos,heapM,atributo);
           heapifyUp(pos,heapM,atributo);
        }

    }

    public Traslado eliminarMax(String atributo){
//        if(tamaño == 0){
//            return;
//        }
        if(atributo.equals("ganancia")){
            comparador = new TrasladosComparatorGanancia();
//            heap = heapGanancia;
        }else if(atributo.equals("tiempo")){
            comparador = new TrasladosComparatorTimestamp();
//            heap = heapTiempo;
        }

//        ArrayList<Handle> heap = new ArrayList<Handle>();
        
        if(atributo.equals("ganancia")){
            if(tamaño == 0){
                return null;
            }
    
            Handle res = heapGanancia.get(0);
            heapGanancia.set(0, heapGanancia.get(tamaño-1));
            heapGanancia.remove(tamaño-1);
            tamaño --;
        
            heapifyDown(0,heapGanancia,"ganancia");
    
            //if(atributo.equals("ganancia")){
            sincronizar(heapTiempo,res.posicionT,atributo);
            //}
            //if(atributo.equals("tiempo")){
            //sincronizar(heapGanancia, res.posicionG);
            //}
    
            return res.traslado;
            
        }
            
        
//        else if(atributo.equals("tiempo")){
        else{
            if(tamaño == 0){
                return null;
            }
    
            Handle res = heapTiempo.get(0);
            heapTiempo.set(0, heapTiempo.get(tamaño-1));
            heapTiempo.remove(tamaño-1);
            tamaño --;
        
            heapifyDown(0,heapTiempo, "tiempo");
    
            //if(atributo.equals("ganancia")){
              //  sincronizar(heapTiempo,res.posicionT);
            //}
            //if(atributo.equals("tiempo")){
            sincronizar(heapGanancia, res.posicionG,atributo);
            //}
    
            return res.traslado;
            
        }
    }


}








       /*  int posActual = 0;
        Handle actual = heapGanancia.get(0);
        int posMayorHijo;

        while(true){
            int posHijoIzq = 2 * posActual + 1;
            int posHijoDer = 2 * posActual + 2;

            if(posHijoDer >= tamaño && posHijoIzq >= tamaño){
                break;
            }
            if(posHijoDer >= tamaño){
                posMayorHijo = posHijoIzq;
            }else if(posHijoIzq >= tamaño){
                posMayorHijo = posHijoDer;
            }
            else{
                Traslado HijoIzq = heapGanancia.get(posHijoIzq);
                Traslado HijoDer = heapGanancia.get(posHijoDer);
                posMayorHijo = posMayor(HijoIzq, HijoDer, posActual);
            }
            
            Traslado hijoMayor = heapGanancia.get(posMayorHijo);

            if(actual.gananciaNeta < hijoMayor.gananciaNeta){
                heapGanancia.set(posActual, hijoMayor);
                heapGanancia.set(posMayorHijo, actual);
            }else{
                break;
            }
            posActual = posMayorHijo;
            
        }
        tamaño --;
        return res.id;
    
    }
}
    */

