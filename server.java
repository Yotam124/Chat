/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 *
 * @author erlichsefi
 */
public class server extends javax.swing.JFrame {

	ChatServer chatServerThread = null;  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Creates new form server
     */
    public server() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea("");
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Start");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                startServer(evt);
            }
        });

        jButton2.setText("Stop");
        jButton2.setEnabled(false);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
            	StopServer(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setText("Server");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(107, 107, 107)
                        .addComponent(jButton2)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    /**
     * Update Gui when the server starts
     * @param evt
     */
    private void startServer(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startServer
    	
        jButton2.setEnabled(true);
        jButton1.setEnabled(false);
        

        chatServerThread = new ChatServer();
        Thread starter = new Thread(chatServerThread);
        starter.start();
        
        jTextArea1.append("Server started...\n");
    }

    /**
     * Update Gui when the server stops
     * @param evt
     */
    private void StopServer(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StopServer
    	
    	jButton2.setEnabled(false);
    	jButton1.setEnabled(true);
    	
    	 try 
         {
             Thread.sleep(5000);                 //5000 milliseconds is five second.
             chatServerThread.stop();
         } 
         catch(InterruptedException ex) 
    	 {
        	 Thread.currentThread().interrupt();
        	 ex.printStackTrace();
         }
         
         sendToAll("Server:is stopping and all users will be disconnected.\n:Chat");
         jTextArea1.append("Server stopping... \n");
         
         jTextArea1.setText("");
    	
         
        System.out.println("Server stop");

    }//GEN-LAST:event_StopServer

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new server().setVisible(true);
            }
        });
    }

	/**
	 * This method is for adding a user to the array that holding all the connected users.
	 */	   
    public synchronized void userAdd (String user) 
    {
    	users.add(user);
        jTextArea1.append("User " + user + " added. \n");
    }
    
	/**
	 * This method is for removing a user from the array that holding all the connected users.
	 */	
    public synchronized void userRemove(String user) 
    {
        users.remove(user);
        jTextArea1.append("User " + user + " removed. \n");
    }
    
	/**
	 * This method is for routing a message to all user.
	 */	
    public void sendToAll(String message) 
    {  	
    	Iterator<PrintWriter> it =  clientOutputStreams.iterator();

        while (it.hasNext()) 
        {
            try 
            {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                jTextArea1.append("Sending: " + message + "\n");
                writer.flush();
                jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());

            } 
            catch (Exception ex) 
            {
            	jTextArea1.append("Error telling everyone. \n");
            	ex.printStackTrace();
            }
        } 
    }
    
	/**
	 * This method is for routing a message to specific user.
	 */	
    public void sendToOne(String message, String user) {
    	
    	if(users.contains(user)) {
    		
    		PrintWriter writer = clientOutputStreams.get(users.indexOf(user));
    		 writer.println(message);
    		 jTextArea1.append("Sending: " + message + "To:" + user + "\n");
    		 writer.flush();
    		 jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
    	}
    }

	/**
	 * This class is for Message Routing between the chat Server and the clients.
	 */	
    public class MessageRouter implements Runnable	
    {
    	static final String connect = "Connect";
    	static final String chat = "Chat";
    	static final String online = "OnlineUsers";
    	static final String disconnect = "Disconnect";
    	
        BufferedReader reader;
        Socket sock;
        PrintWriter client;

        public MessageRouter(Socket clientSocket, PrintWriter user) 
        {
             client = user;
             try 
             {
                 sock = clientSocket;
                 InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                 reader = new BufferedReader(isReader);
             }
             catch (Exception ex) 
             {
                 jTextArea1.append("Unexpected error... \n");
                 ex.printStackTrace();
             }

        }

        @Override
        public void run() 
        {
        	String message;
            String[] messageArray;
            
            
             try 
             {
                 while ((message = reader.readLine()) != null) 
                 {
                     jTextArea1.append("Received: " + message + "\n");
                     
                     messageArray = message.split(":");
 					 String messageType = messageArray[2];

                     if (messageType.equals(connect)) 
                     {
                         connect(messageArray[0],messageArray[1]);
                     } 
                     else if (messageType.equals(disconnect)) 
                     {
                         disconnect(messageArray[0]);
                     } 
                     else if (messageType.equals(online)) 
                     {                   	
                    	onlineUsers(messageArray[0]);
                     } 
                     else if (messageType.equals(chat)) 
                     {                   	 
                    	 chat(message,messageArray[3]);
                     } 
                     else 
                     {
                         jTextArea1.append("No Conditions were met. \n");
                     }
                 } 
              } 
              catch (Exception ex) 
              {
                 jTextArea1.append("connection Lost. \n");
                 ex.printStackTrace();
                 clientOutputStreams.remove(client);
              } 
        }
        
    	/**
    	 * This method is for handling and routing the "chat" type message.
    	 */	
        private void chat(String messageBody,String recipient) {
        	
       	 if(recipient == null || recipient.isEmpty() || recipient.equals("NAME")) {
    		 sendToAll(messageBody);
    	 }
    	 else {                   		                    		 
    		 	sendToOne(messageBody,recipient);
    	 	}
        }
        
    	/**
    	 * This method is for handling and routing the "connect" type message.
    	 */	
		private void connect(String sender,String messageBody) {			
            sendToAll((sender + ":" + messageBody + ":" + chat));
            userAdd(sender);
		}
		
    	/**
    	 * This method is for handling and routing the "disconnect" type message.
    	 */	
		private void disconnect(String sender) {			
            sendToAll((sender + ":has left." + ":" + chat));
            userRemove(sender);
		}
		
    	/**
    	 * This method is for handling and routing the "onlineUsers" type message.
    	 */	
		private void onlineUsers(String sender) {			
        	Object[] usersArr =  users.toArray();
        	String usersStr = Arrays.stream(usersArr).map(i -> i.toString()).collect(Collectors.joining (",")).toString();
        	String message = sender + ":" + usersStr + ":" + online;
        	sendToOne(message,sender);
		}
     }
 
	/**
	 * This class is for create a chat Server socket.
	 */	
    public class ChatServer implements Runnable 
    {
    	private ServerSocket serverSock = null;
    	
        @Override
        public void run() 
        {
        	
            clientOutputStreams = new ArrayList<PrintWriter>();
            users = new ArrayList<String>();  

            try 
            {
                serverSock = new ServerSocket(2222);
               
                while (true) 
                {
				Socket clientSock = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
				clientOutputStreams.add(writer);

				Thread listener = new Thread(new MessageRouter(clientSock, writer));
				listener.start();
				jTextArea1.append("Got a connection. \n");
                }
            }
            catch (Exception ex)
            {
                jTextArea1.append("Error making a connection. \n");
                ex.printStackTrace();
            }
        }
 
    	/**
    	 * This method is for closing the chat Server socket.
    	 */	
        public void stop() {
        	if(serverSock == null || serverSock.isClosed()) 
        		return;
        	try {
				serverSock.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
        }
    }
    
    // ChatServer variables 
    ArrayList<PrintWriter> clientOutputStreams;
    ArrayList<String> users;
    //End of ChatServer variables 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
    
}