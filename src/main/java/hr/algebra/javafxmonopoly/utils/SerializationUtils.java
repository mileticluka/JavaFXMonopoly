package hr.algebra.javafxmonopoly.utils;

import java.io.*;

public final class SerializationUtils {
    private SerializationUtils() {

    }

    public static<T extends Serializable> void write(T t, File file) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(t);
        }
    }

    public static<T extends Serializable> T read(File file) throws IOException, ClassNotFoundException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (T) ois.readObject();
        }
    }


    public static<T extends Serializable> void write(T t, Byte[] bytes) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream();
    }

    public static<T extends Serializable> T read(Byte[] bytes) throws IOException, ClassNotFoundException {
        try(ObjectInputStream ois = new ObjectInputStream(new DataOutputStream(file))) {
            return (T) ois.readObject();
        }
    }


}
