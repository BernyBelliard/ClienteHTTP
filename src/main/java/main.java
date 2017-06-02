
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.net.*;
import java.io.*;

/**
 * Created by Berny Belliard on 26/5/2017.
 */


public class main {
    public static void main(String[] args) {

        Scanner leer = new Scanner(System.in);
        Document document = null;
        String[] nLines = null;

        System.out.println("Escriba una URL válida:");
        String newUrl = leer.next();

        // Obtener documento de la URL
        //newUrl = "http://itachi.avathartech.com:4567/2017.html";
        try {
            document = Jsoup.connect(newUrl).get();
        } catch (UnknownHostException e) {
            System.err.println("Url inválida");

        } catch (IOException e) {
            System.err.println("Url inválida");
        }

        if(document!=null) {
            try {
                operacionA(newUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            operacionBC(document);
            operacionD(document);
            Elements formElements = document.getElementsByTag("form");
            operacionE(formElements);
            operacionF(newUrl,formElements);

        }
    }

    // Imprimir cantidad de líneas

    public static void operacionA(String nUrl) throws IOException {

        int countLines =0;
        URL document = new URL(nUrl);

        BufferedReader input = new BufferedReader(
                new InputStreamReader(document.openStream()));

        while ((input.readLine()) != null)
            countLines++;
        input.close();

        System.out.println("");
        System.out.println("a) Número de líneas: " + countLines);
        System.out.println("");
    }

    // Imprimir cantidad de párrafos y cantidad de imágenes dentro de párrafos

    public static void operacionBC(Document document) {
        int countParag = 0;
        int countImg = 0;
        Elements paragElement = document.getElementsByTag("p");
        for (Element nElement : paragElement) {
            Elements imgElement = nElement.getElementsByTag("img");
            for (Element nImgElement : imgElement) {
                countImg++;
            }
            countParag++;
        }

        System.out.println("b) Número de párrafos: " + countParag);
        System.out.println("");
        System.out.println("c) Número de imágenes dentro de párrafos: " + countImg);
        System.out.println("");
    }

    // Imprimir cantidad de formularios categorizados por el método POST y GET

    public static void operacionD(Document document) {

        int countPost = 0;
        int countGet = 0;
        Elements methodPost = document.getElementsByAttributeValueContaining("method", "post");
        Elements methodGet = document.getElementsByAttributeValueContaining("method", "get");

        for (Element formElement : methodPost) {
            countPost++;
        }
        for (Element formElement : methodGet) {
            countGet++;
        }
        System.out.println("d) Número de formularios por el método POST: " + countPost);
        System.out.println("   Número de formularios por el método GET: " + countGet);
        System.out.println("");
    }

    // Mostrar el campo Input y su tipo de todos los formularios

    public static void operacionE(Elements formElements){

        int countForm = 0;
        System.out.println("e)");
        System.out.println("");
        for (Element nElement : formElements) {
            countForm++;
            System.out.println("Fomulario " + countForm + ":");
            System.out.println("");

            Elements inputElement = nElement.getElementsByTag("input");

            for (Element typeElement:inputElement) {
                Elements valueElement = typeElement.getElementsByAttribute("type");

                for (int i = 0; i < valueElement.size(); i++) {
                    System.out.println("input type = " + valueElement.attr("type").toString());

                }
            }
            System.out.println("");
        }
    }

    // Enviar pedido al servidor

    public static void operacionF(String nUrl, Elements formElements) {
        System.out.println("f)");
        System.out.println("");
        for (Element nElement : formElements) {
            Elements postElement = nElement.getElementsByAttributeValueContaining("method", "post");
            for(Element actionElement: postElement){

                Document newDocument = null;
                try {
                    newDocument = Jsoup.connect(actionElement.absUrl("action")).data("asignatura", "practica1").post();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(actionElement.absUrl("action"));
                System.out.println("");
                if(newDocument!=null)
                System.out.println(newDocument.body().html());
                System.out.println("");
            }
        }
    }
}