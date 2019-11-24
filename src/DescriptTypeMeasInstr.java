import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Это класс Госреестра СИ - описывает описание типа
на отдельно взятый номер госреестра
https://fgis.gost.ru/fundmetrology/files/tsi.xml
*/
public  class DescriptTypeMeasInstr {
    private String NameSI; //наименование типа СИ
    private String NumberSI; //номер госреестра
    private boolean IsEnable; //true - госреестр действует, false  - не действует
    private String FactoryNumSI; // пока неясно что это
    private String ManufacturerTotalSI; //наименование организации производителя



    public DescriptTypeMeasInstr(String ObjectStr) {
        final String regex = "<Obj>.*<\\/Obj>";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(ObjectStr);

        if (matcher.find()) {
            //matcher.group(0); это строка в тегах <Obj>...</Obj>
            setNameSI(ObjectStr);
            setNumberSI(ObjectStr);
            setFactoryNumSI(ObjectStr);
            setIsEnable(ObjectStr);
            setManufacturerTotalSI(ObjectStr);

        }

    }

    public void setNameSI(String nameSI) {
        final String regex = "<NameSI>.*<\\/NameSI>";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(nameSI);

        if (matcher.find()) {
            NameSI = matcher.group(0).replaceAll("<NameSI>|<\\/NameSI>","");// это строка в тегах <NameSI>...</NameSI>

        }
    }

    public void setNumberSI(String numberSI) {
        final String regex = "<NumberSI>.*<\\/NumberSI>";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(numberSI);

        if (matcher.find()) {
            NumberSI = matcher.group(0).replaceAll("<NumberSI>|<\\/NumberSI>","");// это строка в тегах <NumberSI>...</NumberSI>

        }

    }



    public void setIsEnable(String InStr) {
        final String regex = "<StatusSI>.*<\\/StatusSI>";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(InStr);

        if (matcher.find()) {

            String str = matcher.group(0).replaceAll("<StatusSI>|<\\/StatusSI>","");

            IsEnable = str.equals("Действует")? true:false;

        }


    }

    public void setFactoryNumSI(String factoryNumSI) {
        final String regex = "<FactoryNumSI>.*<\\/FactoryNumSI>";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(factoryNumSI);

        if (matcher.find()) {
            FactoryNumSI = matcher.group(0).replaceAll("<FactoryNumSI>|<\\/FactoryNumSI>","");// это строка в тегах <NumberSI>...</NumberSI>

        }

    }

    public void setManufacturerTotalSI(String manufacturerTotalSI) {

        final String regex = "<ManufacturerTotalSI>.*<\\/ManufacturerTotalSI>";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(manufacturerTotalSI);

        if (matcher.find()) {
            ManufacturerTotalSI = matcher.group(0).replaceAll("<ManufacturerTotalSI>|<\\/ManufacturerTotalSI>","");// это строка в тегах <NumberSI>...</NumberSI>

        }


    }

    public String getNameSI() {
        return NameSI;
    }

    public String getNumberSI() {
        return NumberSI;
    }

    public boolean isEnable() {
        return IsEnable;
    }

    public String getFactoryNumSI() {
        return FactoryNumSI;
    }

    public String getManufacturerTotalSI() {
        return ManufacturerTotalSI;
    }

    @Override
    public String toString() {

        return this.getNumberSI()+":\n\t"+this.getNameSI()+"\n\t"+this.getManufacturerTotalSI() +"\n\tСтатус: "+
                this.isEnable()+"\n\t"+this.getFactoryNumSI();
    }




    public static void main(String[] args) throws IOException, InterruptedException {
// добавить внешние параметры через args
        // имя каталога, откуда качать, расположение справочника

        // new siteDownload().LoadOneFile("https://fgis.gost.ru/fundmetrology/files/tsi.xml","/home/local-guest/Documents/GRSI/");

// сделана подготовка файла, не вставлен символ переноса строки после тега </obj>

        FileReader fileTsiXml = new FileReader("/home/local-guest/Documents/GRSI/tsi.xml");
        BufferedReader reader = new BufferedReader(fileTsiXml);
        String FileContent = null;
        while (reader.ready())
        {
            FileContent = FileContent+reader.readLine();
        }
        reader.close();

        final String regex = "<\\/Obj>";
        final String subst = "<\\/Obj>\n";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(FileContent);

        final String resultContent = matcher.replaceAll(subst);

        FileWriter fileWriter = new FileWriter("/home/local-guest/Documents/GRSI/tsi_format.xml");
        BufferedWriter FileBufWriter = new BufferedWriter(fileWriter);

        FileBufWriter.write(resultContent);
        FileBufWriter.close();

        System.out.println("Файл справочника загружен как tsi.xml\nДополнительно преобразован в файл tsi_format.xml");


////////////////////////////////////////////////////////////////////////////////////
        String FileName = "/home/local-guest/Documents/GRSI/tsi_format.xml";
        //String FileName = "E:\\test_txt\\tsi_format.xml";
        FileReader fileReader = new FileReader(FileName);
        BufferedReader readerBufferedReader = new BufferedReader(fileReader);
        //String InStr ="<Obj><NameSI>Измерители коэффициента шума</NameSI><NumberSI>10010-85</NumberSI><StatusSI>Действует</StatusSI><FactoryNumSI>*)</FactoryNumSI><ManufacturerTotalSI>ПО \"Радиоприбор\", г.Великие Луки</ManufacturerTotalSI></Obj>";

        ArrayList<DescriptTypeMeasInstr> MyDevList = new ArrayList<>();

        while (readerBufferedReader.ready())
        {
            String ReadStr = readerBufferedReader.readLine();
            MyDevList.add(new DescriptTypeMeasInstr(ReadStr));

        }

        for (DescriptTypeMeasInstr dev : MyDevList)
        {
            //System.out.println(dev);
            // качаем файл описания типа
            new siteDownload().LoadFile("https://all-pribors.ru/docs/",dev.getNumberSI()+".pdf",
                    "/home/local-guest/Documents/GRSI/");
            Thread.sleep(10);
            // качаем файл методики поверки
            new siteDownload().LoadFile("https://all-pribors.ru/docs/","mp-"+dev.getNumberSI()+".pdf",
                    "/home/local-guest/Documents/GRSI/");
            Thread.sleep(10);

        }
    }
}
