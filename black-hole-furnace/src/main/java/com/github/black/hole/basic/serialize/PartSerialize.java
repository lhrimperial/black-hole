package com.github.black.hole.basic.serialize;

import com.github.black.hole.basic.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/** */
public class PartSerialize {

    public static void main(String[] args) throws Exception {
        //        testTransient();
        //        testOverride();
        testExternalizable();
    }

    public static void testExternalizable() throws Exception {
        Book3 book = new Book3("Hello Java");
        book.setIsbn("ABC123456789");
        book.setAuthors(Arrays.asList("John", "Eric"));
        System.out.println("book==>" + book);

        /** 将book对象序列化到book.temp文件中去 */
        String fileName = Constants.SERIAL_DIR + "book.temp";
        SerializationUtil.serialize(book, fileName);

        /** 从book.temp文件中，反序列化一个Book对象 */
        Book3 deserializedBook = (Book3) SerializationUtil.deserialize(fileName);
        // deserializedBook==>Book [name=Hello Java, isbn=ABC123456789, authors=[John, Eric]]
        System.out.println("deserializedBook==>" + deserializedBook);
    }

    public static void testOverride() throws Exception {
        Book2 book = new Book2("Hello Java");
        book.setIsbn("ABC123456789");
        //        book.setName("Hello Java");
        book.setAuthors(Arrays.asList("John", "Eric"));
        System.out.println("book==>" + book);

        /** 将book对象序列化到book.temp文件中去 */
        String fileName = Constants.SERIAL_DIR + "book.temp";
        SerializationUtil.serialize(book, fileName);

        /** 从book.temp文件中，反序列化一个Book对象 */
        Book2 deserializedBook = (Book2) SerializationUtil.deserialize(fileName);
        // deserializedBook==>Book [name=Hello Java, isbn=ABC123456789, authors=[John, Eric]]
        System.out.println("deserializedBook==>" + deserializedBook);
    }

    public static void testTransient() throws Exception {
        Book1 book = new Book1();
        book.setIsbn("ABC123456789");
        book.setName("Hello Java");
        book.setAuthors(Arrays.asList("John", "Eric"));
        System.out.println("book==>" + book);

        /** 将book对象序列化到book.temp文件中去 */
        String fileName = Constants.SERIAL_DIR + "book.temp";
        SerializationUtil.serialize(book, fileName);

        /** 从book.temp文件中，反序列化一个Book对象 */
        Book1 deserializedBook = (Book1) SerializationUtil.deserialize(fileName);
        // deserializedBook==>Book [name=Hello Java, isbn=ABC123456789, authors=[John, Eric]]
        System.out.println("deserializedBook==>" + deserializedBook);
    }

    @Data
    @NoArgsConstructor
    public static class Book3 implements Externalizable {
        private static final long serialVersionUID = 7017971569029531549L;
        /** 书名 */
        private String name;

        /** ISBN */
        private String isbn;

        /** 作者 */
        private List<String> authors;

        public Book3(String name) {
            this.name = name;
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(name);
            out.writeObject(isbn);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            name = (String) in.readObject();
            isbn = (String) in.readObject();
        }
    }

    @Data
    public static class Book2 implements Serializable {
        private static final long serialVersionUID = -8809378577596769409L;
        /** 书名 */
        private String name;

        /** ISBN */
        private String isbn;

        /** 作者 */
        private List<String> authors;

        public Book2(String name) {
            this.name = name;
        }

        private void writeObject(ObjectOutputStream oos) throws IOException {
            // oos.defaultWriteObject();
            oos.writeObject(name);
            oos.writeObject(isbn);
        }

        private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
            // ois.defaultReadObject();
            name = (String) ois.readObject();
            isbn = (String) ois.readObject();
        }
    }

    @Data
    public static class Book1 implements Serializable {
        private static final long serialVersionUID = 4817467371723856063L;
        /** 书名 */
        private String name;

        /** ISBN */
        private transient String isbn;

        /** 作者 */
        private transient List<String> authors;
    }
}
