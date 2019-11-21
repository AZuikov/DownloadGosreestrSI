import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class siteDownload {

    public static void main(String[] args) throws IOException {

        //имя конкретного файла
        String FileName = "54849-1311.pdf";

        //адрес в сети
        String DomainAdres = "https://all-pribors.ru/docs/";

        //поток для чтения
        InputStream inURL = null;

        // куда все сохраняем
        String path = "/home/local-guest/Documents/GRSI/";

        System.out.println("Нчало скачивания файла: "+FileName);
        try {
            inURL = new URL(DomainAdres + FileName).openStream();
            ArrayList<Byte> byteArrayList = ReadFullyByByte(inURL); //  в массиве лежат байты

            // сохраним байты в файл



            SaveByteArrToFile(path + FileName, byteArrayList);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Файл с именем "+ FileName +" отсутствует по адресу " + DomainAdres);
            //можно тут отсеивать описания типа, на которые нет файлов
            // и методики поверки
        }
        System.out.println("==========Done===========");


    }

    public static  ArrayList<Byte> ReadFullyByByte(InputStream is) throws IOException
    {
        ArrayList<Byte> ReturnByteArr = new ArrayList<>();
        int counter=0;
        while (true)
        {
            int  oneByte = is.read();
            if (oneByte != -1)
            {
                counter++;
                System.out.print("Скачано "+ counter +" байт\r");
                ReturnByteArr.add((byte)oneByte);

            }
            else
            {
                System.out.println();
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
