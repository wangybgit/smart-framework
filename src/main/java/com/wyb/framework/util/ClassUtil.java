package com.wyb.framework.util;


import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类加载工具类
 * @author Administrator
 *
 */
public class ClassUtil {
	public static final Logger LOGGER=LoggerFactory.getLogger(ClassUtil.class);
	
	/**
	 * 获取类加载器
	 * @return
	 */
	public static ClassLoader getClassLoader(){
		return Thread.currentThread().getContextClassLoader();
	}
	
    /**
     * 加载类
     * 加载类需要提供类名与是否初始化的标志，这里提到的初始化指是否执行类的静态代码块;
     * 为了提高加载类的性能，可以将loadClass方法的isInitialized参数设置false
     */
	public static Class<?> loadClass(String className,boolean isInitailized){
		Class<?> cls;
		try{
			cls=Class.forName(className, isInitailized, getClassLoader());
		}catch(ClassNotFoundException e){
			LOGGER.error("class not found", e);
			throw new RuntimeException(e);
		}
		return cls;
	}
	//不带参数isInitialized时，默认false
	public static Class<?> loadClass(String className){
		return loadClass(className,false);
	}
	
	
	 /**
     * 获取指定包名下所有的类；
     * 获取指定包名下所有的类，需要根据包名并将其转换为文件路径，读取class文件或jar包，获取指定的类名去加载类
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String packagePath = url.getPath().replace("%20", " ");
                        addClass(classSet, packagePath, packageName);
                    } else if ("jar".equals(protocol)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf("."))
                                                .replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("get class set failure.", e);
            throw new RuntimeException(e);
        }
        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(packageName)){
                    subPackagePath = packagePath +"/"+subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtil.isNotEmpty(packageName)){
                    subPackageName = packageName +"."+subPackageName;
                }
                addClass(classSet,subPackagePath,subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }

	 
}
