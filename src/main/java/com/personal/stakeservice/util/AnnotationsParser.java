package com.personal.stakeservice.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.personal.stakeservice.controller.annotations.RequestMapping;
import com.personal.stakeservice.server.Handler;

public class AnnotationsParser {
	
	public static Map<String, List<Handler>> getMappedHandlers(String fullPackageName) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
		Map<String, List<Handler>> mappedHandlers = new HashMap<>();
		for (Class clazz : getClasses(fullPackageName)) {
			if (clazz.isAnnotation()) {
				continue;
			}
			Object instance = clazz.newInstance();
			for (Method method : clazz.getDeclaredMethods()) {
				RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
				if (requestMapping != null) {
					String operation = requestMapping.method();
					String path = requestMapping.path();
					Handler handler = new Handler();
					handler.setHttpOperation(operation);
					handler.setInstance(instance);
					handler.setMethod(method);
					mappedHandlers.compute(path, (k, v) -> {
						if (v == null) {
							v = new ArrayList<Handler>();
						}
						v.add(handler);
						return v;
					});
				}
			}
		}
		return mappedHandlers;
	}
	
	private static List<Class> getClasses(String packageName)
	        throws ClassNotFoundException, IOException {
	    String path = packageName.replace('.', '/');
	    Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);
	    List<File> dirs = new ArrayList<File>();
	    while (resources.hasMoreElements()) {
	        URL resource = resources.nextElement();
	        dirs.add(new File(resource.getFile()));
	    }
	    ArrayList<Class> classes = new ArrayList<Class>();
	    for (File directory : dirs) {
	        classes.addAll(findClasses(directory, packageName));
	    }
	    return classes;
	}

	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
	    List<Class> classes = new ArrayList<Class>();
	    if (!directory.exists()) {
	        return classes;
	    }
	    java.io.File[] files = directory.listFiles();
	    for (File file : files) {
	        if (file.isDirectory()) {
	            assert !file.getName().contains(".");
	            classes.addAll(findClasses(file, packageName + "." + file.getName()));
	        } else if (file.getName().endsWith(".class")) {
	            classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
	        }
	    }
	    return classes;
	}
}
