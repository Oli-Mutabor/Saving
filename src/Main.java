import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        GameProgress[] gameProgress = {
                new GameProgress(100, 10000, 1, 5),
                new GameProgress(60, 8000, 2, 15),
                new GameProgress(80, 4000, 3, 25)
        };

        List<String> fileDirs = new ArrayList<>();

        for (int i = 0; i < gameProgress.length; i++) {
            String fileDir = "C://Games/savegames/save" + (i + 1) + ".dat";
            fileDirs.add(fileDir);
            saveGame(fileDir, gameProgress[i]);
        }

        zipFiles("C://Games/savegames/zip.zip", fileDirs);

        for (String fileDir : fileDirs) {
            File file = new File(fileDir);
            file.delete();
        }
    }

    public static void saveGame(String dir, GameProgress gp) {
        try (FileOutputStream fos = new FileOutputStream(dir);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipDir, List<String> fileDirs) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipDir))) {
            for (int i = 0; i < fileDirs.size(); i++) {
                FileInputStream fis = new FileInputStream(fileDirs.get(i));
                ZipEntry entry = new ZipEntry("save" + (i + 1) + ".txt");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

