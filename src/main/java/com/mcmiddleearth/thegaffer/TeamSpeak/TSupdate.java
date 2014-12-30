/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mcmiddleearth.thegaffer.TeamSpeak;

//import com.mcmiddleearth.thegaffer.TeamSpeak.JTS3ServerQuery;
//import com.mcmiddleearth.thegaffer.TeamSpeak.TeamspeakActionListener;
import com.mcmiddleearth.thegaffer.TheGaffer;
import com.mcmiddleearth.thegaffer.storage.JobDatabase;
import de.stefan1200.jts3serverquery.JTS3ServerQuery;
import de.stefan1200.jts3serverquery.TeamspeakActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author Donovan
 */
public class TSupdate {
    @Getter @Setter
    private List<String> inLobby = new ArrayList<String>();
    
    public static void TSjobFetch(){
        if(!JobDatabase.getActiveJobs().isEmpty()){
            
        }
    }
    public static void TSchannelFetch(){
        JTS3ServerQuery query = new JTS3ServerQuery();
        query.connectTS3Query("ts.mcmiddleearth.com", 9987);
        
        TheGaffer.getPluginInstance().getLogger().info(String.valueOf(query.connectTS3Query("ts.mcmiddleearth.com", 9987)));
        TheGaffer.getPluginInstance().getLogger().info(String.valueOf(query.loginTS3("test_bot", "beefburgers")));
        TheGaffer.getPluginInstance().getLogger().info(String.valueOf(query.isConnected()));
    }
    
    public static class TCPconnect extends Thread implements TeamspeakActionListener {
        JTS3ServerQuery query;
        
        @Override
        public void run() {
            query = new JTS3ServerQuery();
            if (!query.connectTS3Query("localhost", 10011)){
                    System.out.println("Not connected");
                    return;
            }
            query.selectVirtualServer(1);
            query.loginTS3("serveradmin", "Y1SA7DZ+");
            System.out.println(query.isConnected());
            query.setTeamspeakActionListener(this);
            System.out.println(query.isConnected());
            query.addEventNotify(JTS3ServerQuery.EVENT_MODE_TEXTCHANNEL, 0);
            query.setDisplayName("GOD");
            System.out.println(query.getLastError());
            while(true){
                try{
                    Thread.sleep(100);
                    if(!query.isConnected()){
                        return;
                    }
                }
                catch (Exception e){}
            }
        }
        
        @Override
        public void teamspeakActionPerformed(String eventType, HashMap<String, String> eventInfo) {
            System.out.println(eventType);
            String msg = eventInfo.get("msg");
            if(msg.contains("poke")){
                query.pokeClient(Integer.parseInt(eventInfo.get("invokerid")), msg);
            }else if(msg.contains("msg")){
                query.sendTextMessage(Integer.parseInt(eventInfo.get("invokerid")), JTS3ServerQuery.TEXTMESSAGE_TARGET_CLIENT, msg);
            }
            System.out.println(eventInfo.toString());
            System.out.println(query.getInfo(JTS3ServerQuery.INFOMODE_CLIENTINFO, Integer.parseInt(eventInfo.get("clid"))).get("client_nickname"));
            System.out.println(eventInfo.keySet());
    //        query.
        }
    }
}
