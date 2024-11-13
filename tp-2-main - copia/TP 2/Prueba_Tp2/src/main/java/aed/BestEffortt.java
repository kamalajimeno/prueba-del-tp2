package aed;

import java.util.ArrayList;
import java.util.Arrays;

public class BestEffort {
    Heap trasladosRedituables;
    Heap trasladosAntiguos;
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
        trasladosRedituables = new Heap(trasladitos,"ganancia");
        trasladosAntiguos = new Heap(trasladitos, "tiempo");
        topG = -1;
        topP = -1; 

        

//        registrarTraslados(traslados); // se registra traslado
    }

    public void registrarTraslados(Traslado[] traslados){
        for (Traslado traslado : traslados) {
            trasladosRedituables.agregar(traslado,"ganancia");
            trasladosAntiguos.agregar(traslado,"tiempo");

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
        
        for (Ciudad ciudad : ciudades) {

            if (traslado.origen == ciudad.nombre) {
                ciudad.ganancia = ciudad.ganancia + traslado.gananciaNeta;
                ciudad.superr += traslado.gananciaNeta;

                if (ciudadesMasGanancia.isEmpty()){
                    ciudadesMasGanancia.add(ciudad.nombre);
                    this.topG = ciudad.ganancia;
                }
                if (ciudad.ganancia > topG){
                    ciudadesMasGanancia.clear();
                    ciudadesMasGanancia.add(ciudad.nombre);
                    this.topG = ciudad.ganancia;
                }
                if (ciudad.ganancia == topG){
                    ciudadesMasGanancia.add(ciudad.nombre);
                }

                superavit.agregar(ciudad); //El agregar de superavit debe ver el caso de si la ciudad ya pertenece al superavit, en ese caso agregarlo y heapificar, caso contrario solo heapificar la ciudad modificada. 
            } 

            if (traslado.destino == ciudad.nombre) {
                ciudad.perdida = ciudad.perdida + traslado.gananciaNeta;
                ciudad.superr -= traslado.gananciaNeta;

                if (ciudadesMasPerdida.isEmpty()){
                    ciudadesMasPerdida.add(ciudad.nombre);
                    this.topP = ciudad.perdida;
                }
                if (ciudad.perdida > topP){
                    ciudadesMasPerdida.clear();
                    ciudadesMasPerdida.add(ciudad.nombre);
                    this.topP = ciudad.perdida;
                }
                if (ciudad.perdida == topG){
                    ciudadesMasPerdida.add(ciudad.nombre);
                }

                superavit.agregar(ciudad); // mismo caso q el anterior
            }
        }
    }
    //
    public int[] despacharMasRedituables(int n){
        int i = 0;
        int[] res = new int[n]; 
        while(i < n){
            Traslado e = trasladosRedituables.eliminarMax("ganancia");
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
            Traslado e = trasladosAntiguos.eliminarMax("tiempo");
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
