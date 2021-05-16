import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;

public class TestCalculator {
    public static void main(String[] args) throws IOException {
        Calculator c = new Calculator();
        Class cls = c.getClass();

        int num = 0;
        BufferedWriter bw = new BufferedWriter(new FileWriter("check.txt"));

        Method[] methods = cls.getMethods();
        if(methods != null){
            for (Method method:methods){
                if (method.isAnnotationPresent(Check.class)){
                    try {
                        method.invoke(c);
                    } catch (Exception e){
                        num++;
                        bw.write(method.getName()+" has exception");
                        bw.newLine();
                        bw.write("Exception name: "+e.getCause().getClass().getSimpleName());
                        bw.newLine();
                        bw.write("Exception cause: "+e.getCause().getMessage());
                        bw.newLine();
                    }
                }
            }
            bw.write(num+" exceptions");
            bw.flush();
            bw.close();
        }
    }
}
