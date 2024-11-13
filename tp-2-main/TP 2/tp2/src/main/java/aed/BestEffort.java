package aed;

import java.util.ArrayList;
import java.util.Arrays;

public class BestEffort {
    Heap traslados;
    //Heap trasladosRedituables;
    //Heap trasladosAntiguos;
    HeapPorSuperavit superavit;
    int topG;    // Máxima ganancia
    int topP;    // Máxima perdida
    int gananciaGlobal;
    int totalTraslados;
    ArrayList<Integer> ciudadesMasGanancia; 
    ArrayList<Integer> ciudadesMasPerdida; 
//    private ArrayList<Traslado> traslados; 
    ArrayList<Ciudad> ciudades; 

    public BestEffort(int cantCiudades, Traslado[] traslados){
        ArrayList<Traslado> trasladitos = new ArrayList<>(Arrays.asList(traslados));
        // Ciudad[] listaCiudades = new Ciudad[cantCiudades]; ya no hace falta
        this.ciudades = new ArrayList<>(cantCiudades);
        for (int i = 0; i < cantCiudades; i++){
            Ciudad c = new Ciudad(i, 0, 0);
            ciudades.add(c);
        }

        ciudadesMasGanancia = new ArrayList<>(); 
        ciudadesMasPerdida = new ArrayList<>();
        superavit = new HeapPorSuperavit(ciudades);
        this.traslados = new Heap(trasladitos);
        //trasladosRedituables = new Heap(trasladitos,"ganancia");
       // trasladosAntiguos = new Heap(trasladitos, "tiempo");
        topG = -1;
        topP = -1; 
        gananciaGlobal = 0;
        totalTraslados = 0;

        

//        registrarTraslados(traslados); // se registra traslado
    }

    public void registrarTraslados(Traslado[] traslados){
        for (Traslado traslado : traslados) {
          //  trasladosRedituables.agregar(traslado,"ganancia");
          //  trasladosAntiguos.agregar(traslado,"tiempo");
          this.traslados.agregar(traslado,"ganancia");
          this.traslados.agregar(traslado,"tiempo");

        }
    }

    // Función Auxiliar
    public void actualizarCiudad(Traslado traslado) {
/*        if(ciudades.get(traslado.origen) == null){
            Ciudad c = new Ciudad(traslado.origen, 0, 0);
            ciudades.set(traslado.origen, c);
        }

        if(ciudades.get(traslado.destino) == null){
            Ciudad c = new Ciudad(traslado.destino, 0, 0);
            ciudades.set(traslado.destino, c);
        }
            */
        
        Ciudad ciudadO = ciudades.get(traslado.origen); 
        ciudadO.ganancia = ciudadO.ganancia + traslado.gananciaNeta;
        ciudadO.superr += traslado.gananciaNeta;

        if (ciudadesMasGanancia.isEmpty()){
            ciudadesMasGanancia.add(ciudadO.nombre);
            this.topG = ciudadO.ganancia;
        }
        else if (ciudadO.ganancia > topG){
            ciudadesMasGanancia.clear();
            ciudadesMasGanancia.add(ciudadO.nombre);
            this.topG = ciudadO.ganancia;
        }
        else if (ciudadO.ganancia == topG){
            ciudadesMasGanancia.add(ciudadO.nombre);
        }

        superavit.agregar(ciudadO); //El agregar de superavit debe ver el caso de si la ciudad ya pertenece al superavit, en ese caso agregarlo y heapificar, caso contrario solo heapificar la ciudad modificada. 
             
        Ciudad ciudadD = ciudades.get(traslado.destino);
        ciudadD.perdida = ciudadD.perdida + traslado.gananciaNeta;
        ciudadD.superr -= traslado.gananciaNeta;

        if (ciudadesMasPerdida.isEmpty()){
            ciudadesMasPerdida.add(ciudadD.nombre);
            this.topP = ciudadD.perdida;
        }
        else if (ciudadD.perdida > topP){
            ciudadesMasPerdida.clear();
            ciudadesMasPerdida.add(ciudadD.nombre);
            this.topP = ciudadD.perdida;
        }
        else if (ciudadD.perdida == topP){
            ciudadesMasPerdida.add(ciudadD.nombre);
        }

        superavit.agregar(ciudadD); // mismo caso q el anterior
    }
        
    
    //
    public int[] despacharMasRedituables(int n){
        int i = 0;
        int[] res = new int[n]; 
        while(i < n){
          //  Traslado e = trasladosRedituables.eliminarMax("ganancia");
          Traslado e = traslados.eliminarMax("ganancia");
            res[i] = e.id;
            actualizarCiudad(e);
            gananciaGlobal = gananciaGlobal + e.gananciaNeta;
            totalTraslados = totalTraslados +1;
            i++;
        }
        return res;  
    }

    public int[] despacharMasAntiguos(int n){
        int i = 0;
        int[] res = new int[n]; 
        while(i < n){
          //  Traslado e = trasladosAntiguos.eliminarMax("tiempo");
          Traslado e = traslados.eliminarMax("tiempo");
            res[i] = e.id;
            actualizarCiudad(e);
            gananciaGlobal = gananciaGlobal + e.gananciaNeta;
            totalTraslados = totalTraslados +1;
            i++;
        }
        return res;  
    }

    public int ciudadConMayorSuperavit(){
        return superavit.verMax();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return ciudadesMasGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return ciudadesMasPerdida;
    }

    public int gananciaPromedioPorTraslado(){
         
        if(totalTraslados == 0) {
            return 0;
        }
        return gananciaGlobal/totalTraslados;
    }
    
}
