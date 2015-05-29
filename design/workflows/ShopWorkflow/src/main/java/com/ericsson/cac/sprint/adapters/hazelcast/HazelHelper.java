package com.ericsson.cac.sprint.adapters.hazelcast;

import java.io.File;
import java.net.InetAddress;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.FileSystemXmlConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.Member;

/**
 * Created by eliisen on 2/27/2015
 */
public class HazelHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(HazelHelper.class);

    static HazelcastInstance hazelInstance = null;

    static {
        getInstance();
    }

    public static HazelcastInstance getInstance() {
        String filepath ="";
        LOGGER.info("sprint_config path is:"+System.getProperty("confdir"));
        if (hazelInstance == null) {
            if(System.getProperty("confdir")==null){

                  filepath= "/opt/drutt/ca/adaptweb/conf/sprint_shop_hazelcast.xml";
                  //filepath= "/sprint_hazelcast.xml";
                  LOGGER.debug("FilePath is >>>>>>>>>>>>>>>>"+filepath);

            } else {
                filepath=  System.getProperty("confdir") + File.separator+"sprint_shop_hazelcast.xml";
                LOGGER.debug("Conf dir Path is >>>>>>>>>>>>>>>>>"+filepath);

            }
           //String filepath = "C:\\Sen"+ File.separator + "aca_hazelcast.xml";
            try {
                Config cfg = new FileSystemXmlConfig(filepath);
                hazelInstance = Hazelcast.newHazelcastInstance(cfg);
               LOGGER.debug("hazelInstance is :: ---------------->"+hazelInstance);
            } catch (Exception ex) {
                LOGGER.error("initial hazel cast error", ex);
                Config cfg = new ClasspathXmlConfig("sprint_shop_hazelcast.xml");
                hazelInstance = Hazelcast.newHazelcastInstance(cfg);
            }
        }

        return hazelInstance;
    }

    public static boolean checkMasterNode() {

        if (hazelInstance != null) {
            Member oldestMember = hazelInstance.getCluster().getMembers().iterator().next();
            String masterhost = oldestMember.getSocketAddress().toString();
            LOGGER.debug("master member ip is:" + masterhost);
            if (masterhost.contains(getHostName())) {
                return true;
            }
        }
        return false;
    }

    public static void putObjectToMap(String sessionid, Object hazelObject,String mapValue){

        IMap map =hazelInstance.getMap(mapValue);
        map.putIfAbsent(sessionid, hazelObject);
        LOGGER.debug(mapValue+" Map size:" + map.size() + " Contains: " + map.toString());
    }
    //use mapVale as map name in confi
    public static Object getObjectFromMap(String sessionid,String mapValue){
        //todo, here use class name?
        //IMap map =hazelInstance.getMap("subscriberPrepaid");
        IMap map =hazelInstance.getMap(mapValue);

        return map.get(sessionid);

    }

    public static void removeObjectFromMap(String sessionid,String mapValue){
        //todo, here use class name?
        //IMap map =hazelInstance.getMap("subscriberPrepaid");
        IMap map =hazelInstance.getMap(mapValue);
        map.remove(sessionid);
    }

    private static String getHostName() {
        String host = "";
        try {
            host = InetAddress.getLocalHost().getHostAddress();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        LOGGER.debug("hostname is:"+host);
        return host;
    }


    //get the next retry timestamp
    public static String getCurrentDateString(int min) {
        DateTime d = new DateTime();
        d =d.plusMinutes(min);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss.SSS");
        String date = d.toString(fmt);
        return date;
    }
    //get current time
    public static String getCurrentDateString() {
        DateTime d = new DateTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss.SSS");
        String date = d.toString(fmt);
        return date;
    }


}
