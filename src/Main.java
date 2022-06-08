import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress save1 = new GameProgress(100, 10000, 1, 5);
        GameProgress save2 = new GameProgress(60, 8000, 2, 15);
        GameProgress save3 = new GameProgress(80, 4000, 3, 25);

        saveGame("C://Games/savegames/save1.dat", save1);
        saveGame("C://Games/savegames/save2.dat", save2);
        saveGame("C://Games/savegames/save3.dat", save3);

        String[] saveGames = {"C://Games/savegames/save1.dat",
                "C://Games/savegames/save2.dat",
                "C://Games/savegames/save3.dat"};

        zipFiles("C://Games/savegames/zip.zip", saveGames);

        File save1File = new File("C://Games/savegames/save1.dat");
        File save2File = new File("C://Games/savegames/save2.dat");
        File save3File = new File("C://Games/savegames/save3.dat");
        save1File.delete();
        save2File.delete();
        save3File.delete();

    }

    public static void saveGame(String dir, GameProgress gp) {
        try (FileOutputStream fos = new FileOutputStream(dir);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipDir, String[] fileDirs) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipDir))) {
            for (int i = 0; i < fileDirs.length; i++) {
                FileInputStream fis = new FileInputStream(fileDirs[i]);
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

