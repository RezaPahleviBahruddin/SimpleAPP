package app.helper;

import java.io.*;
/**
 * Created by r32427 on 29/03/17.
 */
public class Sessions {
    private final String path = "src/app/sessions/";

    public boolean deleteSessions(String filename){
        File session = new File(path+filename);
        if(session.exists()){
            if(session.delete())
                return true;
            else
                return false;
        }else
            return false;
    }

    public boolean isFoundSessions(String filename){
        File session = new File(path+filename);
        if(session.exists()){
            return true;
        }else
            return false;
    }

    public void writeSessions(String filename, String data){
        try{
            DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(path+filename));
            dataOut.writeUTF(data);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public String readSessions(String filename){
        String res = "";

        try{
            DataInputStream dataIn = new DataInputStream(new FileInputStream(path+filename));

            while(dataIn.available()>0){
                String k = dataIn.readUTF();
                res += k+" ";
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

        return res;
    }
}
