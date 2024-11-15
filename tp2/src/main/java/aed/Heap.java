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
        for (int i = heapTiempo.size()/ 2 - 1; i >= 0; i--) {
            heapifyDown(i,heapTiempo, "tiempo");
        }
        for (int j = heapGanancia.size()-1; j>= 0 ; j--){
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

        int posicionIzq = (2 * i) + 1;
        int posicionDer = (2 * i )+ 2;
        int posicionActual = i;



        // Verificar si el hijo izquierdo es mayor que el padre
        if (posicionIzq < tamaño && comparador.compare(heap.get(posicionIzq).traslado, heap.get(posicionActual).traslado) < 0) {
            posicionActual = posicionIzq;
        }

        // Verificar si el hijo derecho es mayor que el padre
        if (posicionDer < tamaño && comparador.compare(heap.get(posicionDer).traslado, heap.get(posicionActual).traslado) < 0) {
            posicionActual = posicionDer;
        } 

        if (posicionActual != i) {
            swap(i, posicionActual, atributo);
            heapifyDown(posicionActual, heap, atributo);
        }
    }
    

    // Intercambiar elementos en el arreglo

    private void swap(int i, int j, String atributo) {

        if(atributo.equals("ganancia")){
           
            Handle a = heapGanancia.get(i);
            Handle b = heapGanancia.get(j);

            a.posicionG = j;
            b.posicionG = i;

            a.posicionT = heapTiempo.get(heapGanancia.get(i).posicionT).posicionT;//}
            b.posicionT = heapTiempo.get(heapGanancia.get(j).posicionT).posicionT;//}
         
            heapGanancia.set(i, b);
            heapGanancia.set(j,a);

            heapTiempo.set( heapGanancia.get(i).posicionT, b);
            heapTiempo.set( heapGanancia.get(j).posicionT,a);
        }

        else if(atributo.equals("tiempo")){
            Handle a = heapTiempo.get(i);
            Handle b = heapTiempo.get(j);

            a.posicionT = j;
            b.posicionT = i;
            
            a.posicionG = heapGanancia.get(heapTiempo.get(i).posicionG).posicionG;//}
            b.posicionG = heapGanancia.get(heapTiempo.get(j).posicionG).posicionG;//}
       

            heapTiempo.set(i, b);
            heapTiempo.set(j,a);

            heapGanancia.set(heapTiempo.get(i).posicionG, b);
            heapGanancia.set(heapTiempo.get(j).posicionG, a);
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

        if (i > 0 && comparador.compare(heap.get(i).traslado, heap.get(posPadre).traslado) < 0) {
            //            swap(posicionIzq,posicionActual,atributo);
            swap(i, posPadre, atributo);
            heapifyUp(posPadre,heap,atributo);
        }
    }



    public void agregar(Traslado tras/*, String atributo*/){
        Handle h = new Handle(tras, tamaño, tamaño);

      //  if(atributo.equals("ganancia")){
            heapGanancia.add(h);
            heapTiempo.add(h);
            heapifyUp(tamaño,heapGanancia,"ganancia");

    //    }else if(atributo.equals("tiempo")){
         //   heapTiempo.add(h);
            heapifyUp(tamaño,heapTiempo,"tiempo");
        //}
        
        this.tamaño++;
    }

    private void sincronizar(ArrayList<Handle> heapM, int pos, String atributo){
        if(pos >= 0 && pos < tamaño){
           heapM.set(pos,heapM.get(tamaño));
           heapM.remove(tamaño);
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

        if(tamaño == 0){ return null;}
        tamaño--;

        if(atributo.equals("ganancia")){
          
            Handle res = heapGanancia.get(0);
            heapGanancia.set(0, heapGanancia.get(tamaño));
            heapGanancia.remove(tamaño);
            if (tamaño != 0 ) {
                heapifyDown(0,heapGanancia,atributo);
            }  
           
            if(res.posicionT == tamaño){ heapTiempo.remove(tamaño);}
            sincronizar(heapTiempo,res.posicionT,atributo);
            return res.traslado;   
        }
            
        else{
          
            Handle res = heapTiempo.get(0);
            heapTiempo.set(0, heapTiempo.get(tamaño));
            heapTiempo.remove(tamaño);
            if (tamaño != 0) {
                heapifyDown(0,heapTiempo, atributo);
            }
           
            if(res.posicionG == tamaño){heapGanancia.remove(tamaño);}
            sincronizar(heapGanancia, res.posicionG,atributo);
            return res.traslado;
            
        }
    }
}





