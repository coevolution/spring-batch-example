package com.treefinance.payment.batch.service;

import com.datatrees.commons.utils.DateUtil;
import com.datatrees.commons.utils.JsonUtil;
import com.treefinance.payment.batch.config.DefaultConfig;
import com.treefinance.payment.batch.listener.PaymentJobExecutionListener;
import com.treefintech.notify.facade.request.mail.MailMessage;
import com.treefintech.notify.facade.request.mail.MailType;
import com.treefintech.notify.facade.service.MailFacade;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lxp
 * @date 2019/12/19 下午5:20
 * @Version 1.0
 */
@Service
public class NoticeServieImpl {
    private static final Logger logger = LoggerFactory.getLogger(NoticeServieImpl.class);
    @Autowired private MailFacade mailFacade;
    @Autowired private DefaultConfig defaultConfig;
    public void sendBusinessNotice(String topic, Object object, Class clz, String message, Throwable e) {
        try {
            logger.info("[Email]Send notice email.");
            List<String> stringList = new LinkedList<>();

            stringList.add(String.format("Date Time:%s", DateUtil.formatLLDateTime(new Date())));
            if (clz != null) {
                stringList.add(MessageFormat.format("Class:{0}, Metadata:{1}", clz.getName(),
                    JsonUtil.jsonFromObject(object)));
            }

            if (null != message && !message.isEmpty()) {
                stringList.add(message);
            }

            if (null != e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionDetails = sw.toString();
                stringList.add(exceptionDetails);
            }

            String[] emailAddrs = defaultConfig.getEmailList().split(";");
            if (null != emailAddrs && 0 < emailAddrs.length) {
                MailMessage mailMessage = new MailMessage();
                mailMessage.setBusiness("monitor");
                mailMessage.setSubject(topic);
                mailMessage.setContent(StringUtils.join(stringList, "\r\n"));
                mailMessage.setMailType(MailType.SIMPLE);
                mailMessage.setToList(Arrays.asList(emailAddrs));
                mailFacade.sendMessage(mailMessage);
            }
        } catch (Exception ee) {
            logger.error("Send Email Exception", ee);
        }
    }
}
