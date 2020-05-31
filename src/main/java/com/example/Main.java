package com.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;




public class Main {

    
    public static void main(String[] args) throws Exception{
        String webappDirLocation = "src/main/webapp/";

        String webPort= System.getenv("PORT");
        if(webPort==null|| webPort.isEmpty()){
            webPort="8081";
        }
      
        Server server = new Server(Integer.valueOf(webPort));
        WebAppContext root = new WebAppContext();
        root.setContextPath("/");
        root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);
        PersistenceManager.getInstance().getEntityManagerFactory();
        
        root.setParentLoaderPriority(true);
        server.setHandler(root);
        server.start();
        server.join();
     
     
    }

}
