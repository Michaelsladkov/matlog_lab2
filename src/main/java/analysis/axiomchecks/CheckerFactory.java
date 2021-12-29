package analysis.axiomchecks;

import java.util.ArrayList;
import java.util.List;

public class CheckerFactory {
    public List<AxiomChecker> getCheckers() {
        List<AxiomChecker> ret = new ArrayList<>(10);

        ret.add(new FirstAxiomChecker());

        return ret;
    }
}
