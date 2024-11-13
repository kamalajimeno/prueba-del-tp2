package aed;
import java.util.Comparator;

public class TrasladosComparatorGananciaa implements Comparator<Trasladoo> {
    @Override
    public int compare(Trasladoo t1, Trasladoo t2){
        return Integer.compare(t2.gananciaNeta, t1.gananciaNeta);
    }
}

