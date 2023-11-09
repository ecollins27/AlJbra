import aljbra.*;
import aljbra.trig.Trig;

public class Main {

    public static void main(String[] args) {
        Variable t = new Variable('t');
        Expression x = new Variable('a',"1").add(new Variable('b',"1").multiply(t));
        Expression y = new Variable('a',"2").add(new Variable('b',"2").multiply(t));
        Expression z = new Variable('a',"3").add(new Variable('b',"3").multiply(t));
        System.out.println(new Variable('A').multiply(x).add(new Variable('B').multiply(y)).add(new Variable('C').multiply(z)).fullSimplify().toLaTeX());
    }
}