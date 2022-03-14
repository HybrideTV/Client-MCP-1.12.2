package scclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import javax.imageio.stream.FileImageInputStream;

import com.google.gson.Gson;

public class FileManager
{
    private static Gson gson = new Gson();
    private static File ROOT_DIR = new File("scclient");
    private static File MODS_DIR = new File(ROOT_DIR, "config");

    public static void init()
    {
        if (!ROOT_DIR.exists())
        {
            ROOT_DIR.mkdirs();
        }

        if (!MODS_DIR.exists())
        {
            MODS_DIR.mkdirs();
        }
    }

    public static Gson getGson()
    {
        return gson;
    }

    public static File getModsDirectory()
    {
        return MODS_DIR;
    }

    public static boolean writeJsonToFile(File file, Object obj)
    {
        try
        {
            if (!file.exists())
            {
                file.createNewFile();
            }

            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(gson.toJson(obj).getBytes());
            outputStream.flush();
            outputStream.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static<T extends Object> T readFromJson(File file, Class<T> c)
    {
        try
        {
            FileInputStream fileImputStream = new FileInputStream(file);
            InputStreamReader imputStreamReader = new InputStreamReader(fileImputStream);
            BufferedReader bufferedReader = new BufferedReader(imputStreamReader);
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null)
            {
                builder.append(line);
            }

            bufferedReader.close();
            imputStreamReader.close();
            fileImputStream.close();
            return gson.fromJson(builder.toString(), c);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
