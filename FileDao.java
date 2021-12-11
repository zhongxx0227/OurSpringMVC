package cqu.mvc.model.Dao;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import cqu.mvc.model.domain.FileEntity;
import cqu.mvc.model.utils.HiberUtil;

/**
 * Dao层文件
 * 实现数据库的写入
 */
public class FileDao {
    public static void addFileEntity(String id, String name) throws IOException {
        FileEntity FileEntity = new FileEntity();
        FileEntity.setId(id);
        FileEntity.setName(name);
        String base = "F:\\"; //上传文件基址
        String fileName = base.concat(FileEntity.getName());
        FileInputStream file = new FileInputStream(fileName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int temp;
        while ((temp = file.read(bytes)) != -1){
            outputStream.write(bytes, 0, temp);
        }
        byte[] finalBytes = outputStream.toByteArray();
        FileEntity.setFileByte(finalBytes);
        Session session = HiberUtil.getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(FileEntity);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static List<FileEntity> getAllFileEntitys() {
        Session session = HiberUtil.getSession();
        try {
            Query<FileEntity> q=session.createQuery("from FileEntity",FileEntity.class);
            return q.list();
        } finally {
            session.close();
        }
    }
}
