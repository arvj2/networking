package com.jvra.net;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by Jansel R. Abreu (Vanwolf) on 7/8/2014.
 */
public class DialogAuthenticator extends Authenticator {

    private JDialog passwordDialog;
    private JLabel mainLabel;
    private JLabel userLabel;
    private JLabel passLabel;
    private JTextField username;
    private JPasswordField password;
    private JButton ok;
    private JButton cancel;
    private PasswordAuthentication response;

    public DialogAuthenticator() {
        this("", new JFrame());
    }

    public DialogAuthenticator(String username) {
        this(username, new JFrame());
    }

    public DialogAuthenticator(JFrame parent) {
        this("", parent);
    }

    public DialogAuthenticator(String usr, JFrame frame) {
        mainLabel = new JLabel("Please enter username and password: ");
        userLabel = new JLabel("Username: ");
        passLabel = new JLabel("Password: ");
        username = new JTextField(20);
        password = new JPasswordField(20);
        ok = new JButton("OK");
        cancel = new JButton("Cancel");

        passwordDialog = new JDialog(frame);
        Container container = passwordDialog.getContentPane();
        container.setLayout( new GridLayout(4,1) );
        container.add(mainLabel);

        JPanel p2 = new JPanel();
        p2.add(userLabel);
        p2.add(username);
        container.add(p2);

        p2 = new JPanel();
        p2.add(passLabel);
        p2.add(password);
        container.add(p2);

        p2 = new JPanel();
        p2.add(ok);
        p2.add(cancel);
        container.add(p2);

        passwordDialog.pack();

        ActionListener okListener = new OKResponse();
        ok.addActionListener(okListener);
        username.addActionListener(okListener);
        password.addActionListener(okListener);
        cancel.addActionListener(new CancelResponse());


    }

    private void show(){
        String prompt = this.getRequestingPrompt();
        if( null == prompt ){
            String site = this.getRequestingSite().getHostName();
            String protocol = this.getRequestingProtocol();
            int port = this.getRequestingPort();
            if( null != site && null != protocol ){
                prompt = protocol+"://"+site;
                if( 0< port )
                    prompt +=":"+port;
            }
        }else
            prompt = "";

        mainLabel.setText( "Please enter username and password for "+prompt+" : " );
        passwordDialog.pack();
        passwordDialog.setVisible(true);
    }


    public PasswordAuthentication getPasswordAuthentication(){
        show();
        return response;
    }

    private class OKResponse implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            passwordDialog.setVisible(false);
            char[] pass = password.getPassword();
            String user = username.getText();
            password.setText("");

            response = new PasswordAuthentication(user,pass);
        }
    }

    private class CancelResponse implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            passwordDialog.setVisible(false);
            password.setText("");
            response = null;
        }
    }
}
