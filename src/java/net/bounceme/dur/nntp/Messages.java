package net.bounceme.dur.nntp;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.mail.Header;

@ManagedBean
@SessionScoped
public class Messages implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(Messages.class.getName());
    private static Level level = Level.INFO;
    private SingletonNNTP nntp = SingletonNNTP.INSTANCE;

    public Messages() {
        logger.log(level, "MessageBean..");
    }

    public void action() throws Exception {
        logger.log(level, "action..");
    }

    public DataModel getModel() throws Exception {
        logger.log(level, "MessageBean.getModel..");
        List<javax.mail.Message> messages = new ArrayList<javax.mail.Message>();
        messages = nntp.getMessages();
        DataModel messagesDataModel = new ListDataModel(messages);
        return messagesDataModel;
    }

    public URL getUrl(javax.mail.Message message) throws Exception {
        List<Header> headers = getHeaders(message);
        URL url = new URL("http://www.google.com/");
        for (Header h : headers) {
            if ("Archived-at".equals(h.getName())) {
                String stringUrl = h.getValue();
                stringUrl = stringUrl.substring(1, stringUrl.length() - 1);
                url = new URL(stringUrl);
            }
        }
        return url;
    }

    private List<Header> getHeaders(javax.mail.Message message) throws Exception {
        Enumeration allHeaders = message.getAllHeaders();
        List<Header> headers = new ArrayList<Header>();
        while (allHeaders.hasMoreElements()) {
            Header hdr = (Header) allHeaders.nextElement();
            headers.add(hdr);
        }
        return headers;
    }

    public void forward() throws Exception {
        logger.log(level, "MessageBean.forward..");
        nntp.nextMessages();
    }

    public void back() throws Exception {
        logger.log(level, "MessageBean.back..");
        nntp.previousMessages();
    }

    public String detail() throws Exception {
        logger.log(level, "MessageBean.detail..");
        return "detail.xhtml";
    }
}