package cqu.mvc.model.utils;

import cqu.mvc.model.domain.FileEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * hibernate工具类
 */
public class HiberUtil {
    private static final SessionFactory sessionFactory;

    static {
        //获取hibernate.properties或hibernate.cfg.xml的配置信息
        final Configuration configuration = new Configuration();
        configuration.addAnnotatedClass( FileEntity.class );
        //创建sessionFactory
        sessionFactory = configuration.buildSessionFactory( new StandardServiceRegistryBuilder().build() );
    }

    //从SessionFactory中获取Session
    public static Session getSession(){
        return sessionFactory.openSession();
    }

}
