import java.io.*;
import java.net.URL;
import java.util.ArrayList;


public class siteDownload {

    public  boolean LoadFile(String DomainAdress, String FileName, String path) throws IOException {

        //имя конкретного файла
        //String FileName = "54849-13.pdf";

        //адрес в сети
        //String DomainAdress = "https://all-pribors.ru/docs/";

        //поток для чтения
        InputStream inURL = null;

        // куда все сохраняем
        //String path = "/home/local-guest/Documents/GRSI/";

        System.out.println("Начало скачивания файла: "+FileName);
        try {
            inURL = new URL(DomainAdress + FileName).openStream();
            ArrayList<Byte> byteArrayList = ReadFullyByByte(inURL, false); //  в массиве лежат байты

            // сохраним байты в файл



            SaveByteArrToFile(path + FileName, byteArrayList);
            inURL.close();

            System.out.println("\t\t==>> Done\n");
            return  true;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Файл с именем "+ FileName +" отсутствует по адресу " + DomainAdress);
            //можно тут отсеивать описания типа, на которые нет файлов
            // и методик поверки
            return false;
        }



    }


    public  boolean LoadOneFile(String DomainAdress,  String path) throws IOException {

               //поток для чтения
        InputStream inURL = null;


        System.out.println("Начало скачивания файла: "+DomainAdress);
        try {
            inURL = new URL(DomainAdress ).openStream();
            ArrayList<Byte> byteArrayList = ReadFullyByByte(inURL, true); //  в массиве лежат байты

            // сохраним байты в файл



            SaveByteArrToFile(path + "tsi.xml", byteArrayList);
            inURL.close();

            System.out.println("\t\t==>> Done\n");
            return  true;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Файл с именем "+ "tsi.xml" +" отсутствует по адресу " + DomainAdress);
            //можно тут отсеивать описания типа, на которые нет файлов
            // и методик поверки
            return false;
        }



    }

    public static  ArrayList<Byte> ReadFullyByByte(InputStream is, boolean ShowByteCount) throws IOException
    {
        ArrayList<Byte> ReturnByteArr = new ArrayList<>();
        int counter=0;
        while (true)
        {
            int  oneByte = is.read();
            if (oneByte != -1)
            {
                counter++;
                // покажем сколько скачано, если вызвали с  true
                 if (ShowByteCount) System.out.print("Скачано "+ counter +" байт\r");

                ReturnByteArr.add((byte)oneByte);

            }
            else
            {

                break;
            }
        }

        return ReturnByteArr;
    }

    public static void SaveByteArrToFile (String Path,ArrayList<Byte> InByteArr)
    {
        try {
            OutputStream os = new FileOutputStream(Path);
            for (Object MyByte : InByteArr.toArray()) {
                os.write((byte) MyByte);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Не удается сохранить файл по указанному адресу ");
            System.out.println("Имя файла для записи: "+ Path);
            System.out.println("================================");
            e.printStackTrace();
            System.out.println("================================");

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
