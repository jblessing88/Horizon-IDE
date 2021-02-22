package main.java.ouride;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;

public class OurThread{ //extends Thread{

    private BigInteger saveTime;
    private int count = 0;
    private BigInteger big = new BigInteger("300000000000");
    private OurIDE ide;
    private Timer timer = new Timer("AutoSave");
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            aTimer.start();
        }
    };
    private AnimationTimer aTimer = new AnimationTimer() {

        @Override
        public void handle(long l) {
            if(count == 0){
                saveTime = BigInteger.valueOf(l);
                count = 1;
            }
            if((l - saveTime.longValue()) >= (big.longValue())){
                stop();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ide.getOutputArea().setNotification3("Saving..");
                        	//System.out.println("Saving!!");
                            save();
                            try{
                            	Thread.sleep(1500);
                            }
                            catch(InterruptedException e){
                            	Thread.currentThread().interrupt();
                            }
                            ide.getOutputArea().setNotification3("");
                        } catch (IOException ex) {
                            Logger.getLogger(OurThread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };
                runnable.run();
                count = 0;
                start();
            }
        }
    };
    
    public OurThread(OurIDE a){
        ide = a;
        timer.schedule(task, 1);
    }
    
    public void stop(){
        timer.cancel();
    }
    
    public void save() throws IOException{
        String delimiter = System.getProperty("file.separator");
        for(int i = 1; i < ide.getTabsSize(); i++){
            OurTab tab = ide.nextActiveTab(i);
            
            if(true){//!tab.hasBeenSaved()){
                DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmssZ");
                Calendar cal = Calendar.getInstance();
                String path = "Rec" + dateFormat.format(cal.getTime());
                File file = new File(ide.getInstallDirec() + delimiter + "Recovery" + delimiter + "Rec" + dateFormat.format(cal.getTime()) + ".java");
                FileWriter fileWriter = null;
                BufferedWriter bufferedWriter = null;
                try{
                    fileWriter = new FileWriter(file.getAbsolutePath());
                    bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(tab.getContentText());
                    file.mkdir();
                } catch(IOException ioe){
                    //put something here
                } finally{
                    try{
                        bufferedWriter.close();
                        fileWriter.close();
                    } catch(IOException ioe){
                        //put something here
                    }
                }
            }
            /*else if(tab.hasBeenSaved()){
                String str = "";
                String title = "";
                String content = ide.lastActiveTab().getTextArea().getText();
                for(int j = 0; j < content.length(); j++){
                    if(content.charAt(j) == 'c'){
                        do
                        {
                            if(!str.equals("class")){
                                System.out.println(str);
                                if(content.charAt(j) == ' '){
                                    System.out.println("Wrong c");
                                    str = "";
                                    j++;
                                }
                                else if(content.charAt(j) != ' ')
                                    str += content.charAt(j);
                                if(j != content.length())
                                    j++;
                                if(j == content.length())
                                    break;
                            }
                            else if(str.equals("class")){
                                System.out.println(title);
                                j += 2;
                                if(content.charAt(j) == ' ')
                                    break;
                                title += content.charAt(j);
                                if(j != content.length())
                                    j++;
                                if(j == content.length())
                                    break;
                            }
                        } while(Character.isLetter(content.charAt(j)));
                    }
                }

                if(str.equals("class") && !title.equals(null)){

                    System.out.println(ide.getInstallDirec() + delimiter + "Recovery" + delimiter + "" + title + ".java");
                    File file = new File(ide.getInstallDirec() + delimiter + "Recovery" + delimiter + "" + title + ".java");
                    FileWriter fileWriter = null;
                    BufferedWriter bufferedWriter = null;
                    try{
                        fileWriter = new FileWriter(file.getAbsolutePath());
                        bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(tab.getContentText());
                        file.mkdir();
                    } catch(IOException ioe){
                        //put something here
                    } finally{
                        try{
                            bufferedWriter.close();
                            fileWriter.close();
                        } catch(IOException ioe){
                            //put something here
                        }
                    }
                }
                /*String path = ;
                String text = ;
                File file = new File(path);
                if(file.exists()){
                    FileWriter fw = null;
                    BufferedWriter bw = null;
                    try{
                        fw = new FileWriter(path);
                        bw = new BufferedWriter(fw);
                        bw.write(text);
                    } catch(IOException ioe){
                        //put something
                    } finally{
                        try{
                            bw.close();
                            fw.close();
                        } catch(IOException ioe){
                            //put something
                        }

                    }
                }
                else{

                }*/

            //}
        }
    }
    
}
