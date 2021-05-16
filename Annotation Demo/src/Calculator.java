public class Calculator {
    @Check
    public void add(){
        System.out.println("1+0 = "+(1+0));
    }
    @Check
    public void substract(){
        System.out.println("1-0 = "+(1-0));
    }
@Check
    public void multiple(){
        System.out.println("1*0 = "+(1*0));
    }
    @Check
    public void divide(){
        System.out.println("1/0 = "+(1/0));
    }
}
