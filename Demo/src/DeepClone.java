import java.io.*;

public class DeepClone implements Cloneable, Serializable {
    private String name;
    private DeepClone partner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeepClone getPartner() {
        return partner;
    }

    public void setPartner(DeepClone partner) {
        this.partner = partner;
    }

    public DeepClone deepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (DeepClone) ois.readObject();
    }

    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
        DeepClone d1 = new DeepClone();
        d1.setName("mike");
        DeepClone d2 = new DeepClone();
        d2.setName("nil");

        d1.setPartner(d2);

        System.out.println(d2.hashCode());

        DeepClone shallow = (DeepClone) d1.clone();

        System.out.println(shallow.getPartner().hashCode());

        DeepClone deep = d1.deepClone();

        System.out.println(deep.getPartner().hashCode());
    }


}
