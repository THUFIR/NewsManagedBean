package net.bounceme.dur.nntp;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;

public enum SingletonNNTP {

    INSTANCE;
    private final Logger logger = Logger.getLogger(SingletonNNTP.class.getName());
    private final Level level = Level.INFO;
    private Properties props = new Properties();
    private List<javax.mail.Message> messages = new ArrayList<javax.mail.Message>();
    private boolean loaded = false;
    private int index = 0;
    private Folder folder = null;
    private Folder root = null;
    private Store store = null;

    private SingletonNNTP() {
        logger.logp(level, "SingletonNNTP", "SingletonNNTP", "only once...");
        props = PropertiesReader.getProps();
        if (!loaded) {
            try {
                loaded = connect();
            } catch (Exception ex) {
                Logger.getLogger(SingletonNNTP.class.getName()).log(Level.SEVERE, "FAILED TO LOAD MESSAGES", ex);
            }
        }
    }

    public List<javax.mail.Message> getMessages(boolean debug) throws Exception {
        logger.logp(level, "SingletonNNTP", "getMessages", "returning messages");
        return Collections.unmodifiableList(messages);
    }

    private boolean connect() throws Exception {
        logger.logp(level, "SingletonNNTP", "setMessages", "connecting to leafnode");
        Session session = Session.getDefaultInstance(props);
        session.setDebug(false);
        store = session.getStore(new URLName(props.getProperty("nntp.host")));
        store.connect();
        root = store.getDefaultFolder();
        folder = root.getFolder(props.getProperty("nntp.group"));
        folder.open(Folder.READ_ONLY);
        index = folder.getMessageCount();
        //folder.close(false);
        //store.close();
        setMessages();
        return true;
    }

    private void setMessages() throws Exception {
        javax.mail.Message[] msgs;
        msgs = folder.getMessages(index - 10, index);
        messages = Arrays.asList(msgs);
        Collections.reverse(messages);
    }

    public void previousMessages() throws Exception {
        logger.log(level, "SingletonNNTP.back..");
        index = index - 10;
        setMessages();
    }

    public void nextMessages() throws Exception {
        logger.log(level, "SingletonNNTP.forward..");
        index = index + 10;
        setMessages();
    }

    public List<javax.mail.Message> getMessages() {
        logger.logp(level, "SingletonNNTP", "getMessages", "returning messages");
        return Collections.unmodifiableList(messages);
    }

    public Message getMessage(int id) {
        logger.log(level, "SingletonNNTP.forward..{0}", messages.size());
        Message message = null;
        for (Message m : messages) {
            int i = 0;
            if (i == m.getMessageNumber()) {
            }
        }
        message = messages.get(id);
        return message;
    }
}