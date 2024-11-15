package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap {
    public ArrayList<Handle> heapGanancia;
    private ArrayList<Handle> heapTiempo;
    private Comparator<Traslado> comparador;

    private int tamaño;

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
 
  // Construir el heapGanancia Max y el heapTimestamp Min
  private void constructorHeap() {
        for (int i = tamaño/ 2 - 1; i >= 0; i--) {
            heapifyDown(i,heapTiempo, "tiempo");
        }
        for (int j = tamaño-1; j> 0 ; j--){
            heapifyUp(j, heapGanancia, "ganancia");
        }
  }

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
        if (i>heap.size()&& heap.isEmpty()){return;}

        else{
           // Traslado actual = heap.get(i).traslado;
        // Verificar si el hijo izquierdo es mayor que el padre
        if (posicionIzq < heap.size() && comparador.compare(heap.get(posicionIzq).traslado, heap.get(i).traslado) < 0) {
            posicionActual = posicionIzq;
        }

        // Verificar si el hijo derecho es mayor que el padre
        if (posicionDer < heap.size() && comparador.compare(heap.get(posicionDer).traslado, heap.get(posicionActual).traslado) < 0) {
            posicionActual = posicionDer;
            }  

        // Si el mayor no es el padre, intercambiar y continuar heapificando
        if (posicionActual != i) {
            swap(i, posicionActual, atributo);
            heapifyDown(posicionActual, heap, atributo);
         
        }
        }
    }
    

    // Intercambiar elementos en el arreglo

    private void swap(int i, int j, String atributo) {

        if(atributo.equals("ganancia")){
          //  Handle t = heapGanancia.get(i);
          if(i==heapGanancia.size()){i=heapGanancia.size()-1;}
          if(j== heapGanancia.size()) {j=heapGanancia.size()-1;}

          Handle t1 =heapGanancia.get(i);
          Handle t2 = heapGanancia.get(j);

          heapGanancia.set(i,t2);
          heapGanancia.set(j, t1);

          // heapGanancia.set(i, heapGanancia.get(j));
          //  heapGanancia.set(j, t);

            heapGanancia.get(i).posicionG = i;
            heapGanancia.get(j).posicionG = j;
            
            heapTiempo.get(t1.posicionT).posicionG = i;
            heapTiempo.get(t2.posicionT).posicionG = j;
          //  heapTiempo.get(t.posicionT).posicionG = i;
          //  heapTiempo.get(heapGanancia.get(j).posicionT).posicionG = j;
        }
        else if(atributo.equals("tiempo")){
            if(i==heapTiempo.size()){i=heapTiempo.size()-1;}
            if(j== heapTiempo.size()) {j=heapTiempo.size()-1;}
          //  Handle t = heapTiempo.get(i);
          Handle t1 = heapTiempo.get(i);
          Handle t2 = heapTiempo.get(j);

         //   heapTiempo.set(i, heapTiempo.get(j));
           // heapTiempo.set(j, t);

            heapTiempo.get(i).posicionT = i;
            heapTiempo.get(j).posicionT = j;

            heapGanancia.get(t1.posicionG).posicionT = i;
            heapGanancia.get(t2.posicionG).posicionT = j;

          //  heapGanancia.get(t.posicionG).posicionT = i;
         //   heapGanancia.get(heapTiempo.get(j).posicionG).posicionT = j;
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
        if (i>heap.size()&& heap.isEmpty()){return;}


        int posPadre = (i-1)/2;
//        int posActual = i;

        if ( (i > 0 && i < tamaño) && comparador.compare(heap.get(i).traslado, heap.get(posPadre).traslado) < 0) {
            //            swap(posicionIzq,posicionActual,atributo);
            swap(i, posPadre, atributo);
            heapifyUp(posPadre,heap,atributo);
        }
    }


public void agregar(Traslado tras){

    Handle h = new Handle(tras, tamaño, tamaño);

    heapGanancia.add(h);
    heapTiempo.add(h);
    heapifyUp(tamaño,heapGanancia,"ganancia");
    heapifyUp(tamaño,heapTiempo,"tiempo");    
    this.tamaño++;
}

    private void sincronizar(ArrayList<Handle> heapM, int pos, String atributo){
        if(pos >= 0 &&  pos < tamaño){
           heapM.set(pos,heapM.get(tamaño/*-1*/));
           heapM.remove(tamaño/*-1*/);
           heapifyDown(pos,heapM,atributo);
           heapifyUp(pos,heapM,atributo);
        }

    }

    public Traslado eliminarMax(String atributo){
        if(atributo.equals("ganancia")){
            comparador = new TrasladosComparatorGanancia();
        }else if(atributo.equals("tiempo")){
            comparador = new TrasladosComparatorTimestamp();
        }

        if(tamaño == 0){return null;}

        tamaño --;
        if(atributo.equals("ganancia")){
            
            //if(tamaño == 0){return null;}

            Handle res = heapGanancia.get(0);
            heapGanancia.set(0, heapGanancia.get(tamaño/*-1*/));
            heapGanancia.remove(tamaño/*-1*/);
           // tamaño --;
            if (tamaño != 0 ) {
                heapifyDown(0,heapGanancia,"ganancia");
                
            }
            sincronizar(heapTiempo,res.posicionT,atributo);
            if(res.posicionT == tamaño){ heapTiempo.remove(tamaño);}
            return res.traslado;
            
        }
            
        
        else{
           // if(tamaño == 0){return null;}

            Handle res = heapTiempo.get(0);
            heapTiempo.set(0, heapTiempo.get(tamaño/*-1*/));
            heapTiempo.remove(tamaño/*-1*/);
           // tamaño --;
            if (tamaño != 0) {
                heapifyDown(0,heapTiempo, "tiempo");
            }
            sincronizar(heapGanancia, res.posicionG,atributo);
            if(res.posicionG == tamaño){heapGanancia.remove(tamaño);}
            return res.traslado;
            
        }
    }
}
